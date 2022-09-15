package com.example.projectTest.controller.dto.response;

import com.example.projectTest.model.entriy.mstmb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.MacSpi;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private mstmb mstmb;
    private String mesage;

}
