package com.mitocode.controller;

import com.mitocode.dto.SpecialtyDTO;
import com.mitocode.model.Specialty;
import com.mitocode.service.ISpeacialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final ISpeacialtyService service;

    @Qualifier("defaultMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> findAll() throws Exception{
        List<SpecialtyDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Specialty obj = service.findById(id);
        return ResponseEntity.ok(convertToDto(obj));
    }

    @PostMapping
    public ResponseEntity<Specialty> save(@Valid @RequestBody SpecialtyDTO dto) throws Exception{
        Specialty obj = service.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getIdSpecialty()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Specialty> update(@Valid @RequestBody SpecialtyDTO dto, @PathVariable Integer id) throws Exception{
        dto.setIdSpecialty(id);
        Specialty obj = service.update(convertToEntity(dto),id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) throws Exception{
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<java.lang.String> delete() {
        return ResponseEntity.ok("");
    }


    private Specialty convertToEntity (SpecialtyDTO dto){
        return modelMapper.map(dto,Specialty.class);
    }

    private SpecialtyDTO convertToDto (Specialty specialty){
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }
}
