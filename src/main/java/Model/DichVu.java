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
public class DichVu {

    private int MaDV;
    private String TenDV;
    private BigDecimal TienCong;
    private boolean TrangThai;

    public DichVu(int MaDV, String TenDV, BigDecimal TienCong, boolean TrangThai) {
        this.MaDV = MaDV;
        this.TenDV = TenDV;
        this.TienCong = TienCong;
        this.TrangThai = TrangThai;
    }

    public DichVu() {
    }

    public int getMaDV() {
        return MaDV;
    }

    public String getTenDV() {
        return TenDV;
    }

    public BigDecimal getTienCong() {
        return TienCong;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setMaDV(int MaDV) {
        this.MaDV = MaDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public void setTienCong(BigDecimal TienCong) {
        this.TienCong = TienCong;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    @Override
    public String toString() {
        return "DichVu{"
                + "MaDV: " + MaDV
                + ", TenDV: " + TenDV + '\''
                + ", TienCong: " + TienCong + '\''
                + ", TrangThai: " + TrangThai + '\''
                + '}';
    }
}
