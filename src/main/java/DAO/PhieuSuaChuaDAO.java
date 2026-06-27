/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Admin
 */
import Model.DichVu;
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;
import Model.PhieuSuaChua;
import Model.Xe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;


import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;


public class PhieuSuaChuaDAO {

    private PhieuSuaChua mapResultSetToPhieuSuaChua(ResultSet rs) throws Exception {

        PhieuSuaChua psc = new PhieuSuaChua();

        psc.setMaPhieu(rs.getInt("MaPhieu"));
        psc.setMaXe(rs.getInt("MaXe"));
        psc.setMaNV(rs.getInt("MaNV"));
        psc.setNgayLap(rs.getTimestamp("NgayLap"));
        psc.setNgayHoanThanh(rs.getTimestamp("NgayHoanThanh"));
        psc.setTrangThai(rs.getString("TrangThai"));
        psc.setTongTien(rs.getBigDecimal("TongTien"));
        
        return psc;
    }

    // Lay tat ca
    public List<PhieuSuaChua> getAll() {

        List<PhieuSuaChua> list = new ArrayList<>();

        String sql =
            "SELECT p.*, x.LoaiXe, x.MaKH, k.TenKH " +
            "FROM PhieuSuaChua p " +
            "JOIN Xe x ON p.MaXe = x.MaXe " +
            "JOIN KhachHang k ON x.MaKH = k.MaKH";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {
               
                PhieuSuaChua p = mapResultSetToPhieuSuaChua(rs);

                p.setLoaiXe(rs.getString("LoaiXe"));
                p.setTenKH(rs.getString("TenKH"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
}
    // Tìm theo mã phiếu
    public PhieuSuaChua findByID(int maPhieu,Connection conn) {

        String sql = "SELECT * FROM PhieuSuaChua WHERE MaPhieu = ?";

        try (
                
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, maPhieu);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToPhieuSuaChua(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

        // Tìm theo mã xe
    public List<PhieuSuaChua> findByMaXe(int maXe) {

        List<PhieuSuaChua> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuSuaChua WHERE MaXe = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, maXe);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPhieuSuaChua(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm theo nhân viên
    public List<PhieuSuaChua> findByNhanVien(int maNV) {

        List<PhieuSuaChua> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuSuaChua WHERE MaNV = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, maNV);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPhieuSuaChua(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm theo trạng thái
    public List<PhieuSuaChua> findByTrangThai(String trangThai) {

        List<PhieuSuaChua> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuSuaChua WHERE TrangThai = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, trangThai);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPhieuSuaChua(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // Thêm phiếu sửa chữa
    public boolean insert(PhieuSuaChua psc, Connection conn)throws SQLException {

        String sql = "INSERT INTO PhieuSuaChua (MaXe, MaNV, NgayLap, NgayHoanThanh, TrangThai, TongTien) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, psc.getMaXe());
            ps.setInt(2, psc.getMaNV());
            ps.setDate(3,new java.sql.Date(psc.getNgayLap().getTime()));
            
                ps.setDate(4, new java.sql.Date(psc.getNgayLap().getTime()));
            
            ps.setString(5, psc.getTrangThai());
            ps.setBigDecimal(6, psc.getTongTien());
            return ps.executeUpdate() > 0;
        }
    }

    // Cập nhật phiếu sửa chữa
    public boolean update(PhieuSuaChua psc, Connection conn)throws SQLException {

        String sql = "UPDATE PhieuSuaChua SET MaXe = ? , MaNV = ? , NgayLap = ? , NgayHoanThanh = ?, TrangThai = ? , TongTien = ? WHERE MaPhieu = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, psc.getMaXe());
            ps.setInt(2, psc.getMaNV());
             ps.setDate(3,new java.sql.Date(psc.getNgayLap().getTime()));

             ps.setDate(4, new java.sql.Date(psc.getNgayLap().getTime()));
            ps.setString(5, psc.getTrangThai());
            ps.setBigDecimal(6, psc.getTongTien());
            ps.setInt(7, psc.getMaPhieu());
            int row = ps.executeUpdate();

return row > 0;

        }
    }

    // Xóa phiếu sửa chữa
    public boolean delete(int maPhieu, Connection conn)throws SQLException {

        String sql = "DELETE FROM PhieuSuaChua WHERE MaPhieu = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maPhieu);

            return ps.executeUpdate() > 0;

        }
    }

    // Cập nhật trạng thái
    public boolean updateTrangThai(int maPhieu, String trangThai, Connection conn)throws SQLException {

        String sql = "UPDATE PhieuSuaChua SET TrangThai = ? WHERE MaPhieu = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, trangThai);
            ps.setInt(2, maPhieu);

            return ps.executeUpdate() > 0;

        }
    }

    // Cập nhật tổng tiền
    public boolean updateTongTien(int maPhieu,List<DichVu> dsDichVu)throws SQLException {
        BigDecimal tongTien = BigDecimal.ZERO;
        for (DichVu dv : dsDichVu) {
                if (dv.getTienCong() != null){       
                    tongTien = tongTien.add(dv.getTienCong());
                }         
            }
            
        String sql = "UPDATE PhieuSuaChua SET TongTien = ? WHERE MaPhieu = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, tongTien);
            ps.setInt(2, maPhieu);

            return ps.executeUpdate() > 0;

        }
    }

    // Lấy phiếu mới nhất
    public PhieuSuaChua getLatestPhieu() {

        String sql = "SELECT TOP 1 * FROM PhieuSuaChua ORDER BY MaPhieu DESC";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return mapResultSetToPhieuSuaChua(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tìm theo ngày lập
    public List<PhieuSuaChua> findByNgayLap(Date ngayLap) {

        List<PhieuSuaChua> list = new ArrayList<>();

        String sql = "SELECT * FROM PhieuSuaChua WHERE CAST(NgayLap AS DATE) = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setDate(1, ngayLap);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToPhieuSuaChua(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // tìm kiếm
    public List<PhieuSuaChua> timKiem(String keyword) throws Exception {

    List<PhieuSuaChua> list = new ArrayList<>();

    String sql =
        "SELECT DISTINCT p.*, x.LoaiXe, k.TenKH " +
        "FROM PhieuSuaChua p " +
        "JOIN Xe x ON p.MaXe = x.MaXe " +
        "JOIN KhachHang k ON x.MaKH = k.MaKH " +
        "WHERE k.TenKH COLLATE Latin1_General_CI_AI LIKE ? "+
        "OR x.LoaiXe COLLATE Latin1_General_CI_AI LIKE ? "+
        "OR x.BienSo COLLATE Latin1_General_CI_AI LIKE ? ";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)
    ) {

        String search = "%" + keyword.toLowerCase() + "%";

        ps.setString(1, search);
        ps.setString(2, search);
        ps.setString(3, search);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

                PhieuSuaChua p = mapResultSetToPhieuSuaChua(rs);

                p.setLoaiXe(rs.getString("LoaiXe"));
                p.setTenKH(rs.getString("TenKH"));

                list.add(p);
            
        }
    }
        System.out.println("[" + keyword + "]");
    return list;
}
}
