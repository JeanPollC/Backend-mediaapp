package com.mitocode.controller;

import com.mitocode.dto.*;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.service.IConsultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;

    @Qualifier("consultMapper")
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

    //////////////QUERYS/////////////////////

    @PostMapping("/search/others")
    public ResponseEntity<List<ConsultDTO>> searchOthers(@RequestBody FilterConsultDTO dto){
        List<ConsultDTO> list = service.search(dto.getDni(), dto.getFullname()).stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/dates")
    public ResponseEntity<List<ConsultDTO>> searchByDates (
        @RequestParam(value = "date1") String date1,
        @RequestParam(value = "date2") String date2
    ){
        List<ConsultDTO> list = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2)).stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/getProcedureNative")
    public ResponseEntity<List<ConsultProcDTO>> callProcedureNative(){
        return ResponseEntity.ok(service.callProcedureOrFunctionNative());
    }

    @GetMapping("/getProcedureProjection")
    public ResponseEntity<List<IConsultProcDTO>> callProcedureProjection() {
        return ResponseEntity.ok(service.callProcedureOrFunctionProjection());
    }

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport() throws Exception{
        return ResponseEntity.ok(service.generateReport());
    }

    private Consult convertToEntity(ConsultDTO dto) {
        return modelMapper.map(dto, Consult.class);
    }

    private ConsultDTO convertToDto(Consult entity) {
        return modelMapper.map(entity, ConsultDTO.class);
    }

}
