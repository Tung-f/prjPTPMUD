/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class Xe {
    private int MaXe;
    private String BienSo;
    private String HangXe;
    private String LoaiXe;
    private String MaKH;

    public Xe(int MaXe, String BienSo, String HangXe, String LoaiXe, String MaKH) {
        this.MaXe = MaXe;
        this.BienSo = BienSo;
        this.HangXe = HangXe;
        this.LoaiXe = LoaiXe;
        this.MaKH = MaKH;
    }

    public Xe() {
    }

    public int getMaXe() {
        return MaXe;
    }

    public String getBienSo() {
        return BienSo;
    }

    public String getHangXe() {
        return HangXe;
    }

    public String getLoaiXe() {
        return LoaiXe;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaXe(int MaXe) {
        this.MaXe = MaXe;
    }

    public void setBienSo(String BienSo) {
        this.BienSo = BienSo;
    }

    public void setHangXe(String HangXe) {
        this.HangXe = HangXe;
    }

    public void setLoaiXe(String LoaiXe) {
        this.LoaiXe = LoaiXe;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }
    
    @Override
    public String toString() {
        return "Xe{" +
                "MaXe=" + MaXe +
                ", BienSo='" + BienSo + '\'' +
                ", HangXe='" + HangXe + '\'' +
                ", LoaiXe='" + LoaiXe + '\'' +
                ", MaKH=" + MaKH +
                '}';
    }
}
