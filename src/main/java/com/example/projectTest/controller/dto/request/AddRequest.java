package com.example.projectTest.controller.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddRequest {

    private String tradeDate;

    private String branchNo;
    private String custSeq;
    private String docSeq;
    private String stock;
    private BigDecimal price;
    private BigDecimal qty;


}
