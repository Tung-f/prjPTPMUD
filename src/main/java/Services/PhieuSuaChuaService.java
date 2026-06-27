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
import Model.ChiTietDichVu;
import Model.DichVu;
import Model.KhachHang;
import Model.NhanVien;
import Model.PhieuSuaChua;
import Model.Xe;
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public PhieuSuaChua findByID(int MaPhieu,Connection conn) {
        return pscd.findByID(MaPhieu,conn);
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
        PhieuSuaChua psc = pscd.findByID(Maphieu,conn);
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
    public boolean updateTongTien(int maPhieu,List<DichVu> dsDichVu) {
        try {
            return pscd.updateTongTien(maPhieu,dsDichVu);
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
    public boolean taoPhieuSuaChua(String tenKH, String sdt, String loaiXe, String bienSo, Date ngayLap, String trangThai, String hangXe, List<DichVu> dsDichVu) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            //---------------------- KHÁCH HÀNG ----------------------//
            KhachHangService khs = new KhachHangService();

            KhachHang kh = khs.findBySDT(sdt, conn);

            if (kh == null) {

                KhachHang khMoi = new KhachHang();
                khMoi.setTenKH(tenKH);
                khMoi.setSoDienThoai(sdt);

                if (!khs.insert(khMoi, conn)) {
                    throw new Exception("Không thể thêm khách hàng.");
                }

                kh = khs.findBySDT(sdt, conn);

                if (kh == null) {
                    throw new Exception("Không tìm thấy khách hàng vừa tạo.");
                }
            }

            //---------------------- XE ----------------------//
            XeService xs = new XeService();

            Xe xe = xs.findByBienSo(bienSo, conn);

            if (xe == null) {

                Xe xeMoi = new Xe();

                xeMoi.setBienSo(bienSo);
                xeMoi.setLoaiXe(loaiXe);
                xeMoi.setHangXe(hangXe);
                xeMoi.setMaKH(kh.getMaKH());

                if (!xs.insert(xeMoi, conn)) {
                    throw new Exception("Không thể thêm xe.");
                }

                xe = xs.findByBienSo(bienSo, conn);

                if (xe == null) {
                    throw new Exception("Không tìm thấy xe vừa tạo.");
                }
            }

            //---------------------- NHÂN VIÊN ----------------------//
            NhanVienService nvs = new NhanVienService();

            int maNV = nvs.findNhanVienRanhNhat(conn);

            if (maNV == -1) {
                throw new Exception("Hiện không có nhân viên rảnh.");
            }

            //---------------------- PHIẾU ----------------------//
            PhieuSuaChua phieu = new PhieuSuaChua();

            phieu.setMaXe(xe.getMaXe());
            phieu.setNgayLap(ngayLap);
            phieu.setTrangThai(trangThai);
            phieu.setMaNV(maNV);
            phieu.setTongTien(BigDecimal.ZERO);

            if (!pscd.insert(phieu, conn)) {
                throw new Exception("Không thể tạo phiếu sửa chữa.");
            }

            //---------------------- CHI TIẾT DỊCH VỤ ----------------------//
            ChiTietDichVuService ctdvService = new ChiTietDichVuService();

            BigDecimal tongTien = BigDecimal.ZERO;

            if (dsDichVu != null) {

                for (DichVu dv : dsDichVu) {

                    ChiTietDichVu ct = new ChiTietDichVu();

                    ct.setMaPhieu(phieu.getMaPhieu());
                    ct.setMaDV(dv.getMaDV());
                    ct.setDonGia(dv.getTienCong());

                    if (!ctdvService.insert(ct, conn)) {
                        throw new Exception("Không thể thêm chi tiết dịch vụ.");
                    }

                    tongTien = tongTien.add(dv.getTienCong());
                }
            }

            //---------------------- CẬP NHẬT TỔNG TIỀN ----------------------//
            phieu.setTongTien(tongTien);

            if (!pscd.updateTongTien(phieu.getMaPhieu(),dsDichVu)) {
                throw new Exception("Không thể cập nhật tổng tiền.");
            }

            //---------------------- COMMIT ----------------------//
            conn.commit();

            return true;

        } catch (Exception e) {
    System.out.println("====== LỖI CHI TIẾT TẠI ĐÂY ======");
    e.printStackTrace(); // Nó sẽ chỉ rõ lỗi do Insert ChiTiet hay do gì
    System.out.println("==================================");

    try {
        if (conn != null) {
            conn.rollback();
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false;
}
         finally {

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
    public boolean capNhatPhieuSuaChua(PhieuSuaChua phieu,
            String tenKH,
            String sdt,
            String loaiXe,
            String bienSo,
            Date ngayLap,
            String trangThai,
            String hangXe,
            List<DichVu> dsDichVu) {

        Connection conn = null;

        try {

            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            XeService xs = new XeService();
            KhachHangService khs = new KhachHangService();
            ChiTietDichVuService ctdvService = new ChiTietDichVuService();

            //------------------- XE --------------------//
            Xe xe = xs.findByID(phieu.getMaXe());

            if (xe == null) {
                throw new Exception("Không tìm thấy xe.");
            }

            //---------------- KHÁCH HÀNG ----------------//
            KhachHang kh = khs.findByID(xe.getMaKH());

            if (kh == null) {
                throw new Exception("Không tìm thấy khách hàng.");
            }

            kh.setTenKH(tenKH);
            kh.setSoDienThoai(sdt);

            if (!khs.update(kh, conn)) {
                throw new Exception("Cập nhật khách hàng thất bại.");
            }

            //-------------------- XE --------------------//
            xe.setLoaiXe(loaiXe);
            xe.setBienSo(bienSo);
            xe.setHangXe(hangXe);

            if (!xs.update(xe, conn)) {
                throw new Exception("Cập nhật xe thất bại.");
            }

            //------------------- PHIẾU ------------------//
            phieu.setNgayLap(ngayLap);
            phieu.setTrangThai(trangThai);

            
            if (!update(phieu, conn)) {
                throw new Exception("Cập nhật phiếu thất bại.");
            }
            System.out.println("3");

            //---------------- XÓA CHI TIẾT CŨ ----------------//
            boolean a= ctdvService.delete(phieu.getMaPhieu(), conn);
            System.out.println("1");
            if (!a) {
                throw new Exception("Không thể xóa chi tiết dịch vụ cũ.");
            }

            //---------------- THÊM LẠI CHI TIẾT ----------------//
            for (DichVu dv : dsDichVu) {

                ChiTietDichVu ct = new ChiTietDichVu();

                ct.setMaPhieu(phieu.getMaPhieu());
                ct.setMaDV(dv.getMaDV());
                ct.setDonGia(dv.getTienCong());
                
                boolean b =ctdvService.insert(ct, conn);
                System.out.println("2");
                if (!b) {
                    throw new Exception("Không thể thêm chi tiết dịch vụ.");
                }

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
    
    
    public List<PhieuSuaChua> timKiem(String keyword) throws Exception {
        return pscd.timKiem(keyword);
    }
}
