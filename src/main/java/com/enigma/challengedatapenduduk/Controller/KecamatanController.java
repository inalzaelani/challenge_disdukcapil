package com.enigma.challengedatapenduduk.Controller;

import com.enigma.challengedatapenduduk.models.dto.KecamatanData;
import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.models.response.SuccessResponse;
import com.enigma.challengedatapenduduk.service.KecamatanService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kecamatan")
@Validated
public class KecamatanController {
    @Autowired
    private KecamatanService kecamatanService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(kecamatanService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Kecamatan kecamatan = kecamatanService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " found", kecamatan));
    }
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody KecamatanData kecamatanData){
        Kecamatan kecamatan = modelMapper.map(kecamatanData, Kecamatan.class);
        Kecamatan newKecamatan = kecamatanService.create(kecamatan);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Data with id " +newKecamatan.getId()+ " created", newKecamatan));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody KecamatanData kecamatanData, @PathVariable Long id){
        Kecamatan kecamatan = modelMapper.map(kecamatanData, Kecamatan.class);
        kecamatanService.update(kecamatan, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " updated", kecamatan));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        kecamatanService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " deleted", null));
    }
    @PostMapping("/bulkinsert")
    public ResponseEntity bulkInsert(@RequestBody List<@Valid KecamatanData> kecamatanData){
        List<Kecamatan> kecamatans = kecamatanData.stream()
                .map(kecamatan -> modelMapper.map(kecamatan, Kecamatan.class))
                .collect(Collectors.toList());
        kecamatanService.insertBulk(kecamatans);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data inserted", null));
    }
}
