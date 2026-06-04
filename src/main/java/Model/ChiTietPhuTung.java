/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public class ChiTietPhuTung {
    private int MaPhieu;
    private int MaPT;
    private int SoLuong;
    private BigDecimal DonGia;
    private BigDecimal ThanhTien;

    public ChiTietPhuTung(int MaPhieu, int MaPT, int SoLuong, BigDecimal DonGia, BigDecimal ThanhTien) {
        this.MaPhieu = MaPhieu;
        this.MaPT = MaPT;
        this.SoLuong = SoLuong;
        this.DonGia = DonGia;
        this.ThanhTien = ThanhTien;
    }

    public ChiTietPhuTung() {
    }

    public int getMaPhieu() {
        return MaPhieu;
    }

    public int getMaPT() {
        return MaPT;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public BigDecimal getThanhTien() {
        return ThanhTien;
    }

    public void setMaPhieu(int MaPhieu) {
        this.MaPhieu = MaPhieu;
    }

    public void setMaPT(int MaPT) {
        this.MaPT = MaPT;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setDonGia(BigDecimal DonGia) {
        this.DonGia = DonGia;
    }

    public void setThanhTien(BigDecimal ThanhTien) {
        this.ThanhTien = ThanhTien;
    }
    
    @Override
    public String toString() {
        return "ChiTietPhuTung{"
                + "MaPhieu: " + MaPhieu
                + ", MaPT: " + MaPT + '\''
                + ", SoLuong: " + SoLuong + '\''
                + ", DonGia: " + DonGia + '\''
                + ", ThanhTien: " + ThanhTien + '\''
                + '}';
    }
}
