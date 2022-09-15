package com.example.projectTest.service;

import com.example.projectTest.controller.dto.request.StockRequest;
import com.example.projectTest.controller.dto.response.StockResponse;
import com.example.projectTest.model.entriy.mstmb;
import com.example.projectTest.model.mstmbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachingStockService {
    @Autowired
    mstmbRepo mstmbRepos;

    @Cacheable(cacheNames = "cacheStock", key = "#request.getStock()")
    public StockResponse cacheStock(StockRequest request){
        if(null == mstmbRepos.findByStock(request.getStock()))
            return new StockResponse(null, "Stock錯誤");
        mstmb mstmb = mstmbRepos.findByStock(request.getStock());
        return new StockResponse(mstmb, "Stock正確");
    }
}
