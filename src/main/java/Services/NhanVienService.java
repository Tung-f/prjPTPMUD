/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import Model.NhanVien;
import DAO.NhanVienDAO;

import java.util.ArrayList;
import java.util.List;

public class NhanVienService {
    
    private NhanVienDAO nvd;
    
    public NhanVienService(){
        nvd = new NhanVienDAO();
    }
    //Login
    public NhanVien LoginService (String TenDangNhap , String MatKhau) throws Exception{
        if(TenDangNhap == null || TenDangNhap.trim().isEmpty())
            throw new Exception("Tên đăng nhập không được để trống");
        if(MatKhau == null || MatKhau.trim().isEmpty())
            throw new Exception("Mật khẩu không được để trống");
        
        NhanVien nv = nvd.login(TenDangNhap.trim(), MatKhau.trim());
        
        if(nv == null)
            throw new Exception("Sai tài khoản hoặc mật khẩu!");
        if(!nv.isTrangThai())
            throw new Exception("Tài khoản đã bị khóa!");
        
        return nv;
    }
    
    //Thông tin cá nhân
    public NhanVien getInformation(){
        return nvd.findByID(Utils.Auth.user.getMaNV());
    }
    
    //Xem thông tin các nhân viên khác
    public List<NhanVien> getAll(){
        return nvd.getAll();
    }
    
    //Tìm kiếm theo mã
    public NhanVien findByID(int MaNV){
        return nvd.findByID(MaNV);
    }
    
    //Tìm kiếm theo keyword
    public List<NhanVien> findByKeyWord(String keyword){
        return nvd.search(keyword);
    }
    
    //Thêm nhân viên
    public boolean insert (NhanVien nv)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        return nvd.insert(nv);
    }
    
    //Thay đổi trạng thái của nhân viên
    public boolean setTrangThaiNhanVien(int MaNV, boolean TrangThai)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        return nvd.setTrangThaiNhanVien(MaNV, TrangThai);
    }
    
    //Sửa thông tin nhân viên
    public boolean update (NhanVien nv){
        if(Utils.Auth.user.getMaNV() == nv.getMaNV())
            return nvd.update(nv);
        return false;
    }
    
    //Thay đổi mật khẩu
    public boolean updatePassword (String MatKhauCu , String MatKhauMoi, String XacNhanMatKhau)throws Exception{
        if(MatKhauCu.trim().isEmpty())
            throw new Exception("Chưa nhập mật khẩu cũ !");
        if(MatKhauMoi.trim().isEmpty())
            throw new Exception("Chưa nhập mật khẩu mới !");
        if(XacNhanMatKhau.trim().isEmpty())
            throw new Exception("Chưa nhập xác nhập mật khẩu !");
        if(!XacNhanMatKhau.trim().equals(MatKhauMoi))
            throw new Exception("Xác nhận mật khẩu không khớp !");
        if(!Utils.Auth.user.getMatKhau().equals(MatKhauCu))
            throw new Exception("Mật khẩu cũ không đúng !");
        
        return nvd.updatePassword(MatKhauMoi, Utils.Auth.user.getMaNV());
    }
}
