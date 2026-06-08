/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Admin
 */
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;
import Model.ChiTietPhuTung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ChiTietPhuTungDAO {
    //Tao doi tuong
    private ChiTietPhuTung mapResultSetToChiTietPhuTung(ResultSet rs) throws Exception{
        ChiTietPhuTung ctpt = new ChiTietPhuTung();
        
        ctpt.setMaPhieu(rs.getInt("MaPhieu"));
        ctpt.setMaPT(rs.getInt("MaPT"));
        ctpt.setSoLuong(rs.getInt("SoLuong"));
        ctpt.setDonGia(rs.getBigDecimal("DonGia"));
        ctpt.setThanhTien(rs.getBigDecimal("ThanhTien"));
        
        return ctpt;
    }
    
    //Tim kiem theo ma
}
