/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

/**
 *
 * @author Admin
 */
import DAO.ChiTietDichVuDAO;
import DAO.PhieuSuaChuaDAO;
import Model.ChiTietDichVu;
import Model.PhieuSuaChua;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
public class ChiTietDichVuService {
    
    private ChiTietDichVuDAO ctdvd;
    
    public ChiTietDichVuService(){
        ctdvd = new ChiTietDichVuDAO();
    }
    
    //Tìm kiếm theo mã dịch vụ
    public ChiTietDichVu findByID (int MaDV){
        return ctdvd.findByID(MaDV);
    }
    
    //Tìm kiếm theo mã phiếu
    public List<ChiTietDichVu> findByMaPhieu (int MaPhieu){
        return ctdvd.findByMaPhieu(MaPhieu);
    }
    
    //Thêm chi tiết dịch vụ
    public boolean insert(ChiTietDichVu ctdv,Connection conn) throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctdv.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa !");
        try{
            return ctdvd.insert(ctdv, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Sửa thông tin chi tiết dịch vụ
    public boolean update (ChiTietDichVu ctdv,Connection conn)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctdv.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception("Đơn hàng đã hoàn thành ,  không thể sửa !");
        try{
            return ctdvd.update(ctdv, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Xóa chi tiết dịch vụ
    public boolean delete(int MaPhieu,int MaDV,Connection conn)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(MaPhieu);
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception ("Đơn hàng đã hoàn thành không thể sửa !");
        try{
            return ctdvd.delete(MaPhieu, MaDV, conn);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
