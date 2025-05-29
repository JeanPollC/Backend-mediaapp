package com.mitocode.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class ConsultExamPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_cosult", nullable = false, foreignKey = @ForeignKey(name = "FK_CEPK_CONSULT"))
    private Consult consult;

    @ManyToOne
    @JoinColumn(name = "id_exam", nullable = false, foreignKey = @ForeignKey(name = "FK_CEPK_EXAM"))
    private Exam exam;
}
