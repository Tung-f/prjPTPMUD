/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */

import java.util.Date;
import java.math.BigDecimal;

public class PhieuSuaChua {

    private int MaPhieu;
    private int MaXe;
    private int MaNV;
    private Date NgayLap;
    private Date NgayHoanThanh;
    private String TrangThai;
    private BigDecimal TongTien;
    private String LoaiXe;
    private String TenKH;

    public PhieuSuaChua(String LoaiXe, String TenKH) {
        this.LoaiXe = LoaiXe;
        this.TenKH = TenKH;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setLoaiXe(String LoaiXe) {
        this.LoaiXe = LoaiXe;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }
    

    public PhieuSuaChua(int MaPhieu, int MaXe, int MaNV, Date NgayLap, Date NgayHoanThanh, String TrangThai, BigDecimal TongTien) {
        this.MaPhieu = MaPhieu;
        this.MaXe = MaXe;
        this.MaNV = MaNV;
        this.NgayLap = NgayLap;
        this.NgayHoanThanh = NgayHoanThanh;
        this.TrangThai = TrangThai;
        this.TongTien = TongTien;
    }

    public PhieuSuaChua() {
    }

    public int getMaPhieu() {
        return MaPhieu;
    }

    public int getMaXe() {
        return MaXe;
    }

    public int getMaNV() {
        return MaNV;
    }

    public Date getNgayLap() {
        return NgayLap;
    }

    public Date getNgayHoanThanh() {
        return NgayHoanThanh;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public BigDecimal getTongTien() {
        return TongTien;
    }

    

    public void setMaPhieu(int MaPhieu) {
        this.MaPhieu = MaPhieu;
    }

    public void setMaXe(int MaXe) {
        this.MaXe = MaXe;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public void setNgayLap(Date NgayLap) {
        this.NgayLap = NgayLap;
    }

    public void setNgayHoanThanh(Date NgayHoanThanh) {
        this.NgayHoanThanh = NgayHoanThanh;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public void setTongTien(BigDecimal TongTien) {
        this.TongTien = TongTien;
    }

    @Override
    public String toString() {
        return "PhieuSuaChua{"
                + "MaPhieu: " + MaPhieu
                + ", MaXe: " + MaXe + '\''
                + ", MaNV: " + MaNV + '\''
                + ", NgayLap: " + NgayLap + '\''
                + ", NgayHoanThanh: " + NgayHoanThanh + '\''
                + ", TrangThai: " + TrangThai + '\''
                + ", TongTien: " + TongTien + '\''
                + '}';
    }
}
