package com.mitocode.controller;

import com.mitocode.dto.MedicDTO;
import com.mitocode.dto.PatientDTO;
import com.mitocode.model.Medic;
import com.mitocode.service.IMedicService;
import com.mitocode.util.MapperUtil;
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
@RequestMapping("/medics")
@RequiredArgsConstructor
public class MedicController {

    private final IMedicService service;

    //@Qualifier("medicMapper")
    //private final ModelMapper modelMapper;
    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<MedicDTO>> findAll() throws Exception{
        List<MedicDTO> list = mapperUtil.mapList(service.findAll(), MedicDTO.class, "medicMapper");

        return ResponseEntity.ok(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<MedicDTO> findById(@PathVariable("id") Integer id) throws Exception{
        Medic obj = service.findById(id);
        return ResponseEntity.ok(mapperUtil.map(obj, MedicDTO.class, "medicMapper"));
    }

    @PostMapping
    public ResponseEntity<Medic> save(@Valid @RequestBody MedicDTO dto)throws Exception {
        Medic obj = service.save(mapperUtil.map(dto, Medic.class, "medicMapper"));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getIdMedic()).toUri();
        return ResponseEntity.created(location).build();
    }


    @PutMapping("{id}")
    public ResponseEntity<Medic> update(@Valid @RequestBody MedicDTO dto, @PathVariable Integer id) throws Exception{
        dto.setIdMedic(id);
        Medic obj = service.update(mapperUtil.map(dto, Medic.class, "medicMapper"),id);
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


    /*public Medic convertToEntity(MedicDTO dto){
        return modelMapper.map(dto, Medic.class);
    }

    public MedicDTO convertToDTO(Medic medic){
        return modelMapper.map(medic, MedicDTO.class);
    }*/


}
