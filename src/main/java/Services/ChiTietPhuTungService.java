/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import DAO.ChiTietPhuTungDAO;
import DAO.PhieuSuaChuaDAO;
import Model.ChiTietPhuTung;
import Model.PhieuSuaChua;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

public class ChiTietPhuTungService {
    
    private ChiTietPhuTungDAO ctptd;
    
    public ChiTietPhuTungService(){
        ctptd = new ChiTietPhuTungDAO();
    }
    
    //Tìm kiếm theo mã phụ tùng
    public ChiTietPhuTung findByID ( int MaPT){
        return ctptd.findByID(MaPT);
    }
    
    //Tìm kiếm theo mã phiếu
    public ChiTietPhuTung findByMaPhieu (int MaPhieu){
        return ctptd.findByMaPhieu(MaPhieu);
    }
    
    //Thêm chi tiết phụ tùng
    public boolean insert (ChiTietPhuTung ctpt,Connection conn)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctpt.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn thành"))
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa !");
        try{
            return ctptd.insert(ctpt, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Sửa chi tiết phụ tùng
    public boolean update (ChiTietPhuTung ctpt,Connection conn)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctpt.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn thành"))
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa !");
        try{
            return ctptd.update(ctpt, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Xóa chi tiết phụ tùng
    public boolean delete (int MaPhieu, int MaPT,Connection conn)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(MaPhieu);
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn thành"))
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa !");
        try{
            return ctptd.delete(MaPhieu, MaPT, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
}
