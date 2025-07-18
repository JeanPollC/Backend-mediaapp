package com.mitocode.service.impl;

import com.mitocode.model.ConsultExam;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.service.IConsultExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultExamServiceImpl implements IConsultExamService {

    private final IConsultExamRepo repo;

    @Override
    public List<Exam> getExamByConsultId(Integer id) {
        return repo.getExamsByConsultId(id);
    }
}
