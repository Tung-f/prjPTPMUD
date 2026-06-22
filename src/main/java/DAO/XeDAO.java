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
import Model.Xe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class XeDAO {
    //Tao doi tuong
    private Xe mapResultSetToXe(ResultSet rs) throws Exception{
        
        Xe x = new Xe();
        
        x.setMaXe(rs.getInt("MaXe"));
        x.setBienSo(rs.getString("BienSo"));
        x.setHangXe(rs.getString("HangXe"));
        x.setLoaiXe(rs.getString("LoaiXe"));
        x.setMaKH(rs.getInt("MaKH"));
        
        return x;
    }
    
    //Lay tat ca xe
    public List<Xe> getAll(){
        
        List<Xe> list = new ArrayList<>();
        String sql = "SELECT * FROM Xe";
        
        try(    
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ){
            while(rs.next())
                list.add(mapResultSetToXe(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Tim kiem theo ma xe
    public Xe findByID(int MaXe){
        String sql = "SELECT * FROM Xe WHERE MaXe = ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaXe);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
                return mapResultSetToXe(rs);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    //Tim kiem theo bien so
    public Xe findByBienSo(String keyword, Connection conn) throws SQLException, Exception {

        String sql = "SELECT * FROM Xe  WHERE BienSo = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, keyword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToXe(rs);
                }
            }
        }
        return null;
    }
    
    //Tim theo ma khach hang
    public List<Xe> findByKhachHang(int MaKH) {
        List<Xe> list = new ArrayList<>();
        String sql = "SELECT * FROM Xe WHERE MaKH = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, MaKH);

            ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(mapResultSetToXe(rs));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //Them xe
    public boolean insert(Xe xe, Connection conn) throws SQLException {

    String sql = "INSERT INTO Xe (BienSo, HangXe, LoaiXe, MaKH) VALUES (?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, xe.getBienSo());
        ps.setString(2, xe.getHangXe());
        ps.setString(3, xe.getLoaiXe());
        ps.setInt(4, xe.getMaKH());

        return ps.executeUpdate() > 0;
    }
}
     
    //Cap nhat
    public boolean update(Xe xe, Connection conn)throws SQLException {
        String sql = "UPDATE Xe SET BienSo=? ,HangXe=? ,LoaiXe=?  WHERE MaKH=? AND MaXe=? ";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, xe.getBienSo());
            ps.setString(2, xe.getHangXe());
            ps.setString(3, xe.getLoaiXe());
            ps.setInt(4, xe.getMaKH());
            ps.setInt(5, xe.getMaXe());

            return ps.executeUpdate() > 0;
        }
    }
    
    //Xoa
    public boolean delete(int MaXe, Connection conn)throws SQLException  {
        String sql = "DELETE FROM Xe WHERE MaXe=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, MaXe);
            return ps.executeUpdate() > 0;
        }
    }
     
}
