/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import DAO.DichVuDAO;
import Model.DichVu;

import java.util.ArrayList;
import java.util.List;

public class DichVuService {
    private DichVuDAO dvd;
    public DichVuService(){
        dvd = new DichVuDAO();
    }
    //Lấy tất cả
    public List<DichVu> getAll(){
        return dvd.getAll();
    }
    
    //Tìm kiếm theo mã dịch vụ
    public DichVu findByID (int MaDV){
        return dvd.findByID(MaDV);
    }
    
    //Tìm kiếm theo keyword
    public List <DichVu> search (String keyword){
        return dvd.search(keyword);
    }
    
    //Thêm dịch vụ
    public boolean insert (DichVu dv)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception ("Bạn không có quyền này !");
        return dvd.insert(dv);
    }
    
    //Sửa thông tin dịch vụ
    public boolean update (DichVu dv) throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception ("Bạn không có quyền này !");
        return dvd.update(dv);
    }
    //Thay đổi trạng thái dịch vụ
    public boolean updateTrangThai (int MaDV,boolean TrangThai)throws Exception{
        if(!Utils.Auth.user.getVaiTro().equalsIgnoreCase("Admin"))
            throw new Exception ("Bạn không có quyền này !");
        
        return dvd.updateTrangThai(MaDV, TrangThai);
    }
    
}
