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
    public boolean insert(ChiTietDichVu ctdv) throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctdv.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception("Đơn hàng đã hoàn thành , không thể sửa !");
        return ctdvd.insert(ctdv);
    }
    
    //Sửa thông tin chi tiết dịch vụ
    public boolean update (ChiTietDichVu ctdv)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(ctdv.getMaPhieu());
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception("Đơn hàng đã hoàn thành ,  không thể sửa !");
        return ctdvd.update(ctdv);
    }
    
    //Xóa chi tiết dịch vụ
    public boolean delete(int MaPhieu,int MaDV)throws Exception{
        PhieuSuaChuaDAO pscd = new PhieuSuaChuaDAO();
        PhieuSuaChua psc = pscd.findByID(MaPhieu);
        if(psc.getTrangThai().trim().equalsIgnoreCase("Hoàn Thành"))
            throw new Exception ("Đơn hàng đã hoàn thành không thể sửa !");
        return ctdvd.delete(MaPhieu, MaDV);
    }
}
