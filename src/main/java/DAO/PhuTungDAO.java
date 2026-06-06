/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Admin
 */
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;
import Model.PhuTung;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class PhuTungDAO {
    //Tao doi tuong phu tung

    private PhuTung mapResultSetToPhuTung(ResultSet rs) throws Exception {
        PhuTung pt = new PhuTung();
        pt.setMaPT(rs.getInt("MaPT"));
        pt.setTenPT(rs.getString("TenPT"));
        pt.setSoLuongTon(rs.getInt("SoLuongTon"));
        pt.setDonGiaNhap(rs.getBigDecimal("DonGiaNhap"));
        pt.setDonGiaBan(rs.getBigDecimal("DonGiaBan"));
        pt.setMucCanhBao(rs.getInt("MucCanhBao"));
        
        return pt; 
    }
    
    //Lay tat ca phu tung
    public List<PhuTung> getAll(){
        
        List<PhuTung> list = new ArrayList<>();
        String sql = "SELECT * FROM PhuTung";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ){
            while(rs.next())
                list.add(mapResultSetToPhuTung(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Tim kiem theo ma
    public PhuTung findByID(int MaPT){
        String sql = "SELECT * FROM PhuTung WHERE MaPT = ?";
        
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1,MaPT);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToPhuTung(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    //Tim kiem theo ten
    public List<PhuTung> search(String keyword){
        List<PhuTung> list = new ArrayList<>();
        
        String sql = "SELECT * FROM PhuTung WHERE TenPT LIKE ?";
        
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
            ){
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(mapResultSetToPhuTung(rs));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Them phu tung
    public boolean insert(PhuTung pt){
        
        String sql =    "INSERT INTO PhuTung " +
                        "(TenPT, SoLuongTon, " +
                        "DonGiaNhap, DonGiaBan) " +
                        "VALUES (?, ?, ?, ?)";
        try(
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
                ){
                ps.setString(1, pt.getTenPT());
                ps.setInt(2, pt.getSoLuongTon());
                ps.setBigDecimal(3, pt.getDonGiaNhap());
                ps.setBigDecimal(4, pt.getDonGiaBan());
                
                return ps.executeUpdate()>0;
            }catch(SQLException e){
               e.printStackTrace();
            }
        return false;
    }
    
    //Cap nhat phu tung
    public boolean update(PhuTung pt){
        
        String sql =    "UPDATE PhuTung SET "
                        +"TenPT=?, SoLuongTon=?, DonGiaNhap=?, DonGiaBan=?, MucCanhBao=? "
                        +"WHERE MaPT=?";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setString(1, pt.getTenPT());
            ps.setInt(2, pt.getSoLuongTon());
            ps.setBigDecimal(3, pt.getDonGiaNhap());
            ps.setBigDecimal(4, pt.getDonGiaBan());
            ps.setInt(5, pt.getMucCanhBao());
            ps.setInt(6, pt.getMaPT());
            
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Xoa phu tung
    public boolean delete(int MaPT){
        String sql = "DELETE FROM PhuTung WHERE MaPT = ?";
        
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
                ){
            ps.setInt(1, MaPT);
            return ps.executeUpdate()>0;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //=====================================================================================//
    
    //Bao sap het hang
    public List<PhuTung> getSapHetHang(){
        
        List<PhuTung> list = new ArrayList<>();
        String sql = "SELECT * FROM PhuTung WHERE SoLuongTon <= MucCanhBao";
        
        try(
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
                ){
            while(rs.next()){
                list.add(mapResultSetToPhuTung(rs));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
    //Giam so luong
    public boolean giamSoLuong(int MaPT, int SoLuong){
        String sql = "UPDATE PhuTung SET SoLuongTon = SoLuongTon - ? WHERE MaPT=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SoLuong);
            ps.setInt(2, MaPT);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    //Tang so luong
    public boolean tangSoLuong(int MaPT, int SoLuong){
        String sql = "UPDATE PhuTung SET SoLuongTon = SoLuongTon + ? WHERE MaPT=?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SoLuong);
            ps.setInt(2, MaPT);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //=====================================================================================//
}
