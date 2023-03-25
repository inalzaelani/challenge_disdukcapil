package com.enigma.challengedatapenduduk.service;

import com.enigma.challengedatapenduduk.models.entities.Kecamatan;
import com.enigma.challengedatapenduduk.models.entities.Penduduk;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IService<T> {
    public Iterable<T> findAll();
    public T findById(Long id);
    public T create(T entity);
    public T update(T entity,Long id);
    public void delete(Long id);
    public List<T> insertBulk(List<T> entityList);
    public T findByNik(String nik);
    public Iterable<T> findByNama(String nama, Pageable pageable);

}
