package com.example.projectTest.model.entriy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mstmb") //股票資訊
public class mstmb {

    @Id
    @Column(name = "Stock")
    private String stock;

    @Column(name = "StockName")
    private String stockName;

    /* 市場別 T O R */
    @Column(name = "MarketType")
    private String marketType;

    /* 現價 */
    @Column(name = "CurPrice")
    private BigDecimal curPrice;

    /* 參考價 */
    @Column(name = "RefPrice")
    private BigDecimal refPrice;

    /* 幣別 */
    @Column(name = "Currency")
    private String currency;

    @Column(name = "ModDate")
    private String modDate;
    @Column(name = "ModTime")
    private String modTime;
    @Column(name = "ModUser")
    private String modUser;
}
