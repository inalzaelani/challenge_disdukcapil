package com.enigma.challengedatapenduduk.repository;

import com.enigma.challengedatapenduduk.models.entities.Kelurahan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, Long> {
    Optional<Kelurahan> findByNamaKelurahan(String namaKelurahan);
}
