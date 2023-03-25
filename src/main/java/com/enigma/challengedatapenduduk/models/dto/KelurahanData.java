package com.enigma.challengedatapenduduk.models.dto;

import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class KelurahanData {
    @NotBlank(message = "{invalid.kelurahan.name.required}")
    private String namaKelurahan;
    private Kecamatan kecamatan;

    public String getNamaKelurahan() {
        return namaKelurahan;
    }

    public void setNamaKelurahan(String namaKelurahan) {
        this.namaKelurahan = namaKelurahan;
    }

    public Kecamatan getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(Kecamatan kecamatan) {
        this.kecamatan = kecamatan;
    }
}
