package com.example.projectTest.service;

import com.example.projectTest.controller.dto.request.UpdateMstmbRequest;
import com.example.projectTest.model.entriy.mstmb;
import com.example.projectTest.model.mstmbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;

@Service
public class UpdateService {

    @Autowired
    mstmbRepo mstmbRepos;
    public String update(UpdateMstmbRequest request) {
        mstmb mstmb = mstmbRepos.findByStock(request.getStock());
        mstmb.setCurPrice(request.getCurPrice().setScale(2, RoundingMode.HALF_UP));
        mstmbRepos.save(mstmb);
        return "ok";
    }
}
