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

    private PhieuSuaChua phieuSuaChua; 
    private Consumer<PhieuSuaChua> onCardSelectedListener;

    public void setOnCardSelected(Consumer<PhieuSuaChua> listener) {
        this.onCardSelectedListener = listener;
    }

    @FXML
    public void setData(PhieuSuaChua p) {
        this.phieuSuaChua = p; 
        
        lblNgayLap.setText(p.getNgayLap().toString());
        lblTrangThai.setText(p.getTrangThai());
        lblTenXe.setText(p.getLoaiXe().toUpperCase());
        lblKhachHang.setText(p.getTenKH());
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