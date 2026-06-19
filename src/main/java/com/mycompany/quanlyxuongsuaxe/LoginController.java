package com.mycompany.quanlyxuongsuaxe;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Model.NhanVien;
import Services.NhanVienService;
import javafx.scene.control.Label;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private PasswordField txtMatKhau;

    @FXML
    private Label lblTB;

    private NhanVienService nvs = new NhanVienService();

    @FXML
    private void Login() throws Exception {

        try {
                String TenDangNhap = txtTenDangNhap.getText();
        
                String MatKhau = txtMatKhau.getText();
        
                NhanVien nv = nvs.LoginService(TenDangNhap, MatKhau);
        
                lblTB.setText("Đăng nhập thành công ");
                
                Utils.Auth.user = nv;
            //Chuyen sang trang sau
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CongViec.fxml"));
            Parent root = loader.load();

            Stage stage= (Stage) txtTenDangNhap.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblTB.setText(e.getMessage());
        }
    }
}
