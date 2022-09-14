package com.example.projectTest.controller;

import com.example.projectTest.controller.dto.request.AddRequest;
import com.example.projectTest.controller.dto.request.UnrealProfitRequest;
import com.example.projectTest.controller.dto.response.SumResponse;
import com.example.projectTest.controller.dto.response.TransactionResponse;
import com.example.projectTest.service.UnrealProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/unreal")
public class tcnudController {

    @Autowired
    UnrealProfitService unrealProfitService;

    @PostMapping("/detail")
    public TransactionResponse getUnrealList(@RequestBody UnrealProfitRequest request){
        return unrealProfitService.unrealProfit(request);

    }

    @PostMapping("/sum")
    public SumResponse getSumList(@RequestBody UnrealProfitRequest request){
        return unrealProfitService.sum(request);

    }

    @PostMapping("/add")
    public TransactionResponse getAddList(@RequestBody AddRequest request){
        return unrealProfitService.addHcmioAndTcnud(request);

    }
}
