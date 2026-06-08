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
import Model.DichVu;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
public class DichVuDAO {
    //Tao doi tuong
    private DichVu mapResultSetToDichVu(ResultSet rs) throws Exception{
        DichVu dv = new DichVu();
        
        dv.setMaDV(rs.getInt("MaDV"));
        dv.setTenDV(rs.getString("TenDV"));
        dv.setTienCong(rs.getBigDecimal("TienCong"));
        
        return dv;
    }
    
    //Lay tat ca
    public List<DichVu> getAll(){
        String sql = "SELECT * FROM DichVu ";
        List<DichVu> list = new ArrayList<>();
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ){
            while(rs.next())
                list.add(mapResultSetToDichVu(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Tim kiem theo ma
    public DichVu findByID(int MaDV){
        String sql = "SELECT * FROM DichVu WHERE MaDV = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaDV);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                return mapResultSetToDichVu(rs);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    //Tim kiem theo ten
    public List<DichVu> search (String keyword){
        List<DichVu> list= new ArrayList<>();
        
        String sql = "SELECT * FROM Dichvu WHERE TenDV LIKE ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, "%"+ keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(mapResultSetToDichVu(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Them dich vu
    public boolean insert(DichVu dv){        
        String sql ="INSERT INTO DichVu (TenDV, TienCong) VALUES(?,?)";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
   
            ps.setString(1, dv.getTenDV());
            ps.setBigDecimal(2, dv.getTienCong());
            
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Cap nhat dich vu
    public boolean update(DichVu dv){
        String sql = "UPDATE DichVu SET TenDV = ? , TienCong = ? WHERE MaDV = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, dv.getTenDV());
            ps.setBigDecimal(2, dv.getTienCong());
            ps.setInt(3, dv.getMaDV());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Xoa dich vu
    public boolean delete(int MaDV){
        String sql = "DELETE FROM DichVu WHERE MaDV = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaDV);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
