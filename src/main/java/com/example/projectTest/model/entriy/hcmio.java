package com.example.projectTest.model.entriy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hcmio")

public class hcmio {

    @EmbeddedId
    private Id id;


    @Column(name = "Stock")
    private String stock;
    @Column(name = "BsType")
    private String bsType;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Qty")
    private BigDecimal qty;

    @Column(name = "Amt")
    private BigDecimal amt;

    @Column(name = "Fee")
    private BigDecimal fee;

    @Column(name = "Tax")
    private BigDecimal tax;

    @Column(name = "StinTax")
    private BigDecimal stinTax;

    @Column(name = "NetAmt")
    private BigDecimal netAmt;

    @Column(name = "ModDate")
    private String modDate;

    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;


}
