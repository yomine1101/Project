package com.example.projectTest.model;

import com.example.projectTest.model.entriy.Id;
import com.example.projectTest.model.entriy.hcmio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface hcmioRepo extends JpaRepository<hcmio, Id> , JpaSpecificationExecutor<hcmio> {


    @Query(value = "SELECT COUNT(*) FROM hcmio WHERE TradeDate = ?1 AND BranchNo =?2 AND CustSeq = ?3 AND DocSeq = ?4  ", nativeQuery = true)
    int findByTradeDateAndBranchNoAndCustSeqAndStock(String tradeDate, String branchNo, String custSeq, String docSeq);
}
