/*
 *  Copyright (c) 2015 Agung Pramono <agungpermadi13@gmail.com || www.github.com/agung pramono>.
 *  All rights reserved.
 * 
 * Silahkan digunakan dengan bebas / dimodifikasi
 * Dengan tetap mencantumkan nama @author dan Referensi / Source
 * Terima Kasih atas Kerjasamanya.
 * 
 *  BiodataTest.java
 * 
 *  Created on Nov 13, 2015, 9:59:11 PM
 */
package com.agung.template.springboot.biodatatest;

import com.agung.template.springjdbc.entity.Biodata;
import com.agung.template.springjdbc.service.BiodataService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.sql.DataSource;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author agung
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/config/spring-config.xml")
@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = "/data/data.sql"
)
public class BiodataTest {
    
    @Autowired private DataSource ds;
    @Autowired private BiodataService bs;
    
    @Test
    public void testSimpan() throws SQLException{
        Biodata b = new Biodata();
        
        b.setNama("biodata-test1");
        b.setTanggalLahir(new Date());
        b.setAlamat("address-test");
        b.setEmail("biodata-test1@gmail.com");
        
        bs.simpan(b);
        
        // cek generate id penjualan
        assertNotNull(b.getId());
        
        String sql = "select count(*) as jumlah from biodata where email = 'biodata-test1@gmail.com'";
        Connection conn = ds.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql);
        Assert.assertTrue(rs.next());
        
        Long jumlah = rs.getLong("jumlah");
        Assert.assertEquals(1L, jumlah.longValue());
    }
    
    //@Test
    public void testHapus() throws SQLException{
        Biodata b = new Biodata();
        b.setId(16);
        
        bs.hapus(b);
        String sql = "select * from biodata where id =?";
        Connection con = ds.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, b.getId());
        ResultSet rs = ps.executeQuery();
        
        
        Assert.assertFalse(rs.next());
        //Assert.assertNull(b);
        
        con.close();
    }
    
    @Test
    public void testCariById() throws SQLException{
        String sql = "select * from biodata where email = 'biodata-test0@gmail.com'";
        Connection conn = ds.getConnection();
        ResultSet rs = conn.createStatement().executeQuery(sql);
        
        Integer id = rs.getInt("id");
        
        Biodata b = bs.cariById(id);
        
        Assert.assertNotNull(b);
        Assert.assertEquals("address-test0", b.getAlamat());
        
        conn.close();
    }
    
}
