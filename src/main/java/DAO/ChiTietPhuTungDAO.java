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
import java.math.BigDecimal;

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
    public ChiTietPhuTung findByID(int MaPT){
        String sql = "SELECT * FROM ChiTietPhuTung WHERE MaPT = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaPT);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToChiTietPhuTung(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    
    }
    
    //tim kiem theo ma phieu
    public ChiTietPhuTung findByMaPhieu(int MaPhieu){
        String sql = "SELECT * FROM ChiTietPhuTung WHERE MaPhieu = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaPhieu);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToChiTietPhuTung(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    
    }
    
    //Them phu tung
    public boolean insert(ChiTietPhuTung ctpt){
        
        String sql ="INSERT INTO ChiTietPhuTung (MaPhieu,MaPT,SoLuong,DonGia) VALUES (?, ?, ?, ?)";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
                ps.setInt(1, ctpt.getMaPhieu());
                ps.setInt(2, ctpt.getMaPT());
                ps.setInt(3, ctpt.getSoLuong());
                ps.setBigDecimal(4, ctpt.getDonGia());
                return ps.executeUpdate()>0;
            }catch(SQLException e){
               e.printStackTrace();
            }
        return false;
    }
    //Cap nhat chi tiet phu tung
    public boolean update(ChiTietPhuTung ctpt){
        String sql = "UPDATE ChiTietPhuTung SET SoLuong = ? , DonGia = ? WHERE MaPT = ? AND MaPT = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, ctpt.getSoLuong());
            ps.setBigDecimal(2, ctpt.getDonGia());
            ps.setInt(3, ctpt.getMaPhieu());
            ps.setInt(4, ctpt.getMaPT());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //Xoa chi tiet phu tung
    public boolean delete(int MaPhieu, int MaPT){
        String sql = "DELETE FROM ChiTietPhuTung WHERE MaPhieu = ? AND MaPT = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaPhieu);
            ps.setInt(2, MaPT);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
}
