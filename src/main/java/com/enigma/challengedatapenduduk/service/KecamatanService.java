package com.enigma.challengedatapenduduk.service;

import com.enigma.challengedatapenduduk.Controller.exception.NotFoundException;
import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.repository.KecamatanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KecamatanService implements IService<Kecamatan>{

    @Autowired
    private KecamatanRepository kecamatanRepository;

    @Override
    public Iterable<Kecamatan> findAll(){
        try {
            List<Kecamatan> kecamatanList = kecamatanRepository.findAll();
            if (kecamatanList.isEmpty()){
                throw new NotFoundException("Kecamatan not found");
            }else {
                return kecamatanList;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Kecamatan findById(Long id){
        try {
            return kecamatanRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("Kecamatan not found with id "+ id);
        }

    }

    @Override
    public Kecamatan create(Kecamatan kecamatan){
        Optional<Kecamatan> existingKecamatan = kecamatanRepository.findByNamaKecamatan(kecamatan.getNamaKecamatan());
        if (existingKecamatan.isPresent()) {
            throw new IllegalArgumentException("Kecamatan with name " + kecamatan.getNamaKecamatan() + " already exists.");
        }
        return kecamatanRepository.save(kecamatan);
    }

    @Override
    public Kecamatan update(Kecamatan kecamatan, Long id) {
        Optional<Kecamatan> kecamatanOptional = kecamatanRepository.findById(id);
        if (kecamatanOptional.isEmpty()) {
            throw new NotFoundException("Kecamatan not found with id " + id);
        }

        Optional<Kecamatan> existingKecamatan = kecamatanRepository.findByNamaKecamatan(kecamatan.getNamaKecamatan());
        if (existingKecamatan.isPresent() && !existingKecamatan.get().getId().equals(id)) {
                    throw new IllegalArgumentException("Kecamatan with name " + kecamatan.getNamaKecamatan() + " already exists.");
        }
        Kecamatan updatedKecamatan = kecamatanOptional.get();
        updatedKecamatan.setNamaKecamatan(kecamatan.getNamaKecamatan());
        return kecamatanRepository.save(updatedKecamatan);
    }

    @Override
    public void delete(Long id) {
        Optional<Kecamatan> kecamatanOptional = kecamatanRepository.findById(id);
        if(kecamatanOptional.isPresent()){
            kecamatanRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Kecamatan not found with id "+ id);
        }
    }

    @Override
    public List<Kecamatan> insertBulk(List<Kecamatan> kecamatanList) {
        List<Kecamatan> savedKecamatans = new ArrayList<>();

        for (Kecamatan kecamatan : kecamatanList) {
            Optional<Kecamatan> existingKecamatan = kecamatanRepository.findByNamaKecamatan(kecamatan.getNamaKecamatan());
            if (existingKecamatan.isPresent()) {
                System.out.println("Kecamatan with name " + kecamatan.getNamaKecamatan() + " already exists. Skipping...");
            } else {
                savedKecamatans.add(kecamatanRepository.save(kecamatan));
            }
        }
        if (savedKecamatans.isEmpty()) {
            throw new RuntimeException("None of the kecamatan could be saved because all already exist.");
        }

        return savedKecamatans;
    }

    @Override
    public Kecamatan findByNik(String nik) {
        return null;
    }

    @Override
    public Iterable<Kecamatan> findByNama(String nama, Pageable pageable) {
        return null;
    }


}
