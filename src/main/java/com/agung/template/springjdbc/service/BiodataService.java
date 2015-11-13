/*
 *  Copyright (c) 2015 Agung Pramono <agungpermadi13@gmail.com || www.github.com/agung pramono>.
 *  All rights reserved.
 * 
 * Silahkan digunakan dengan bebas / dimodifikasi
 * Dengan tetap mencantumkan nama @author dan Referensi / Source
 * Terima Kasih atas Kerjasamanya.
 * 
 *  BiodataService.java
 * 
 *  Created on Nov 13, 2015, 9:45:08 PM
 */
package com.agung.template.springjdbc.service;

import com.agung.template.springjdbc.entity.Biodata;
import java.util.List;

/**
 *
 * @author agung
 */

public interface BiodataService {
    void simpan(Biodata b);
    void hapus(Biodata b);
    Biodata cariById(Integer id);
    List<Biodata> cariSemua();
    List<Biodata> cariByNama(String nama);
    Long hitungSemua();
    
}
