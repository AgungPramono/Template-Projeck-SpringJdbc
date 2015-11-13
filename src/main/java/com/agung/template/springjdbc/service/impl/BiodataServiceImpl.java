/*
 *  Copyright (c) 2015 Agung Pramono <agungpermadi13@gmail.com || www.github.com/agung pramono>.
 *  All rights reserved.
 * 
 * Silahkan digunakan dengan bebas / dimodifikasi
 * Dengan tetap mencantumkan nama @author dan Referensi / Source
 * Terima Kasih atas Kerjasamanya.
 * 
 *  BiodataServiceImpl.java
 * 
 *  Created on Nov 13, 2015, 9:45:58 PM
 */
package com.agung.template.springjdbc.service.impl;

import com.agung.template.springjdbc.dao.BiodataDao;
import com.agung.template.springjdbc.entity.Biodata;
import com.agung.template.springjdbc.service.BiodataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author agung
 */
@Service("biodataService")
@Transactional(readOnly = true)
public class BiodataServiceImpl implements BiodataService{
    
    @Autowired private BiodataDao biodataDao;

    @Transactional(readOnly = false)
    public void simpan(Biodata b) {
        biodataDao.simpan(b);
    }
    
    @Transactional(readOnly = false)
    public void hapus(Biodata b) {
        biodataDao.hapus(b);
    }
    
    public Biodata cariById(Integer id) {
        return biodataDao.cariById(id);
    }

    public List<Biodata> cariSemua() {
        return biodataDao.cariSemua();
    }

    public List<Biodata> cariByNama(String nama) {
        return biodataDao.cariByNama(nama);
    }

    public Long hitungSemua() {
        return biodataDao.hitungSemua();
    }
    
}
