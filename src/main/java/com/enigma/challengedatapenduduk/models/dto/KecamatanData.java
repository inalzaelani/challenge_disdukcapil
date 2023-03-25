package com.enigma.challengedatapenduduk.models.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class KecamatanData {


    @NotBlank(message = "{invalid.kecamatan.name.required}")
    private String namaKecamatan;

    public String getNamaKecamatan() {
        return namaKecamatan;
    }

    public void setNamaKecamatan(String namaKecamatan) {
        this.namaKecamatan = namaKecamatan;
    }
}
