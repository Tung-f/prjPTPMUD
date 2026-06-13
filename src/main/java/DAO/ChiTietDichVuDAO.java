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
import Model.ChiTietDichVu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
public class ChiTietDichVuDAO {
    private ChiTietDichVu mapResultSetToChiTietDichVu(ResultSet rs)throws Exception{
        ChiTietDichVu ctdv = new ChiTietDichVu();
        
        ctdv.setMaPhieu(rs.getInt("MaPhieu"));
        ctdv.setMaDV(rs.getInt("MaDV"));
        ctdv.setDonGia(rs.getBigDecimal("DonGia"));
        return ctdv;
    }
    
    public ChiTietDichVu findByID(int MaDV){
        String sql = "SELECT * FROM ChiTietDichVu WHERE MaDV = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaDV);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToChiTietDichVu(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //tim kiem theo ma phieu
    public ChiTietDichVu findByMaPhieu(int MaPhieu){
        String sql = "SELECT * FROM ChiTietDichVu WHERE MaPhieu = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaPhieu);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToChiTietDichVu(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //Them phu tung
    public boolean insert(ChiTietDichVu ctdv){
        
        String sql ="INSERT INTO ChiTietDichVu (MaPhieu,MaDV,DonGia) VALUES (?, ?, ?)";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
                ps.setInt(1, ctdv.getMaPhieu());
                ps.setInt(2, ctdv.getMaDV());
                ps.setBigDecimal(4, ctdv.getDonGia());
                return ps.executeUpdate()>0;
            }catch(Exception e){
               e.printStackTrace();
            }
        return false;
    }
    //Cap nhat chi tiet dich vu
    public boolean update(ChiTietDichVu ctdv){
        String sql = "UPDATE ChiTietDichVu SET DonGia = ? WHERE MaPhieu = ? AND MaDV = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setBigDecimal(1, ctdv.getDonGia());
            ps.setInt(2, ctdv.getMaPhieu());
            ps.setInt(3, ctdv.getMaDV());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //Xoa chi tiet dich vu
    public boolean delete(int MaPhieu , int MaDV){
        String sql = "DELETE FROM ChiTietDichVu WHERE MaPhieu = ? AND MaDV = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaPhieu);
            ps.setInt(2, MaDV);
            return ps.executeUpdate()>0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
}
