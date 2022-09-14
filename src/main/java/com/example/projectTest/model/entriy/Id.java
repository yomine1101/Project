package com.example.projectTest.model.entriy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Id implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private String tradeDate;

    @Column
    private String branchNo;

    @Column
    private String custSeq;

    @Column
    private String docSeq;
}

