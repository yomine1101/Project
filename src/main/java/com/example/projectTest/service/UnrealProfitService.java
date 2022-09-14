package com.example.projectTest.service;

import com.example.projectTest.controller.dto.request.AddRequest;
import com.example.projectTest.controller.dto.request.UnrealProfitRequest;
import com.example.projectTest.controller.dto.response.Sum;
import com.example.projectTest.controller.dto.response.SumResponse;
import com.example.projectTest.controller.dto.response.TransactionResponse;
import com.example.projectTest.controller.dto.response.UnrealResult;
import com.example.projectTest.model.entriy.Id;
import com.example.projectTest.model.entriy.hcmio;
import com.example.projectTest.model.entriy.mstmb;
import com.example.projectTest.model.entriy.tcnud;
import com.example.projectTest.model.hcmioRepo;
import com.example.projectTest.model.mstmbRepo;
import com.example.projectTest.model.tcnudRepo;
import lombok.extern.java.Log;
import net.bytebuddy.asm.Advice;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.lang.invoke.CallSite;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
public class UnrealProfitService {

    @Autowired
    tcnudRepo tcnudRepos;

    @Autowired
    mstmbRepo mstmbRepos;

    @Autowired
    hcmioRepo hcmioRepos;

    /* Sum */
    public SumResponse sum(UnrealProfitRequest request) {
        if ("001".equals(check(request)))
            return new SumResponse(null, "001", "查無符合資料");
        if (null != check(request)) return new SumResponse(null, "002", check(request));
        List<String> stockList;
        if (request.getStock().isBlank()) {
            stockList = tcnudRepos.getAllStock(request.getBranchNo(), request.getCustSeq());
        } else {
            stockList = new ArrayList<>();
            stockList.add(request.getStock());
        }

        List<Sum> Sums = new ArrayList<>();

        for (String stock : stockList) {
            mstmb mstmbs = mstmbRepos.findByStock(stock);
            Sum sumResult = new Sum();
            BigDecimal temp = new BigDecimal(0);

            sumResult.setDetailList(getResultList(new UnrealProfitRequest(request.getBranchNo(), request.getCustSeq(), stock)));
            for (UnrealResult unrealResult : sumResult.getDetailList()) {
                sumResult.setSumRemainQty(temp.add(unrealResult.getRemainQty()));
                sumResult.setSumFee(temp.add(unrealResult.getFee()));
                sumResult.setSumCost(temp.add(unrealResult.getCost()));
                sumResult.setSumMarketValue(temp.add(unrealResult.getMarketValue()));
                sumResult.setSumUnrealProfit(temp.add(unrealResult.getUnrealProfit()));
            }

            sumResult.setStock(mstmbs.getStock());
            sumResult.setStockName(mstmbs.getStockName());
            sumResult.setNowPrice(mstmbs.getCurPrice());

            Sums.add(sumResult);
        }
        return new SumResponse(Sums, "000", "");
    }

    /* Detail */
    public TransactionResponse unrealProfit(UnrealProfitRequest request) {
        //check
        if ("001".equals(check(request))) return new TransactionResponse(null, "001", "查無符合資料");
        if (null != check(request)) return new TransactionResponse(null, "002", check(request));


        TransactionResponse transactionResponse = new TransactionResponse();

        List<String> stockList;
        if (request.getStock().isBlank()) {
            stockList = tcnudRepos.getAllStock(request.getBranchNo(), request.getCustSeq());
        } else {
            stockList = new ArrayList<>();
            stockList.add(request.getStock());
        }
        List<UnrealResult> resultList = new ArrayList<>();
        for (String stock : stockList) {
            resultList.addAll(getResultList(new UnrealProfitRequest(request.getBranchNo(), request.getCustSeq(), stock)));
        }

        try {
            if (resultList.size() == 0) {
                return new TransactionResponse(resultList, "001", "查無符合資料");
            } else {
                return new TransactionResponse(resultList, "000", "");
            }
        }catch (Exception e){
            return new TransactionResponse(null, "005", "伺服器忙碌中，請稍後嘗試");

        }


    }

