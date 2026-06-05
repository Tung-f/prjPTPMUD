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
        String sql = "SELECT *FROM NhanVien WHERE MaNV = ?";
        
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
    
}
