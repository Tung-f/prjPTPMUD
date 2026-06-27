/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlyxuongsuaxe;

/**
 *
 * @author Admin
 */
import Model.NhanVien;
import Services.NhanVienService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;


public class NhanVienController {


    @FXML
    private VBox vboxDanhSachNV;

    @FXML
    private TextField txtMaNV;

    @FXML
    private TextField txtHoTen;

    @FXML
    private TextField txtSDT;

    @FXML
    private TextField txtVaiTro;


    private NhanVienService nhanVienService;


    public NhanVienController(){
        nhanVienService = new NhanVienService();
    }


    @FXML
    public void initialize(){

        loadDanhSachNhanVien();

    }



    // Load danh sách nhân viên bên trái
    private void loadDanhSachNhanVien(){

        vboxDanhSachNV.getChildren().clear();


        List<NhanVien> list = nhanVienService.getAll();


        for(NhanVien nv : list){

            Button btn = new Button(nv.getHoTen());

            btn.setPrefWidth(180);


            btn.setOnAction(e -> {

                hienThiThongTin(nv);

            });


            vboxDanhSachNV.getChildren().add(btn);

        }

    }




    // Hiện thông tin nhân viên
    private void hienThiThongTin(NhanVien nv){

        txtMaNV.setText(
                String.valueOf(nv.getMaNV())
        );


        txtHoTen.setText(
                nv.getHoTen()
        );


        txtSDT.setText(
                nv.getSoDienThoai()
        );


        txtVaiTro.setText(
                nv.getVaiTro()
        );

    }




    // Nút thêm mới
    @FXML
    public void themNhanVien(ActionEvent event){


        txtMaNV.clear();
        txtHoTen.clear();
        txtSDT.clear();
        txtVaiTro.clear();


    }




    // Nút Save
    @FXML
    public void saveNhanVien(ActionEvent event){


        try{


            NhanVien nv = new NhanVien();


            nv.setMaNV(
                    Integer.parseInt(txtMaNV.getText())
            );


            nv.setHoTen(
                    txtHoTen.getText()
            );


            nv.setSoDienThoai(
                    txtSDT.getText()
            );


            nv.setVaiTro(
                    txtVaiTro.getText()
            );


            // phần update cần Connection
            // tạm thời gọi hàm load lại sau khi sửa Service


            loadDanhSachNhanVien();


        }
        catch(Exception e){

            e.printStackTrace();

        }

    }





    // Nút Delete
    @FXML
    public void deleteNhanVien(ActionEvent event){


        try{


            int maNV = Integer.parseInt(
                    txtMaNV.getText()
            );


            // Sau khi thêm delete vào Service thì gọi ở đây


            loadDanhSachNhanVien();


        }
        catch(Exception e){

            e.printStackTrace();

        }


    }

}