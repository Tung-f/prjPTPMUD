/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class NhanVien {

    private int MaNV;
    private String TenDangNhap;
    private String MatKhau;
    private String HoTen;
    private String SoDienThoai;
    private String VaiTro;
    private boolean TrangThai;

    public NhanVien(int MaNV, String TenDangNhap, String MatKhau, String HoTen, String SoDienThoai, String VaiTro) {
        this.MaNV = MaNV;
        this.TenDangNhap = TenDangNhap;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.SoDienThoai = SoDienThoai;
        this.VaiTro = VaiTro;
    }

    public NhanVien(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public NhanVien() {
    }

    public int getMaNV() {
        return MaNV;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public void setTenDangNhap(String TenDangNhap) {
        this.TenDangNhap = TenDangNhap;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public void setSoDienThoai(String SoDienThoai) {
        this.SoDienThoai = SoDienThoai;
    }

    public void setVaiTro(String VaiTro) {
        this.VaiTro = VaiTro;
    }

    @Override
    public String toString() {
        return "NhanVien{"
                + "MaNV: " + MaNV
                + ", HoTen: " + HoTen + '\''
                + ", SoDienThoai: " + SoDienThoai + '\''
                + ", VaiTro: " + VaiTro + '\''
                + '}';
    }

}
