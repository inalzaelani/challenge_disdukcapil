package com.enigma.challengedatapenduduk.repository;

import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KecamatanRepository extends JpaRepository<Kecamatan, Long> {
    Optional<Kecamatan> findByNamaKecamatan(String namaKecamatan);

}
