/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.quanlyxuongsuaxe;

import Model.DichVu;
import java.text.Normalizer;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class CardDichVuController {
    @FXML
    private CheckBox cbLuaChon;
    @FXML
    private ImageView imgDichVu;
    @FXML
    private Label lblTenDichVu;
    @FXML
    private Label lblGiaTien;
    
    
    private DichVu DV;
    
    private String toFileName(String tenDV) {
    return Normalizer.normalize(tenDV, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")   // bỏ dấu
            .replaceAll("\\s+", "")     // bỏ khoảng trắng
            .toLowerCase();             // chuyển thành chữ thường
}
    @FXML
    public void setData(DichVu dv){
        this.DV =dv;
        lblTenDichVu.setText(dv.getTenDV());
        lblGiaTien.setText(dv.getTienCong().toString());
        String Tendv ="/AnhDichVu/" + toFileName(dv.getTenDV().trim()) + ".png";
        try{
            Image image =  new Image(getClass().getResourceAsStream(Tendv));
            imgDichVu.setImage(image);
        }catch(Exception e){
            Image image = new Image(getClass().getResourceAsStream("/AnhDichVu/default.png"));
            imgDichVu.setImage(image);
        }
    }
    
    public boolean isSelected(){
        return cbLuaChon.isSelected();
    }
    
    public void setSelected(boolean selected){
        cbLuaChon.setSelected(selected);
    }

    public CheckBox getCbLuaChon() {
        return cbLuaChon;
    }

    public DichVu getDichVu(){
        return DV;
    }
    
    
}
