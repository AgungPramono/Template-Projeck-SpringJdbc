/*
 *  Copyright (c) 2015 Agung Pramono <agungpermadi13@gmail.com || www.github.com/agung pramono>.
 *  All rights reserved.
 * 
 * Silahkan digunakan dengan bebas / dimodifikasi
 * Dengan tetap mencantumkan nama @author dan Referensi / Source
 * Terima Kasih atas Kerjasamanya.
 * 
 *  BiodataDao.java
 * 
 *  Created on Nov 13, 2015, 9:11:20 PM
 */
package com.agung.template.springjdbc.dao;

import com.agung.template.springjdbc.entity.Biodata;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author agung
 */
@Repository
public class BiodataDao {
    private static final String SQL_UPDATE = "update biodata set nama=:nama,alamat=:alamat,tanggal_lahir=:tanggal_lahir,"
                                            + "email=:email"
                                            + " where id=:id";
    private static final String SQL_HAPUS = "delete from biodata where id=?";
    private static final String SQL_CARI_SEMUA = "select * from biodata";
    private static final String SQL_CARI_BY_ID = "select * from  biodata where id=?";
    private static final String SQL_CARI_BY_NAMA = "select * from biodata where lower(nama) like ?";
    private static final String SQL_HITUNG_SEMUA = "select count(*) from biodata";
    
    private JdbcTemplate JdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleInsert;
    
     @Autowired
    public void setDataSource(DataSource dataSource){
        this.JdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("biodata")
                .usingGeneratedKeyColumns("id");
    }
    
    public void simpan(Biodata b) {
       if(b.getId() ==null){
            SqlParameterSource produkParameter = new BeanPropertySqlParameterSource(b);
            Number idBaru = simpleInsert.executeAndReturnKey(produkParameter);
            b.setId(idBaru.intValue());//set id yg baru ke objeck biodata
           /**
            Map<String,Object>parameter = new HashMap<String, Object>();
            parameter.put("nama", b.getNama());
            parameter.put("alamat", b.getAlamat());
            parameter.put("tanggal_lahir", b.getTanggalLahir());
            parameter.put("email",b.getEmail());
            Number newId = simpleInsert.executeAndReturnKey(parameter);
            b.setId(newId.intValue());*/
       }else{
           String sql="update biodata set nama=:nama,alamat=:alamat,tanggal_lahir=:tanggal_lahir,"
                   + "email=:email"
                   + " where id=:id";
           SqlParameterSource param = new MapSqlParameterSource()
                   .addValue("nama", b.getNama())
                   .addValue("alamat", b.getAlamat())
                   .addValue("tanggal_lahir", b.getTanggalLahir())
                   .addValue("email", b.getEmail())
                   .addValue("id", b.getId());
           namedParameterJdbcTemplate.update(sql, param);
       }       
    }
    
    public void hapus(Biodata b){
        //String sql = "delete from biodata where id=?";
        //JdbcTemplate.update(sql, new Object[]{id});
        JdbcTemplate.update(SQL_UPDATE, b.getId());
    }
    
    public List<Biodata> cariSemua(){
        try {
            //String sql = "select * from biodata";
            return JdbcTemplate.query(SQL_CARI_SEMUA, new ResultSetMapper());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Biodata> cariByNama(String nama){
        return JdbcTemplate.query(SQL_CARI_BY_NAMA, 
                new ResultSetMapper(),
                "%" + nama.toLowerCase() + "%");
    }
    
    public Biodata cariById(Integer id){
        try {
            return JdbcTemplate.queryForObject(SQL_CARI_BY_ID, new ResultSetMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Long hitungSemua(){
        return JdbcTemplate.queryForObject(SQL_HITUNG_SEMUA, Long.class);
    }
    
    private class ResultSetMapper implements RowMapper<Biodata>{

        public Biodata mapRow(ResultSet rs, int rowNum) throws SQLException {
           Biodata biodata = new Biodata();
           biodata.setNama(rs.getString("nama"));
           biodata.setTanggalLahir(rs.getDate("tanggal_lahir"));
           biodata.setAlamat(rs.getString("alamat"));
           biodata.setEmail(rs.getString("email"));
           biodata.setId(rs.getInt("id"));
           return biodata;
        }
        
    }
}
