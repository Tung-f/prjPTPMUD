/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */

import DAO.XeDAO;
import Model.Xe;
import DAO.PhieuSuaChuaDAO;
import Model.PhieuSuaChua;
        
import java.util.List;
import java.util.ArrayList;

public class XeService {
    private XeDAO x;
    
    public XeService(){
        x = new XeDAO();
    }
    
    //Lấy tất cả thông tin các xe
    public List<Xe> getAll(){
        return x.getAll();
    }
    
    //Tìm kiếm theo mã xe
    public Xe findByID (int MaXe){
        return x.findByID(MaXe);
    }
    
    //Tìm kiếm theo biển số
    public List<Xe> findByBienSo (String keyword){
        return x.findByBienSo(keyword);
    }
    
    //Tìm kiếm theo mã khách hàng
    public List<Xe> findByMaKH (int MaKH){
        return x.findByKhachHang(MaKH);
    }
    
    //Thêm xe
    public boolean insert (Xe xe){
        return x.insert(xe);
    }
    
    //Sửa thông tin  xe
    public boolean update (Xe xe){
        return x.update(xe);
    }
    
}
