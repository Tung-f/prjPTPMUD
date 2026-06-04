/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class KhachHang {
    private int MaKH;
    private String TenKH;
    private String SoDienThoai;
    private String DiaChi;

    public KhachHang(int MaKH, String TenKH, String SoDienThoai, String DiaChi) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.SoDienThoai = SoDienThoai;
        this.DiaChi = DiaChi;
    }

    public KhachHang() {
    }

    public int getMaKH() {
        return MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }
    
    @Override
    public String toString() {
        return "KhachHang{"
                + "MaKH: " + MaKH
                + ", TenKH: " + TenKH + '\''
                + ", SoDienThoai: " + SoDienThoai + '\''
                + ", DiaChi: " + DiaChi + '\''
                + '}';
    }
}
