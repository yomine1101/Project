package com.example.projectTest.model;
import com.example.projectTest.model.entriy.Id;
import com.example.projectTest.model.entriy.tcnud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface tcnudRepo extends JpaRepository<tcnud, Id>, JpaSpecificationExecutor<tcnud> {


    @Query(value = "SELECT * FROM tcnud WHERE BranchNo = ?1 AND CustSeq = ?2 AND Stock = ?3  ;",nativeQuery = true)
    List<tcnud> findByBranchNoAndCustSeqAndStock(String branchNo, String custSeq, String stock);

    @Query(value = "SELECT * FROM tcnud WHERE BranchNo = ?1 AND CustSeq = ?2 ;",nativeQuery = true)
    List<tcnud> findByBranchNoAndCustSeq(String branchNo, String custSeq);

    //FROM tcnud 去除一樣的 stock
    @Query(value = "SELECT DISTINCT Stock FROM tcnud WHERE BranchNo = ?1 AND CustSeq = ?2 ORDER BY Stock  ;",nativeQuery = true)
    List<String> getAllStock(String branchNo, String custSeq);
}
