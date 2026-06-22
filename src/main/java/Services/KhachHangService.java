/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */

import DAO.KhachHangDAO;
import Model.KhachHang;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
public class KhachHangService {
    
    private KhachHangDAO khd;
    
    public KhachHangService(){
        khd = new KhachHangDAO();
    }
    
    //Lấy tất cả khách hàng
    public List<KhachHang> getAll(){
        return khd.getAll();
    }
    
    //Tìm kiếm theo mã khách hàng
    public KhachHang findByID (int MaKH){
        return khd.findByID(MaKH);
    }
    
    //Tìm kiếm theo tên
    public List<KhachHang> search (String keyword){
        return khd.search(keyword);
    }
    
    //Thêm khách hàng
    public boolean insert (KhachHang kh,Connection conn){
        try{
            return khd.insert(kh, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Sửa thông tin khách hàng
    public boolean update (KhachHang kh,Connection conn){
        try{
            return khd.update(kh, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    //Tìm theo số điện thoại
    public KhachHang findBySDT(String SDT,Connection conn) throws Exception{
        try{
            return khd.findBySoDienThoai(SDT, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
}
