package com.enigma.challengedatapenduduk.service;

import com.enigma.challengedatapenduduk.Controller.exception.NotFoundException;
import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.models.entities.Penduduk;
import com.enigma.challengedatapenduduk.repository.PendudukRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PendudukService implements IService<Penduduk>{
    @Autowired
    private PendudukRepository pendudukRepository;
    @Override
    public Iterable findAll() {
        try {
            List<Penduduk> pendudukList = pendudukRepository.findAll();
            if (pendudukList.isEmpty()){
                throw new NotFoundException("Penduduk not found");
            }else {
                return pendudukList;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Penduduk findById(Long id) {
        try {
            return pendudukRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("Penduduk not found with id "+ id);
        }
    }

    @Override
    public Penduduk create(Penduduk penduduk) {
        Optional<Penduduk> existingPenduduk = pendudukRepository.findByNik(penduduk.getNik());
        if (existingPenduduk.isPresent()) {
            throw new IllegalArgumentException("Penduduk with nik " + penduduk.getNik() + " already exists.");
        }
        return pendudukRepository.save(penduduk);
    }

    @Override
    public Penduduk update(Penduduk penduduk, Long id) {
        Optional<Penduduk> cekPenduduk = pendudukRepository.findById(id);
        if (cekPenduduk.isEmpty()) {
            throw new NotFoundException("Kecamatan not found with id " + id);
        }

        Optional<Penduduk> existingPenduduk = pendudukRepository.findByNik(penduduk.getNik());
        if (existingPenduduk.isPresent()) {
            throw new IllegalArgumentException("Penduduk with nik " + penduduk.getNik() + " already exists.");
        }

        Optional<Penduduk> pendudukOptional = pendudukRepository.findById(id);
        if (pendudukOptional.isPresent()) {
            Penduduk updatedPenduduk = pendudukOptional.get();
            updatedPenduduk.setNik(penduduk.getNik());
            updatedPenduduk.setNama(penduduk.getNama());
            updatedPenduduk.setJenisKelamin(penduduk.getJenisKelamin());
            updatedPenduduk.setTanggalLahir(penduduk.getTanggalLahir());
            updatedPenduduk.setTempatLahir(penduduk.getTempatLahir());
            return pendudukRepository.save(updatedPenduduk);
        } else {
            throw new NotFoundException("Penduduk not found with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Penduduk> pendudukOptional = pendudukRepository.findById(id);
        if(pendudukOptional.isPresent()){
            pendudukRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Penduduk not found with id "+ id);
        }
    }

    @Override
    public List<Penduduk> insertBulk(List<Penduduk> pendudukList) {
        List<Penduduk> insertedPendudukList = new ArrayList<>();

        for (Penduduk penduduk : pendudukList) {
            String nik = penduduk.getNik();
            if (pendudukRepository.findByNik(nik).isPresent()) {
                throw new IllegalArgumentException("NIK " + nik + " already exists");
            }

            Penduduk insertedPenduduk = create(penduduk);
            insertedPendudukList.add(insertedPenduduk);
        }

        return insertedPendudukList;
    }

    public Penduduk findByNik(String nik){
          Optional<Penduduk> pendudukOptional = pendudukRepository.findByNik(nik);
            if (pendudukOptional.isPresent()){
                return pendudukOptional.get();
            }else {
                throw new NotFoundException("Penduduk not found with nik "+ nik);
            }
    }
    public List<Penduduk> findByNama(String nama, Pageable pageable){
        return pendudukRepository.findByNamaContains(nama,pageable);
    }
}
