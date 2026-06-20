package com.mycompany.quanlyxuongsuaxe;

import DAO.KhachHangDAO;
import DAO.XeDAO;
import Model.KhachHang;
import Model.PhieuSuaChua;
import Model.Xe;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DsCongViecController {

    @FXML
    private Label lblKhachHang;

    @FXML
    private Label lblNgayLap;

    @FXML
    private Label lblTenXe;
    
    @FXML
    private Label lblTrangThai;

    @FXML
    private HBox rootCard;

    private PhieuSuaChua phieuSuaChua; // Biến viết thường chữ p đầu
    private Consumer<PhieuSuaChua> onCardSelectedListener;

    public void setOnCardSelected(Consumer<PhieuSuaChua> listener) {
        this.onCardSelectedListener = listener;
    }

    @FXML
    public void setData(PhieuSuaChua p) {
        // ĐÃ SỬA: Sửa chữ P hoa thành p thường để ăn vào biến toàn cục
        this.phieuSuaChua = p; 
        
        XeDAO xd = new XeDAO();
        Xe xe = xd.findByID(p.getMaXe());

        KhachHangDAO khd = new KhachHangDAO();
        KhachHang kh = khd.findByID(xe.getMaKH());

        lblNgayLap.setText(p.getNgayLap().toString());
        lblTrangThai.setText(p.getTrangThai());
        lblTenXe.setText(xe.getLoaiXe());
        lblKhachHang.setText(kh.getTenKH());
    }

    @FXML
    public void initialize() {
        rootCard.setOnMouseClicked(event -> {
            if (onCardSelectedListener != null && phieuSuaChua != null) {
                onCardSelectedListener.accept(phieuSuaChua);
            }
        });
    }
}