    public List<UnrealResult> getResultList(UnrealProfitRequest request) {

        List<UnrealResult> unrealResults = new ArrayList<>();
        List<tcnud> tcnuds = new ArrayList<>();
        if (!request.getStock().isBlank()) {
            //要先findData
            tcnuds = tcnudRepos.findByBranchNoAndCustSeqAndStock(request.getBranchNo(), request.getCustSeq(), request.getStock());
        } else {
            tcnuds = tcnudRepos.findByBranchNoAndCustSeq(request.getBranchNo(), request.getCustSeq());
        }

//        System.out.println(tcnuds.size());

        mstmb mstmb = new mstmb();
        for (tcnud t : tcnuds) {
            mstmb = mstmbRepos.findByStock(t.getStock());
            UnrealResult Response = new UnrealResult(
                    t.getId().getTradeDate(),
                    t.getId().getDocSeq(),
                    t.getStock(),
                    mstmb.getStockName(),
                    t.getPrice(), //buyPrice
                    mstmb.getCurPrice(), //nowPrice
                    t.getQty(),
                    t.getRemainQty(),
                    t.getFee(),
                    t.getCost().setScale(0, RoundingMode.HALF_UP),
                    Calculator.marketValue(mstmb.getCurPrice(), t.getQty()),
                    Calculator.unrealProfit(mstmb.getCurPrice(), t.getQty(), t.getCost()));
            unrealResults.add(Response);
        }

        return unrealResults;


    }


    public TransactionResponse addHcmioAndTcnud(AddRequest request) {


        /* Add_Hcmio */

        Id id = new Id(request.getTradeDate(),request.getBranchNo(),request.getCustSeq(),request.getDocSeq());
        hcmio addHcmio = new hcmio();
        int checkId = hcmioRepos.findByTradeDateAndBranchNoAndCustSeqAndStock(request.getTradeDate(),request.getBranchNo(),request.getCustSeq(),request.getDocSeq());
        System.out.println(checkId);
        try {
            if (id.getTradeDate().isBlank() || id.getBranchNo().isBlank() || id.getCustSeq().isBlank() || id.getDocSeq().isBlank()) {
                return new TransactionResponse(null, "002", "PK 不得為空");
            }else if(checkId > 0){
                return new TransactionResponse(null, "002", "PK 有重複");
            }

                addHcmio.setId(id);
                addHcmio.setStock(request.getStock());
                addHcmio.setBsType("B");
                addHcmio.setPrice(request.getPrice());
                addHcmio.setQty(request.getQty());
                addHcmio.setAmt(Calculator.amt(addHcmio.getPrice(), addHcmio.getQty()));
                addHcmio.setFee(Calculator.fee(addHcmio.getAmt()));
                addHcmio.setTax(Calculator.tax(addHcmio.getAmt()));
                addHcmio.setStinTax(BigDecimal.ZERO);
                addHcmio.setNetAmt(Calculator.netAmt(addHcmio.getAmt(), addHcmio.getFee(), addHcmio.getTax(), "b"));
                addHcmio.setModDate(Calculator.modeDate());
                addHcmio.setModTime(Calculator.modTime());
                addHcmio.setModUser("Eva");
                hcmioRepos.save(addHcmio);

                //check
                /* Add_Tcnud */
                tcnud addTcnud = new tcnud();
                addTcnud.setId(id);

                addTcnud.setStock(request.getStock());
                addTcnud.setPrice(request.getPrice());
                addTcnud.setQty(request.getQty());
                addTcnud.setRemainQty(request.getQty());
                addTcnud.setFee(addHcmio.getFee());
                addTcnud.setCost(BigDecimal.valueOf(225.55));
                addTcnud.setModDate(Calculator.modeDate());
                addTcnud.setModTime(Calculator.modTime());
                addTcnud.setModUser("Eva");
                tcnudRepos.save(addTcnud);


                List<UnrealResult> unrealResults = new ArrayList<>();
                mstmb mstmb = mstmbRepos.findByStock(request.getStock());
                UnrealResult Response = new UnrealResult(
                        addTcnud.getId().getTradeDate(),
                        addTcnud.getId().getDocSeq(),
                        addTcnud.getStock(),
                        mstmbRepos.findByStock(request.getStock()).getStockName(),
                        addTcnud.getPrice(),
                        mstmbRepos.findByStock(request.getStock()).getCurPrice(),
                        addTcnud.getQty(),
                        addTcnud.getQty(),
                        addTcnud.getFee(),
                        addTcnud.getCost(),
                        Calculator.marketValue(mstmb.getCurPrice(), addTcnud.getQty()),
                        Calculator.unrealProfit(mstmb.getCurPrice(), addTcnud.getQty(), addTcnud.getCost()));
                unrealResults.add(Response);
                return new TransactionResponse(unrealResults, "000", "");

        }catch (Exception e){
            return new TransactionResponse(null, "005", "伺服器忙碌中，請稍後嘗試");
        }
    }

    private String check(UnrealProfitRequest request) {
        if (request.getBranchNo().isBlank()) return "branchNo 不得為空";
        if (request.getCustSeq().isBlank()) return "custSeq 不得為空";
        if (request.getStock().isBlank()) {
            return null;
        } else if (null == mstmbRepos.findByStock(request.getStock())) return "Error Stock";
        if (null == tcnudRepos.findByBranchNoAndCustSeqAndStock(request.getBranchNo(), request.getBranchNo(), request.getStock()))
            return "001";

        return null;
    }


}
