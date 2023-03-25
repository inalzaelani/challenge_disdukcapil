package com.enigma.challengedatapenduduk.service;

import com.enigma.challengedatapenduduk.Controller.exception.NotFoundException;
import com.enigma.challengedatapenduduk.models.dto.KelurahanData;
import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.models.entities.Kelurahan;
import com.enigma.challengedatapenduduk.repository.KelurahanRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class KelurahanService implements IService<Kelurahan>{

    @Autowired
    private KelurahanRepository kelurahanRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Iterable<Kelurahan> findAll() {
        try{
            List<Kelurahan> kelurahanList = kelurahanRepository.findAll();
            if(kelurahanList.isEmpty()){
                throw new NotFoundException("Kelurahan not found");
            }else{
                return kelurahanList;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Kelurahan findById(Long id) {
        try {
            return kelurahanRepository.findById(id).get();
        } catch (Exception e) {
            throw new NotFoundException("Kelurahan not found with id "+ id);
        }
    }

    @Override
    public Kelurahan create(Kelurahan kelurahan) {
        Optional<Kelurahan> existingKelurahan = kelurahanRepository.findByNamaKelurahan(kelurahan.getNamaKelurahan());
        if(existingKelurahan.isPresent()){
            throw new IllegalArgumentException("Kelurahan with name "+ kelurahan.getNamaKelurahan() + " already exists");
        }
        return kelurahanRepository.save(kelurahan);
    }

    public Kelurahan update(Kelurahan kelurahan, Long id) {
        Optional<Kelurahan> kelurahanOptional = kelurahanRepository.findById(id);
        if (kelurahanOptional.isEmpty()) {
            throw new NotFoundException("Kecamatan not found with id " + id);
        }

        Optional<Kelurahan> existingKelurahan = kelurahanRepository.findByNamaKelurahan(kelurahan.getNamaKelurahan());
        if (existingKelurahan.isPresent()) {
            throw new IllegalArgumentException("Kelurahan with name " + kelurahan.getNamaKelurahan() + " already exists.");
        }
        Kelurahan updatedKelurahan = kelurahanOptional.get();
        updatedKelurahan.setNamaKelurahan(kelurahan.getNamaKelurahan());
        return kelurahanRepository.save(updatedKelurahan);
    }

    @Override
    public void delete(Long id) {
        Optional<Kelurahan> kelurahanOptional = kelurahanRepository.findById(id);
        if(kelurahanOptional.isPresent()){
            kelurahanRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Kelurahan not found with id "+ id);
        }
    }

    @Override
    public List<Kelurahan> insertBulk(List<Kelurahan> kelurahanList) {
        List<Kelurahan> savedKelurahans = new ArrayList<>();

        for (Kelurahan kelurahan : kelurahanList) {
            Optional<Kelurahan> existingKelurahan = kelurahanRepository.findByNamaKelurahan(kelurahan.getNamaKelurahan());
            if (existingKelurahan.isPresent()) {
                System.out.println("Kelurahan with name " + kelurahan.getNamaKelurahan() + " already exists. Skipping...");
            } else {
                savedKelurahans.add(kelurahanRepository.save(kelurahan));
            }
        }
        if (savedKelurahans.isEmpty()) {
            throw new RuntimeException("None of the kelurahan could be saved because all already exist.");
        }

        return savedKelurahans;
    }

    @Override
    public Kelurahan findByNik(String nik) {
        return null;
    }

    @Override
    public Iterable<Kelurahan> findByNama(String nama, Pageable pageable) {
        return null;
    }
}
