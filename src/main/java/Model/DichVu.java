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

    public DichVu(int MaDV, String TenDV, BigDecimal TienCong) {
        this.MaDV = MaDV;
        this.TenDV = TenDV;
        this.TienCong = TienCong;
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

    public void setMaDV(int MaDV) {
        this.MaDV = MaDV;
    }

    public void setTenDV(String TenDV) {
        this.TenDV = TenDV;
    }

    public void setTienCong(BigDecimal TienCong) {
        this.TienCong = TienCong;
    }
    
    @Override
    public String toString() {
        return "DichVu{"
                + "MaDV: " + MaDV
                + ", TenDV: " + TenDV + '\''
                + ", TienCong: " + TienCong + '\''
                + '}';
    }
}
