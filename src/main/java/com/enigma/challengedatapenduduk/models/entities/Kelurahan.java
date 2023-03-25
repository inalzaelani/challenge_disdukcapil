package com.enigma.challengedatapenduduk.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "kelurahan")
public class Kelurahan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaKelurahan;
    @ManyToOne
    @JoinColumn(name = "kecamatan_id")
    private Kecamatan kecamatan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
