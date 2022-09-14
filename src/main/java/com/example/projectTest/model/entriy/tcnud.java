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
@Table(name = "tcnud") //現股餘額

public class tcnud {

    @EmbeddedId
    private Id id;

    @Column(name = "Stock")
    private String stock;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Qty")
    private BigDecimal qty;

    @Column(name = "RemainQty")
    private BigDecimal remainQty;

    @Column(name = "Fee")
    private BigDecimal fee;

    @Column(name = "Cost")
    private BigDecimal cost;

    @Column(name = "ModDate")
    private String modDate;

    @Column(name = "ModTime")
    private String modTime;

    @Column(name = "ModUser")
    private String modUser;
}
