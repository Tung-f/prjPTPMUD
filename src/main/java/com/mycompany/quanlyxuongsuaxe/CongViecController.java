package com.mycompany.quanlyxuongsuaxe;

import DAO.KhachHangDAO;
import DAO.XeDAO;
import Model.KhachHang;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import Model.PhieuSuaChua;
import Model.Xe;
import Services.PhieuSuaChuaService;
import Utils.ThreadPool;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;

public class CongViecController {

    @FXML
    private DatePicker txtNgayTao;

    @FXML
    private TextField txtKhachHang;

    @FXML
    private TextField txtXe;

    @FXML
    private TextField txtSoDienThoai;

    @FXML
    private TextField txtBienSo;

    @FXML
    private ComboBox<String> cbTrangThai;

    @FXML
    private Label lblLoaiXe;

    @FXML
    private Label lblHangXe;

    @FXML
    private Label lblBienSo;

    @FXML
    private ImageView imgXe;

    @FXML
    private Button btnCongViec;

    @FXML
    private VBox vboxDanhSach;

    @FXML
    private Button btnThemMoi;

    @FXML
    private TextField txtTimKiem;

    private final PhieuSuaChuaService phieuSuaChuaService = new PhieuSuaChuaService();
    private PhieuSuaChua phieudangchon = null;

    @FXML
    public void initialize() {
        cbTrangThai.getItems().addAll("Chờ sửa", "Đang sửa", "Đã hoàn thành", "Đã hủy");
        loadDanhSachCongViec();
    }

    ////////////////////////////////// Chuyển tab ///////////////////////////
    private void loadPage(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        Stage stage = (Stage) btnCongViec.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void openDichVu() throws IOException {
        loadPage("dichvu.fxml");
    }

    @FXML
    private void openKhachHang() throws IOException {
        loadPage("khachhang.fxml");
    }

    @FXML
    private void openNhanVien() throws IOException {
        loadPage("nhanvien.fxml");
    }

    /////////////////////////////////// List ////////////////////////////////
    public void loadDanhSachCongViec() {
        ThreadPool.submit(() -> {
            try {
                List<PhieuSuaChua> danhSach = phieuSuaChuaService.getAll();

                Platform.runLater(() -> {
                    try {
                        vboxDanhSach.getChildren().clear();

                        for (PhieuSuaChua p : danhSach) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("dscongviec.fxml"));
                            Parent card = loader.load();

                            DsCongViecController controller = loader.getController();
                            controller.setData(p);

                            controller.setOnCardSelected(phieuDuocChon -> {
                                hienThiThongTinChiTiet(phieuDuocChon);
                            });

                            vboxDanhSach.getChildren().add(card);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    ////////////////////////// Sự kiện hiển thị chi tiết ////////////////////////
    public void hienThiThongTinChiTiet(PhieuSuaChua p) {
        if (p == null) {
            return;
        }

        this.phieudangchon = p;

        ThreadPool.submit(() -> {
            try {
                XeDAO xd = new XeDAO();
                Xe xe = xd.findByID(p.getMaXe());

                KhachHangDAO khd = new KhachHangDAO();
                KhachHang kh = (xe != null) ? khd.findByID(xe.getMaKH()) : null;

                Platform.runLater(() -> {
                   
                    if (p.getNgayLap() != null) {
                        
                        java.sql.Timestamp timestamp = new java.sql.Timestamp(p.getNgayLap().getTime());
                        txtNgayTao.setValue(timestamp.toLocalDateTime().toLocalDate());
                    } else {
                        txtNgayTao.setValue(null);
                    }
                    cbTrangThai.setValue(p.getTrangThai());

                    if (kh != null) {
                        txtKhachHang.setText(kh.getTenKH());
                        txtSoDienThoai.setText(kh.getSoDienThoai());
                    } else {
                        txtKhachHang.clear();
                        txtSoDienThoai.clear();
                    }

                    if (xe != null) {
                        txtXe.setText(xe.getLoaiXe());
                        txtBienSo.setText(xe.getBienSo());

                        lblLoaiXe.setText(xe.getLoaiXe());
                        lblHangXe.setText(xe.getHangXe());
                        lblBienSo.setText(xe.getBienSo());
                    } else {
                        txtXe.clear();
                        txtBienSo.clear();
                        lblLoaiXe.setText("");
                        lblHangXe.setText("");
                        lblBienSo.setText("");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
