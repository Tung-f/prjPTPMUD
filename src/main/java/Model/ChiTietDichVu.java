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
public class ChiTietDichVu {
    private int MaPhieu;
    private int MaDV;
    private BigDecimal DonGia;

    public ChiTietDichVu(int MaPhieu, int MaDV, BigDecimal DonGia) {
        this.MaPhieu = MaPhieu;
        this.MaDV = MaDV;
        this.DonGia = DonGia;
    }

    public ChiTietDichVu() {
    }

    public int getMaPhieu() {
        return MaPhieu;
    }

    public int getMaDV() {
        return MaDV;
    }

    public BigDecimal getDonGia() {
        return DonGia;
    }

    public void setMaPhieu(int MaPhieu) {
        this.MaPhieu = MaPhieu;
    }

    public void setMaDV(int MaDV) {
        this.MaDV = MaDV;
    }

    public void setDonGia(BigDecimal DonGia) {
        this.DonGia = DonGia;
    }
    
    @Override
    public String toString() {
        return "ChiTietPhuDichVu{"
                + "MaPhieu: " + MaPhieu
                + ", MaDV: " + MaDV + '\''
                + ", DonGia: " + DonGia + '\''
                + '}';
    }
}
