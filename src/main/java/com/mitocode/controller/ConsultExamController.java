package com.mitocode.controller;

import ch.qos.logback.core.model.ModelUtil;
import com.mitocode.dto.ExamDTO;
import com.mitocode.model.ConsultExam;
import com.mitocode.model.Exam;
import com.mitocode.service.IConsultExamService;
import com.mitocode.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultexams")
@RequiredArgsConstructor
public class ConsultExamController {

    private final IConsultExamService service;
    private final MapperUtil mapperUtil;

    @GetMapping("/{idConsult}")
    public ResponseEntity<List<ExamDTO>> getConsultsById(@PathVariable("idConsult") Integer idConsult){
        List<ExamDTO> list = mapperUtil.mapList(service.getExamByConsultId(idConsult), ExamDTO.class);

        return ResponseEntity.ok().body(list);
    }

}
