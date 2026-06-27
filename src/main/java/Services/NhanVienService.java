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
import java.sql.Connection;
import java.sql.SQLException;

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
    public boolean insert (NhanVien nv,Connection conn)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        try{
            return nvd.insert(nv, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Tìm nhân viên rảnh nhất

    public int findNhanVienRanhNhat(Connection conn) throws Exception {
    int maNV = nvd.findNhanVienRanhNhat(conn);

    if (maNV == -1) {
        throw new Exception("Không có nhân viên nào khả dụng!");
    }

    return maNV; 
}

    
    //Thay đổi trạng thái của nhân viên
    public boolean setTrangThaiNhanVien(int MaNV, boolean TrangThai,Connection conn)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        try{
            return nvd.setTrangThaiNhanVien(MaNV, TrangThai, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Sửa thông tin nhân viên
    public boolean update (NhanVien nv,Connection conn){
        if(Utils.Auth.user.getMaNV() == nv.getMaNV())
            try{
            return nvd.update(nv, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
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
        
        boolean result = nvd.updatePassword(MatKhauMoi, Utils.Auth.user.getMaNV());

        if(result){
            Utils.Auth.user.setMatKhau(MatKhauMoi);
        }

        return result;
            }
    
    public boolean delete(int MaNV, Connection conn)throws Exception{

    if(!Utils.Auth.user.getVaiTro()
            .equalsIgnoreCase("Admin"))
        throw new Exception("Bạn không có quyền này!");

    try{
        return nvd.delete(MaNV, conn);
    }
    catch(SQLException e){
        e.printStackTrace();
    }

    return false;
}
}
