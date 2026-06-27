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
import Model.DichVu;
import java.math.BigDecimal;

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
    public List<ChiTietDichVu> findByMaPhieu(int MaPhieu){
        List<ChiTietDichVu> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietDichVu WHERE MaPhieu = ?";
        
         try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaPhieu);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToChiTietDichVu(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //Them chi tiet dich vu
    public boolean insert(ChiTietDichVu ctdv, Connection conn)throws SQLException{
        
        String sql ="INSERT INTO ChiTietDichVu (MaPhieu,MaDV,DonGia) VALUES (?, ?, ?)";
        try(
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
                ps.setInt(1, ctdv.getMaPhieu());
                ps.setInt(2, ctdv.getMaDV());
                ps.setBigDecimal(3, ctdv.getDonGia());
                int row = ps.executeUpdate();
                System.out.println("Cap nhat"+row);
                return row>0;
            }
    }
    //Cap nhat chi tiet dich vu
    public boolean update(ChiTietDichVu ctdv, Connection conn)throws SQLException{
        String sql = "UPDATE ChiTietDichVu SET DonGia = ? WHERE MaPhieu = ? AND MaDV = ?";
        
        try(
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setBigDecimal(1, ctdv.getDonGia());
            ps.setInt(2, ctdv.getMaPhieu());
            ps.setInt(3, ctdv.getMaDV());
            return ps.executeUpdate()>0;
        }
    }
    //Xoa chi tiet dich vu
    public boolean delete(int MaPhieu , Connection conn)throws SQLException{
        String sql = "DELETE FROM ChiTietDichVu WHERE MaPhieu = ?";
        
        try(
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaPhieu);
           
            int row = ps.executeUpdate();
            System.out.println("row="+row);
            return true;
        }
        
    }
    //Tính tổng tiền dịch vụ
    public BigDecimal tongTienDichVu(int maPhieu) {

    String sql = "SELECT SUM(DonGia) AS TongTien FROM ChiTietDichVu WHERE MaPhieu = ?";

    try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        ps.setInt(1, maPhieu);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            BigDecimal tongTien = rs.getBigDecimal("TongTien");

            if (tongTien != null) {
                return tongTien;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return BigDecimal.ZERO;
}
    public List<DichVu> getDichVuTheoPhieu(int maPhieu) throws Exception {

        List<DichVu> list = new ArrayList<>();

        String sql = "SELECT dv.MaDV, dv.TenDV, ctdv.DonGia FROM ChiTietDichVu ctdv JOIN DichVu dv ON dv.MaDV = ctdv.MaDV WHERE ctdv.MaPhieu = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                DichVu dv = new DichVu();

                dv.setMaDV(rs.getInt("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setTienCong(rs.getBigDecimal("DonGia"));

                list.add(dv);
            }
        }

        return list;
    }
}
