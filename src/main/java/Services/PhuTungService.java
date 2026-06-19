/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import DAO.PhuTungDAO;
import Model.PhuTung;

import java.util.ArrayList;
import java.util.List;

public class PhuTungService {
    
    private PhuTungDAO ptd;
    
    public PhuTungService(){
        ptd = new PhuTungDAO();
    }
    
    //Lấy tất cả
    public List<PhuTung> getAll(){
        return ptd.getAll();
    }
    
    //Tìm kiếm theo mã phụ tùng
    public PhuTung findByID (int MaPT){
        return ptd.findByID(MaPT);
    }
    
    //Tìm kiếm theo keyword
    public List<PhuTung> search (String keyword){
        return ptd.search(keyword);
    }
    
    //Thêm phụ tùng
    public boolean insert (PhuTung pt){
        return ptd.insert(pt);
    }
    
    //Sửa thông tin phụ tùng
    public boolean update (PhuTung pt)throws Exception{
        if(!Utils.Auth.user.getVaiTro().trim().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        return ptd.update(pt);
    }
    
    //Báo hàng sắp hết
    public List<PhuTung> getSapHetHang(){
        return ptd.getSapHetHang();
    }
    
    //Nhập hàng
    public boolean nhapHang (int MaPT, int SoLuong) throws Exception{
        if(SoLuong <=0)
            throw new Exception("Số lượng phải lớn hơn 0 ");
        if(!Utils.Auth.user.getVaiTro().trim().equalsIgnoreCase("Admin"))
            throw new Exception("Bạn không có quyền này!");
        return ptd.tangSoLuong(MaPT, SoLuong);
    }
    //Giảm số lượng
    public boolean giamSoLuong (int MaPT , int SoLuong)throws Exception{
        PhuTung pt = ptd.findByID(MaPT);
        if(SoLuong <=0)
            throw new Exception("Số lượng phải lớn hơn 0 ");
        if(SoLuong > pt.getSoLuongTon())
            throw new Exception("Trong kho không còn đủ phụ tùng này !");
        return ptd.giamSoLuong(MaPT, SoLuong);
    }
}
