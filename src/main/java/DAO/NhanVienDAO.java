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
import Model.NhanVien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    //Tao doi tuong nhan vien tu ResultSet
    private NhanVien mapResultSetToNhanVien(ResultSet rs) throws Exception {
        NhanVien nv = new NhanVien();

        nv.setMaNV(rs.getInt("MaNV"));
        nv.setTenDangNhap(rs.getString("TenDangNhap"));
        nv.setMatKhau(rs.getString("MatKhau"));
        nv.setHoTen(rs.getString("HoTen"));
        nv.setSoDienThoai(rs.getString("SoDienThoai"));
        nv.setVaiTro(rs.getString("VaiTro"));
        nv.setTrangThai(rs.getBoolean("TrangThai"));

        return nv;
    }

    //Lay tat ca nhan vien
    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();

        String sql = "SELECT * FROM NhanVien";
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ) {

            while (rs.next()) {
                list.add(mapResultSetToNhanVien(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //Tim nhan vien theo ma
    public NhanVien findByID(int MaNV){
        String sql = "SELECT * FROM NhanVien WHERE MaNV = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaNV);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToNhanVien(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    
    }
    //Tim kiem theo ho ten
    public List<NhanVien> search(String keyword){
        List<NhanVien> list = new ArrayList<>();
        
        String sql = "SELECT * FROM NhanVien WHERE HoTen LIKE ?";
        
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
            ){
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(mapResultSetToNhanVien(rs));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Them nhan vien
    public boolean insert(NhanVien nv){
        
        String sql =    "INSERT INTO NhanVien " +
                        "(TenDangNhap, MatKhau, HoTen, " +
                        "SoDienThoai, VaiTro, TrangThai) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
                ps.setString(1, nv.getTenDangNhap());
                ps.setString(2, nv.getMatKhau());
                ps.setString(3, nv.getHoTen());
                ps.setString(4, nv.getSoDienThoai());
                ps.setString(5, nv.getVaiTro());
                ps.setBoolean(6,nv.isTrangThai());
                
                return ps.executeUpdate()>0;
            }catch(SQLException e){
               e.printStackTrace();
            }
        return false;
    }
    
    //Cap nhat nhan vien
    public boolean update(NhanVien nv){
        String sql =    "UPDATE NhanVien"
                        +" SET TenDangNhap = ?"
                        +",MatKhau = ?"
                        +",HoTen = ?"
                        +",SoDienThoai = ?"
                        +",VaiTro = ?"
                        +",TrangThai = ?"
                        +" WHERE MaNV = ?";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
            ){
            
            ps.setString(1, nv.getTenDangNhap());
            ps.setString(2, nv.getMatKhau());
            ps.setString(3, nv.getHoTen());
            ps.setString(4, nv.getSoDienThoai());
            ps.setString(5, nv.getVaiTro());
            ps.setBoolean(6, nv.isTrangThai());
            ps.setInt(7, nv.getMaNV());

            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false; 
    }
    
    //Xoa nhan vien
    public boolean delete(int MaNV){
        String sql =" DELETE FROM NhanVien WHERE MaNV = ?";
        
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
            ){
            ps.setInt(1, MaNV);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Dang Nhap
    public NhanVien login(String username , String password){
        String sql =    "SELECT * FROM NhanVien "
                        +"WHERE TenDangNhap = ?"
                        +"AND MatKhau = ? "
                        +"AND TrangThai = 1";
        
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
            ){
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return mapResultSetToNhanVien(rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
            return null;
    }
}
