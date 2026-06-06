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
import Model.KhachHang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
public class KhachHangDAO {
    //Tao doi tuong khach hang
    private KhachHang mapResultSetToKhachHang(ResultSet rs) throws Exception{
        KhachHang kh = new KhachHang();
        
        kh.setMaKH(rs.getInt("MaKH"));
        kh.setTenKH(rs.getString("TenKH"));
        kh.setSoDienThoai(rs.getString("SoDienThoai"));
        kh.setDiaChi(rs.getString("DiaChi"));
        
        return kh;
    }
    
    //Lay tat ca khach hang
    public List<KhachHang> getAll(){
        List<KhachHang> list = new ArrayList<>();
        
        String sql = "SELECT * FROM KhachHang";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ){
            while(rs.next())
                list.add(mapResultSetToKhachHang(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Tim kiem theo ma
    public KhachHang findByID(int MaKH){
        String sql = "SELECT * FROM KhachHang WHERE MaKH = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaKH);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                return mapResultSetToKhachHang(rs);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    //Tim kiem theo ten
    public List<KhachHang> search (String keyword){
        List<KhachHang> list= new ArrayList<>();
        
        String sql = "SELECT * FROM KhachHang WHERE TenKH LIKE ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, "%"+ keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(mapResultSetToKhachHang(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //Them khach hang
    public boolean insert(KhachHang kh){        
        String sql =    "INSERT INTO KhachHang "
                        +"(TenKH, SoDienThoai, DiaChi )"
                        +"VALUES(?,?,?)";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
   
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Cap nhat khach hang
    public boolean update(KhachHang kh){
        String sql =    "UPDATE KhachHang"
                        +" SET TenKH = ?"
                        +",SoDienThoai = ?"
                        +",DiaChi = ?"
                        +" WHERE MaKH = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            ps.setInt(4, kh.getMaKH());
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Xoa khach hang
    public boolean delete(int MaKH){
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaKH);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
