package com.enigma.challengedatapenduduk.Controller;

import com.enigma.challengedatapenduduk.models.dto.KelurahanData;
import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.models.entities.Kelurahan;
import com.enigma.challengedatapenduduk.models.response.SuccessResponse;
import com.enigma.challengedatapenduduk.service.KelurahanService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/kelurahan")
public class KelurahanController {
    @Autowired
    private KelurahanService kelurahanService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAll(){
        Iterable<Kelurahan> kelurahanList = kelurahanService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(kelurahanList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        return ResponseEntity.ok(kelurahanService.findById(id));
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody KelurahanData kelurahanData){
        Kelurahan kelurahan = modelMapper.map(kelurahanData, Kelurahan.class);
        Kelurahan newKelurahan = kelurahanService.create(kelurahan);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Data with id " +newKelurahan.getId()+ " created", newKelurahan));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody KelurahanData kelurahanData){
        Kelurahan kelurahan = modelMapper.map(kelurahanData, Kelurahan.class);
        kelurahanService.update(kelurahan, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " updated", kelurahan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        kelurahanService.delete(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Data with id " +id+ " deleted", null));
    }

    @PostMapping("/bulkinsert")
    public ResponseEntity bulkInsert(@RequestBody List<@Valid KelurahanData> kelurahanDataList){
        List<Kelurahan> kelurahanList = kelurahanDataList.stream()
                .map(kelurahanData -> modelMapper.map(kelurahanData, Kelurahan.class))
                .collect(Collectors.toList());
        kelurahanService.insertBulk(kelurahanList);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Data inserted", kelurahanList));
    }


}
