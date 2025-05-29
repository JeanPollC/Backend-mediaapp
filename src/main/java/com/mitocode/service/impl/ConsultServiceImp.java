package com.mitocode.service.impl;

import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.repo.IConsutRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IConsultService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultServiceImp extends CRUDImpl<Consult, Integer> implements IConsultService {

    private final IConsutRepo consultRepo;
    private final IConsultExamRepo ceRepo;

    @Override
    protected IGenericRepo<Consult, Integer> getRepo() {
        return consultRepo;
    }

    @Transactional
    @Override
    public Consult saveTransactional(Consult consult, List<Exam> exams) {
        consultRepo.save(consult); //GUARDANDO EL MAESTRO DETALLE
        exams.forEach(ex -> ceRepo.saveExam(consult.getIdConsult(), ex.getIdExam()));

        return consult;
    }
}














