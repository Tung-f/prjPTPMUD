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
    public List<Xe> findByBienSo (String keyword){
        List<Xe> list = new ArrayList<>();
        String sql = "SELECT * FROM Xe WHERE BienSo LIKE ?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, "%"+ keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                list.add(mapResultSetToXe(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    //Tim theo ma khach hang
    public List<Xe> getByKhachHang(int MaKH) {
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
    //them xe
     public boolean insert(Xe xe) {
        String sql = "INSERT INTO Xe (BienSo, HangXe, LoaiXe, MaKH) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, xe.getBienSo());
            ps.setString(2, xe.getHangXe());
            ps.setString(3, xe.getLoaiXe());
            ps.setInt(4, xe.getMaKH());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
     }
     
    //cap nhat
    public boolean update(Xe xe) {
        String sql = "UPDATE Xe SET BienSo=?, HangXe=?, LoaiXe=?, MaKH=? WHERE MaXe=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, xe.getBienSo());
            ps.setString(2, xe.getHangXe());
            ps.setString(3, xe.getLoaiXe());
            ps.setInt(4, xe.getMaKH());
            ps.setInt(5, xe.getMaXe());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //xoa
     public boolean delete(int MaXe) {
        String sql = "DELETE FROM Xe WHERE MaXe=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, MaXe);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
     
}
