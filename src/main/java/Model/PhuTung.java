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
public class PhuTung {
    private int MaPT;
    private String TenPT;
    private int SoLuongTon;
    private BigDecimal DonGiaNhap;
    private BigDecimal DonGiaBan;
    private int MucCanhBao;

    public PhuTung(int MaPT, String TenPT, int SoLuongTon, BigDecimal DonGiaNhap, BigDecimal DonGiaBan, int MucCanhBao) {
        this.MaPT = MaPT;
        this.TenPT = TenPT;
        this.SoLuongTon = SoLuongTon;
        this.DonGiaNhap = DonGiaNhap;
        this.DonGiaBan = DonGiaBan;
        this.MucCanhBao = MucCanhBao;
    }

    public PhuTung() {
    }

    public int getMaPT() {
        return MaPT;
    }

    public String getTenPT() {
        return TenPT;
    }

    public int getSoLuongTon() {
        return SoLuongTon;
    }

    public BigDecimal getDonGiaNhap() {
        return DonGiaNhap;
    }

    public BigDecimal getDonGiaBan() {
        return DonGiaBan;
    }

    public int getMucCanhBao() {
        return MucCanhBao;
    }

    public void setMaPT(int MaPT) {
        this.MaPT = MaPT;
    }

    public void setTenPT(String TenPT) {
        this.TenPT = TenPT;
    }

    public void setSoLuongTon(int SoLuongTon) {
        this.SoLuongTon = SoLuongTon;
    }

    public void setDonGiaNhap(BigDecimal DonGiaNhap) {
        this.DonGiaNhap = DonGiaNhap;
    }

    public void setDonGiaBan(BigDecimal DonGiaBan) {
        this.DonGiaBan = DonGiaBan;
    }

    public void setMucCanhBao(int MucCanhBao) {
        this.MucCanhBao = MucCanhBao;
    }
    
    @Override
    public String toString() {
        return "PhuTung{"
                + "MaPT: " + MaPT
                + ", TenPT: " + TenPT + '\''
                + ", SoLuongTon: " + SoLuongTon + '\''
                + ", DonGiaNhap: " + DonGiaNhap + '\''
                + ", DonGiaBan: " + DonGiaBan + '\''
                + '}';
    }
    
}
