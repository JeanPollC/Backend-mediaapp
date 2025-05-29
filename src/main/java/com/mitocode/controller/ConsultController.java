package com.mitocode.controller;

import com.mitocode.dto.ConsultDTO;
import com.mitocode.dto.ConsultListExamDto;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.IConsultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ConsultDTO>> findAll() throws Exception{
        List<ConsultDTO> list = service.findAll().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Consult obj = service.findById(id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ConsultListExamDto dto) throws Exception {
        Consult obj1 = modelMapper.map(dto.getConsult(), Consult.class);
        List<Exam> list = dto.getLstExam().stream().map(ex -> modelMapper.map(ex, Exam.class)).toList();

        Consult obj = service.saveTransactional(obj1, list);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
        return ResponseEntity.created(location).build();
    }




    @PutMapping("/{id}")
    public ResponseEntity<Consult> update(@Valid @RequestBody ConsultDTO dto, @PathVariable Integer id) throws Exception{
        Consult obj = service.update(convertToEntity(dto), id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception{
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("");
    }

    private Consult convertToEntity(ConsultDTO dto) {
        return modelMapper.map(dto, Consult.class);
    }

    private ConsultDTO convertToDto(Consult entity) {
        return modelMapper.map(entity, ConsultDTO.class);
    }

}
