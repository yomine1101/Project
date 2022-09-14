package com.example.projectTest.service;

import org.hibernate.type.BinaryType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class Calculator {
    /* Amt 價金*/
    public static BigDecimal amt(BigDecimal price, BigDecimal qty){
        return price.multiply(qty);
    }

    /* Fee 手續費*/
    public static BigDecimal fee(BigDecimal amt){
        BigDecimal fee = new BigDecimal("0.001425");
        return amt.multiply(fee).setScale(0, RoundingMode.HALF_UP);
    }

    /* Tax 交易稅*/
    public static BigDecimal tax(BigDecimal amt){
        BigDecimal tax = new BigDecimal(("0.003"));
        return amt.multiply(tax).setScale(0, RoundingMode.HALF_UP);
    }

    /* NetAmt */
    public static BigDecimal netAmt(BigDecimal amt, BigDecimal fee, BigDecimal tax, String bsType){
        if("S".equalsIgnoreCase(bsType)){
            return amt.subtract(tax.add(fee));
        }
        return amt.add(fee);
    }

    /* marketValue */
    public static BigDecimal marketValue(BigDecimal curPrice, BigDecimal qty){
        BigDecimal amt = amt(curPrice,qty);
        return (amt.subtract(fee(amt)).subtract(tax(amt)).setScale(0, RoundingMode.HALF_UP));

    }
    /* unrealProfit */
    public static BigDecimal unrealProfit(BigDecimal curPrice, BigDecimal qty, BigDecimal cost){
        return marketValue(curPrice, qty).subtract(cost).setScale(0, RoundingMode.HALF_UP);

    }

    public static String modeDate(){
        SimpleDateFormat md = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();
        return md.format(dateObj);
    }

    public static String modTime(){
        SimpleDateFormat mt = new SimpleDateFormat("hhmmss");
        Date current = new Date();
        return mt.format(current);
    }

}
