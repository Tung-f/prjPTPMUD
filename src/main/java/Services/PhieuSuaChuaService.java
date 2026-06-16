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
import Model.PhieuSuaChua;
import java.math.BigDecimal;
import java.sql.Date;

import java.util.ArrayList;
import java.util.List;
public class PhieuSuaChuaService {
    private PhieuSuaChuaDAO pscd;
    
    public PhieuSuaChuaService(){
        pscd = new PhieuSuaChuaDAO();
    }
    
    //Lất tất cả
    public List<PhieuSuaChua> getAll(){
        return pscd.getAll();
    }
    
    //Tìm kiếm bằng mã
    public PhieuSuaChua findByID(int MaPhieu){
        return pscd.findByID(MaPhieu);
    }
    
    //Thêm phiếu sửa chữa
    public boolean insert (PhieuSuaChua psc){
        return pscd.insert(psc);
    }
    
    //Xóa phiếu sửa chữa
    public boolean delete (int Maphieu)throws Exception{
        PhieuSuaChua psc = pscd.findByID(Maphieu);
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception ("Đơn hàng đã hoàn thành , không thể xóa");
        return pscd.delete(Maphieu);
    }
    
    //Sửa thông tin phiếu sửa chữa
    public boolean update (PhieuSuaChua psc)throws Exception{
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception ("Đơn hàng đã hoàn thành , không thể sửa");
        return pscd.update(psc);
    }
    
    //Tìm theo mã xe
    public List<PhieuSuaChua> findByMaXe(int MaXe){
        return pscd.findByMaXe(MaXe);
    }
    
    // Tìm theo nhân viên
    public List<PhieuSuaChua> findByNhanVien(int maNV){
        return pscd.findByNhanVien(maNV);
    }
    
    // Tìm theo trạng thái
    public List<PhieuSuaChua> findByTrangThai(String trangThai){
        return pscd.findByTrangThai(trangThai);
    }
    
    // Cập nhật trạng thái
    public boolean updateTrangThai(int maPhieu, String trangThai) {
        return pscd.updateTrangThai(maPhieu, trangThai);
    }
    
    // Cập nhật tổng tiền
    public boolean updateTongTien(int maPhieu, BigDecimal tongTien){
        return pscd.updateTongTien(maPhieu, tongTien);
    }
    
    // Lấy phiếu mới nhất
    public PhieuSuaChua getLatestPhieu(){
        return pscd.getLatestPhieu();
    }
    
    // Tìm theo ngày lập
    public List<PhieuSuaChua> findByNgayLap(Date ngayLap){
        return pscd.findByNgayLap(ngayLap);
    }
    
    //Tính tổng tiền
    public BigDecimal tinhTongTien (int MaPhieu){
        ChiTietDichVuDAO ctdvd = new ChiTietDichVuDAO();
        ChiTietPhuTungDAO ctptd = new ChiTietPhuTungDAO();
        BigDecimal tongDichVu = ctdvd.tongTienDichVu(MaPhieu);
        BigDecimal tongPhuTung = ctptd.tongTienPhuTung(MaPhieu);

    return tongDichVu.add(tongPhuTung);
    }
}
