/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import DAO.PhieuSuaChuaDAO;
import DAO.ChiTietDichVuDAO;
import DAO.ChiTietPhuTungDAO;
import DAO.NhanVienDAO;
import Model.KhachHang;
import Model.NhanVien;
import Model.PhieuSuaChua;
import Model.Xe;
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import java.util.List;

public class PhieuSuaChuaService {

    private PhieuSuaChuaDAO pscd;

    public PhieuSuaChuaService() {
        pscd = new PhieuSuaChuaDAO();
    }

    //Lất tất cả
    public List<PhieuSuaChua> getAll() {
        return pscd.getAll();
    }

    //Tìm kiếm bằng mã
    public PhieuSuaChua findByID(int MaPhieu) {
        return pscd.findByID(MaPhieu);
    }

    //Thêm phiếu sửa chữa
    public boolean insert(PhieuSuaChua psc, Connection conn) throws Exception {

        NhanVienService nvs = new NhanVienService();

        psc.setMaNV(nvs.findNhanVienRanhNhat(conn));

        psc.setNgayLap(new java.sql.Date(System.currentTimeMillis()));

        psc.setNgayHoanThanh(null);

        psc.setTrangThai("Đang sửa");

        psc.setTongTien(BigDecimal.ZERO);

        try {
            return pscd.insert(psc, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Xóa phiếu sửa chữa
    public boolean delete(int Maphieu, Connection conn) throws Exception {
        PhieuSuaChua psc = pscd.findByID(Maphieu);
        if (psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành")) {
            throw new Exception("Đơn hàng đã hoàn thành , không thể xóa");
        }
        try {
            return pscd.delete(Maphieu, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Sửa thông tin phiếu sửa chữa
    public boolean update(PhieuSuaChua psc, Connection conn) throws Exception {
        if (psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành")) {
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa");
        }
        try {
            return pscd.update(psc, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Tìm theo mã xe
    public List<PhieuSuaChua> findByMaXe(int MaXe) {
        return pscd.findByMaXe(MaXe);
    }

    // Tìm theo nhân viên
    public List<PhieuSuaChua> findByNhanVien(int maNV) {
        return pscd.findByNhanVien(maNV);
    }

    // Tìm theo trạng thái
    public List<PhieuSuaChua> findByTrangThai(String trangThai) {
        return pscd.findByTrangThai(trangThai);
    }

    // Cập nhật trạng thái
    public boolean updateTrangThai(int maPhieu, String trangThai, Connection conn) {
        try {
            return pscd.updateTrangThai(maPhieu, trangThai, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật tổng tiền
    public boolean updateTongTien(int maPhieu, BigDecimal tongTien, Connection conn) {
        try {
            return pscd.updateTongTien(maPhieu, tongTien, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy phiếu mới nhất
    public PhieuSuaChua getLatestPhieu() {
        return pscd.getLatestPhieu();
    }

    // Tìm theo ngày lập
    public List<PhieuSuaChua> findByNgayLap(Date ngayLap) {
        return pscd.findByNgayLap(ngayLap);
    }

    //Tính tổng tiền
    public BigDecimal tinhTongTien(int MaPhieu) {
        ChiTietDichVuDAO ctdvd = new ChiTietDichVuDAO();
        ChiTietPhuTungDAO ctptd = new ChiTietPhuTungDAO();
        BigDecimal tongDichVu = ctdvd.tongTienDichVu(MaPhieu);
        BigDecimal tongPhuTung = ctptd.tongTienPhuTung(MaPhieu);

        return tongDichVu.add(tongPhuTung);
    }

    //Tạo phiếu
    public boolean taoPhieuSuaChua(String tenKH, String sdt, String loaiXe, String bienSo, Date ngayLap, String trangThai,String HangXe) {
        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // BƯỚC 1: TÌM KHÁCH HÀNG
            KhachHangService khs = new KhachHangService();
            KhachHang kh = khs.findBySDT(sdt,conn);

            if (kh == null) {

                KhachHang khMoi = new KhachHang();
                khMoi.setTenKH(tenKH);
                khMoi.setSoDienThoai(sdt);

                if (!khs.insert(khMoi, conn)) {
                    throw new Exception("Không thể thêm khách hàng");
                }

                kh = khs.findBySDT(sdt,conn);

                if (kh == null) {
                    throw new Exception("Không tìm thấy khách hàng vừa tạo");
                }
            }

            // BƯỚC 2: TÌM XE
            XeService xs = new XeService();
            Xe xe = xs.findByBienSo(bienSo,conn);

            if (xe == null) {

                Xe xeMoi = new Xe();
                xeMoi.setBienSo(bienSo);
                xeMoi.setLoaiXe(loaiXe);
                xeMoi.setMaKH(kh.getMaKH());
                xeMoi.setHangXe(HangXe);
                if (!xs.insert(xeMoi, conn)) {
                    throw new Exception("Không thể thêm xe");
                }

                xe = xs.findByBienSo(bienSo,conn);

                if (xe == null) {
                    throw new Exception("Không tìm thấy xe vừa tạo");
                }
            }

            // BƯỚC 3: TẠO PHIẾU
            PhieuSuaChua phieu = new PhieuSuaChua();

            phieu.setMaXe(xe.getMaXe());
            phieu.setNgayLap(ngayLap);
            phieu.setTrangThai(trangThai);
            phieu.setTongTien(BigDecimal.ZERO);
            NhanVienService nvs = new NhanVienService(); 
        int  nvRanh = nvs.findNhanVienRanhNhat(conn); // Gọi hàm của bạn

        if (nvRanh == -1) {
            throw new Exception("Không thể tạo phiếu vì hiện tại không có nhân viên nào đang rảnh!");
        }
        phieu.setMaNV(nvRanh); // Gán mã nhân viên vào phiếu
            if (!pscd.insert(phieu, conn)) {
                throw new Exception("Không thể tạo phiếu sửa chữa");
            }

            conn.commit();

            return true;

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Lỗi tạo phiếu:");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
            
            

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //update phieu
    public boolean capNhatPhieuSuaChua(PhieuSuaChua phieu, String tenKH, String sdt, String loaiXe, String bienSo, Date ngayLap, String trangThai,String HangXe) {

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            XeService xs = new XeService();
            KhachHangService khs = new KhachHangService();

            // 1. Lấy xe theo phiếu
            Xe xe = xs.findByID(phieu.getMaXe());
            if (xe == null) {
                throw new Exception("Không tìm thấy xe");
            }

            // 2. Lấy khách hàng
            KhachHang kh = khs.findByID(xe.getMaKH());
            if (kh == null) {
                throw new Exception("Không tìm thấy khách hàng");
            }

            // 3. UPDATE KHÁCH HÀNG
            kh.setTenKH(tenKH);
            kh.setSoDienThoai(sdt);

            if (!khs.update(kh, conn)) {
                throw new Exception("Update khách hàng thất bại");
            }

            // 4. UPDATE XE
            xe.setLoaiXe(loaiXe);
            xe.setBienSo(bienSo);
            xe.setHangXe(HangXe);
            if (!xs.update(xe, conn)) {
                throw new Exception("Update xe thất bại");
            }

            // 5. UPDATE PHIẾU
            phieu.setNgayLap(ngayLap);
            phieu.setTrangThai(trangThai);

            if (!update(phieu, conn)) {
                throw new Exception("Update phiếu thất bại");
            }

            conn.commit();
            return true;

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
