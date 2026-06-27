/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlyxuongsuaxe;

import Model.DichVu;
import Services.DichVuService;
import Utils.ThreadPool;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ChonDichVuController {

    @FXML
    private VBox vboxDichVu;

    private DichVuService dvs = new DichVuService();

    private List<CardDichVuController> dsCard = new ArrayList<>();

    /////////////danh sách dịch vụ đã chọn///////
    private List<DichVu> dsDaChon;

    public void setSelectedDichVu(List<DichVu> dsDaChon) {
        this.dsDaChon = dsDaChon;
    }

    /////////Load list////////
    public void loadDichVu() {

        List<DichVu> ds = dvs.getAll();

        for (DichVu dv : ds) {

            try {

                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource("CardDichVu.fxml"));

                Parent card = loader.load();

                CardDichVuController controller = loader.getController();

                controller.setData(dv);

                // Nếu dịch vụ này đã thuộc phiếu thì tick
                if (dsDaChon != null) {

                    for (DichVu d : dsDaChon) {

                        if (d.getMaDV() == dv.getMaDV()) {

                            controller.setSelected(true);
                            break;
                        }

                    }

                }

                dsCard.add(controller);

                vboxDichVu.getChildren().add(card);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //////Lấy card được chọn//////////
    public List<DichVu> getSelectedDichVu() {

        List<DichVu> result = new ArrayList<>();

        for (CardDichVuController card : dsCard) {

            if (card.isSelected()) {
                result.add(card.getDichVu());
            }

        }

        return result;
    }

    @FXML
    public void initialize() {
        
        txtSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            timkiem(newValue);
        });

    }

    //Sự kiện nút hủy bỏ ///
    @FXML
    private void handleHuyBo() {
        for (CardDichVuController card : dsCard) {
            card.setSelected(false);
        }
    }
    //Tìm kiếm//
    @FXML
    private TextField txtSearch;

    public void timkiem(String keyword) {
        ThreadPool.submit(() -> {
            try {
                List<DichVu> list = dvs.search(keyword);
                Platform.runLater(() -> {

                    try {

                        vboxDichVu.getChildren().clear();

                        for (DichVu dv : list) {

                            FXMLLoader loader
                                    = new FXMLLoader(getClass().getResource("CardDichVu.fxml"));

                            Parent card = loader.load();

                            CardDichVuController controller
                                    = loader.getController();

                            controller.setData(dv);

                            vboxDichVu.getChildren().add(card);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //list các card đã được chọn///
    @FXML
    private Button btnXacNhan;

    @FXML
    private void handleXacNhan() {

        List<DichVu> ds = getSelectedDichVu();

        for (DichVu dv : ds) {
            System.out.println(dv.getTenDV());
        }
        Stage stage = (Stage) vboxDichVu.getScene().getWindow();
        stage.close();
    }
}
