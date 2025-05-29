package com.mitocode.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultListExamDto {

    private ConsultDTO consult;
    private List<ExamDTO> lstExam;
}
