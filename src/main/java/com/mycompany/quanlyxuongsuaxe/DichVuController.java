/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlyxuongsuaxe;

/**
 *
 * @author Admin
 */
import Model.DichVu;
import Services.DichVuService;
import com.mycompany.quanlyxuongsuaxe.dao.DatabaseConnection;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class DichVuController {

    @FXML
    private TextField txtMaDV;

    @FXML
    private TextField txtTenDV;

    @FXML
    private TextField txtTienCong;

    @FXML
    private TextField txtTimKiem;


    @FXML
    private RadioButton rdDangSuDung;

    @FXML
    private RadioButton rdNgung;


    @FXML
    private VBox vboxDanhSach;

    @FXML
    private void moCongViec(ActionEvent e) {
        chuyenTrang(e, "/congviec.fxml");
    }

    @FXML
    private void moDichVu(ActionEvent e) {
        chuyenTrang(e, "/dichvu.fxml");
    }

    @FXML
    private void moKhachHang(ActionEvent e) {
        chuyenTrang(e, "/khachhang.fxml");
    }

    @FXML
    private void moNhanVien(ActionEvent e) {
        chuyenTrang(e, "/nhanvien.fxml");
    }

    @FXML
    private void moBaoCao(ActionEvent e) {
        chuyenTrang(e, "/baocao.fxml");
    }
    private DichVuService dvService = new DichVuService();


    private DichVu dichVuDangChon = null;


    @FXML
    public void initialize(){

        rdDangSuDung.setSelected(true);

        loadDanhSach();

    }



    // nút thêm mới

    @FXML
    private void themDichVu(){

        dichVuDangChon = null;

        txtMaDV.clear();
        txtTenDV.clear();
        txtTienCong.clear();

        rdDangSuDung.setSelected(true);

    }



    // nút save

    @FXML
    private void luuDichVu(){

        try{

            Connection conn = DatabaseConnection.getConnection();


            DichVu dv = new DichVu();


            if(dichVuDangChon != null){

                dv.setMaDV(
                    dichVuDangChon.getMaDV()
                );

            }


            dv.setTenDV(
                txtTenDV.getText()
            );


            dv.setTienCong(
                new BigDecimal(txtTienCong.getText())
            );


            dv.setTrangThai(
                rdDangSuDung.isSelected()
            );



            boolean result;


            if(dichVuDangChon == null){

                result = dvService.insert(dv, conn);

            }
            else{

                result = dvService.update(dv, conn);

            }



            conn.close();



            if(result){

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Lưu dịch vụ thành công");
                alert.show();

                loadDanhSach();

            }



        }catch(Exception e){

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Lỗi: " + e.getMessage());
            alert.show();

        }

    }




    // load danh sách bên trái

    private void loadDanhSach(){

        vboxDanhSach.getChildren().clear();


        List<DichVu> list = dvService.getAll();



        for(DichVu dv : list){


            Button btn = new Button(
                dv.getMaDV() + " - " + dv.getTenDV()
            );


            btn.setMaxWidth(
                Double.MAX_VALUE
            );


            btn.setOnAction(e -> showDetail(dv));


            vboxDanhSach.getChildren().add(btn);

        }

    }





    // click dịch vụ bên trái

    private void showDetail(DichVu dv){


        dichVuDangChon = dv;



        txtMaDV.setText(
            String.valueOf(dv.getMaDV())
        );


        txtTenDV.setText(
            dv.getTenDV()
        );


        txtTienCong.setText(
            dv.getTienCong().toString()
        );



        if(dv.isTrangThai()){

            rdDangSuDung.setSelected(true);

        }
        else{

            rdNgung.setSelected(true);

        }

    }





    // tìm kiếm

    @FXML
    private void timKiem(){


        String keyword = txtTimKiem.getText();



        vboxDanhSach.getChildren().clear();



        List<DichVu> list =
                dvService.search(keyword);



        for(DichVu dv : list){


            Button btn = new Button(
                    dv.getTenDV()
            );


            btn.setMaxWidth(
                    Double.MAX_VALUE
            );


            btn.setOnAction(e -> showDetail(dv));


            vboxDanhSach.getChildren().add(btn);

        }

    }

    // xóa
       
    @FXML
    private void xoaDichVu(){
        
        if(dichVuDangChon == null){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Chưa chọn dịch vụ");
            alert.show();

            return;

        }
        
        try{

            Connection conn =
                    DatabaseConnection.getConnection();



            boolean result = dvService.delete(dichVuDangChon.getMaDV(),conn);



            conn.close();



            if(result){

                Alert alert =
                        new Alert(Alert.AlertType.INFORMATION);

                alert.setContentText("Xóa thành công");

                alert.show();


                loadDanhSach();

                themDichVu();

            }


        }catch(Exception e){

            e.printStackTrace();

        }


    }
    
    private void chuyenTrang(ActionEvent event, String fxml) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource(fxml));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}