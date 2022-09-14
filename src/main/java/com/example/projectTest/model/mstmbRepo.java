package com.example.projectTest.model;

import com.example.projectTest.model.entriy.mstmb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface mstmbRepo extends JpaRepository<mstmb, String> {
    @Query(value = "SELECT * FROM mstmb WHERE Stock=?1",nativeQuery = true)
    mstmb findByStock(String stock);
}
