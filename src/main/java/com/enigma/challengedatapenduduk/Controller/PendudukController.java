package com.enigma.challengedatapenduduk.Controller;

import com.enigma.challengedatapenduduk.models.dto.PendudukData;
import com.enigma.challengedatapenduduk.models.dto.SearchData;
import com.enigma.challengedatapenduduk.models.entities.Penduduk;
import com.enigma.challengedatapenduduk.models.response.SuccessResponse;
import com.enigma.challengedatapenduduk.service.PendudukService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/penduduk")
public class PendudukController {
    @Autowired
    private PendudukService pendudukService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity getAll(){
        return ResponseEntity.ok(pendudukService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        Penduduk penduduk = pendudukService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " found", penduduk));
    }
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody PendudukData pendudukData){
        Penduduk penduduk = modelMapper.map(pendudukData, Penduduk.class);
        Penduduk newPenduduk = pendudukService.create(penduduk);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("Data with id " +newPenduduk.getId()+ " created", newPenduduk));
    }
    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody PendudukData pendudukData, @PathVariable Long id){
        Penduduk penduduk = modelMapper.map(pendudukData, Penduduk.class);
        pendudukService.update(penduduk, id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " updated", penduduk));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        pendudukService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with id " +id+ " deleted", null));
    }

    @PostMapping("/bulkinsert")
    public ResponseEntity bulkInsert(@RequestBody List<@Valid PendudukData> pendudukDataList){
        List<Penduduk> pendudukList = pendudukDataList.stream()
                .map(pendudukData -> modelMapper.map(pendudukData, Penduduk.class))
                .collect(Collectors.toList());
        pendudukService.insertBulk(pendudukList);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data inserted", pendudukList));
    }
    @PostMapping("/search/nik")
    public ResponseEntity findByNik(@RequestBody SearchData searchData){
        modelMapper.map(searchData, Penduduk.class);
        Penduduk penduduk = pendudukService.findByNik(searchData.getSearchKey());
        return  ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with nik " +searchData.getSearchKey()+ " found", penduduk));
    }

    @PostMapping("/search/nama")
    public ResponseEntity findByName(@RequestBody SearchData searchData,
                                     @RequestParam(required = false, defaultValue = "10") int size,
                                     @RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "nama") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        List<Penduduk> pendudukList = pendudukService.findByNama(searchData.getSearchKey(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Data with nama " +searchData.getSearchKey()+ " found", pendudukList));
    }
}
