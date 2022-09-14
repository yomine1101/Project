package com.example.projectTest.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sum {
    private String stock;
    private String stockName;
    private BigDecimal nowPrice;
    private BigDecimal sumRemainQty;
    private BigDecimal sumFee;
    private BigDecimal sumCost;
    private BigDecimal sumMarketValue;
    private BigDecimal sumUnrealProfit;
    private List<UnrealResult> detailList;

}
