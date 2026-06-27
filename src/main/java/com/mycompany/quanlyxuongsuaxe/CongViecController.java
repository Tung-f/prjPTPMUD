package com.mycompany.quanlyxuongsuaxe;

import Model.ChiTietDichVu;
import Model.DichVu;
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
import Services.ChiTietDichVuService;
import Services.DichVuService;
import Services.KhachHangService;
import Services.PhieuSuaChuaService;
import Services.XeService;
import Utils.ThreadPool;
import java.math.BigDecimal;
import java.net.URL;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;

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

    @FXML
    private TextField txtHangXe;
    private final PhieuSuaChuaService phieuSuaChuaService = new PhieuSuaChuaService();
    private PhieuSuaChua phieudangchon = null;

    @FXML
    public void initialize() {
        cbTrangThai.getItems().addAll("Chờ sửa", "Đang sửa", "Đã hoàn thành", "Đã hủy");
        loadDanhSachCongViec();
        txtXe.textProperty().addListener((obs, oldValue, newValue) -> {
            imgXe.setImage(hienThiAnh(newValue));
        });
        txtTimKiem.textProperty().addListener((obs, oldValue, newValue) -> {
            handleTim(newValue);
        });

        colTenDV.setCellValueFactory(new PropertyValueFactory<>("TenDV"));
        colTienCong.setCellValueFactory(new PropertyValueFactory<>("TienCong"));

        tbDichVu.setItems(dsDichVu);
        setupColThaoTac();
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

    @FXML
    private void openBaoCao() throws IOException {
        loadPage("baocao.fxml");
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

                XeService xs = new XeService();
                Xe xe = xs.findByID(p.getMaXe());

                KhachHangService khs = new KhachHangService();
                KhachHang kh = (xe != null) ? khs.findByID(xe.getMaKH()) : null;

                // Lấy chi tiết dịch vụ
                ChiTietDichVuService ctdvService = new ChiTietDichVuService();
                List<ChiTietDichVu> dsCTDV = ctdvService.findByMaPhieu(p.getMaPhieu());

                // Lấy thông tin dịch vụ
                DichVuService dvService = new DichVuService();

                ObservableList<DichVu> ds = FXCollections.observableArrayList();

                for (ChiTietDichVu ct : dsCTDV) {

                    DichVu dv = dvService.findByID(ct.getMaDV());

                    if (dv != null) {
                        ds.add(dv);
                    }

                }

                Platform.runLater(() -> {

                    // Thông tin phiếu
                    if (p.getNgayLap() != null) {

                        txtNgayTao.setValue(
                                new java.sql.Timestamp(p.getNgayLap().getTime())
                                        .toLocalDateTime()
                                        .toLocalDate()
                        );

                    } else {

                        txtNgayTao.setValue(null);

                    }

                    cbTrangThai.setValue(p.getTrangThai());

                    // Khách hàng
                    if (kh != null) {

                        txtKhachHang.setText(kh.getTenKH());
                        txtSoDienThoai.setText(kh.getSoDienThoai());

                    } else {

                        txtKhachHang.clear();
                        txtSoDienThoai.clear();

                    }

                    // Xe
                    if (xe != null) {

                        txtXe.setText(xe.getLoaiXe());
                        txtBienSo.setText(xe.getBienSo());
                        txtHangXe.setText(xe.getHangXe());

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

                    // Đổ dịch vụ lên TableView
                    dsDichVu.setAll(ds);

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    ////////////////////////////////////////Sự kiện nút thêm mới /////////////////////////////////////
    
    @FXML
    private void handleThemMoi() {
        dsDichVu.clear();
        this.phieudangchon = null;

        txtNgayTao.setValue(null);
        cbTrangThai.setValue(null);
        txtKhachHang.clear();
        txtSoDienThoai.clear();
        txtXe.clear();
        txtBienSo.clear();

        lblLoaiXe.setText("");
        lblHangXe.setText("");
        lblBienSo.setText("");

        if (imgXe != null) {
            imgXe.setImage(null);
        }
    }
    ///////////////////////////////log out/////////////////////////////////////////////
    @FXML
    private Button btnDangXuat;

    @FXML
    public void handleLogOut() {
        Utils.LogOut.logout(btnDangXuat);
    }

    ////////////////////////////////////Sự kiện nút lưu/////////////////////////////
    @FXML
    public void handleSaveAs() {
        LocalDate ngayTao = txtNgayTao.getValue();
        String trangThai = cbTrangThai.getValue();

        String tenKH = txtKhachHang.getText().trim();
        String sdt = txtSoDienThoai.getText().trim();
        String loaiXe = txtXe.getText().trim();
        String bienSo = txtBienSo.getText().trim();
        String hangXe = txtHangXe.getText().trim();

        if (tenKH == null || tenKH.isEmpty()
                || sdt == null || sdt.isEmpty()
                || loaiXe == null || loaiXe.isEmpty()
                || bienSo == null || bienSo.isEmpty()) {

            System.out.println("Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        java.sql.Date ngayLap = java.sql.Date.valueOf(ngayTao);

        final String finalTenKH = tenKH;
        final String finalSdt = sdt;
        final String finalLoaiXe = loaiXe;
        final String finalBienSo = bienSo;
        final String finalTrangThai = trangThai;
        final String finalHangXe = hangXe;
        ThreadPool.submit(() -> {
            try {
                boolean success;

                if (phieudangchon == null) {
                    // Gọi hàm tạo mới
                    success = phieuSuaChuaService.taoPhieuSuaChua(finalTenKH, finalSdt, finalLoaiXe, finalBienSo, ngayLap, finalTrangThai, finalHangXe, dsDichVu);
                } else {
                    // Gọi hàm cập nhật
                    success = phieuSuaChuaService.capNhatPhieuSuaChua(phieudangchon, finalTenKH, finalSdt, finalLoaiXe, finalBienSo, ngayLap, finalTrangThai, finalHangXe, dsDichVu);
                    phieuSuaChuaService.updateTongTien(phieudangchon.getMaPhieu(), dsDichVu);
                }

                Platform.runLater(() -> {
                    if (success) {
                        //   hiện Alert
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                        alert.setTitle("Thông báo");
                        alert.setHeaderText(null);
                        alert.setContentText("Lưu dữ liệu thành công!");
                        alert.showAndWait();

                        loadDanhSachCongViec();

                        if (phieudangchon == null) {
                            handleThemMoi();
                        }
                    } else {
                        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                        alert.setTitle("Lỗi");
                        alert.setHeaderText(null);
                        alert.setContentText("Lưu dữ liệu thất bại!");
                        alert.showAndWait();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> System.out.println("Có lỗi khi lưu dữ liệu!"));
            }
        });
    }

    /////////////////////////////////Hiện ảnh xe////////////////////////////
    
    public Image hienThiAnh(String TenXe) {

        if (TenXe == null || TenXe.trim().isEmpty()) {
            return new Image(getClass().getResourceAsStream("/AnhXe/default.png"));
        }
        String tenXe = "/AnhXe/" + TenXe.trim() + ".png";
        try {
            return new Image(getClass().getResourceAsStream(tenXe));
        } catch (Exception e) {
            return new Image(getClass().getResourceAsStream("/AnhXe/default.png"));
        }
    }

    //////////////////////////Ô tìm kiếm////////////////////////////////////
    
    @FXML
    private void handleTim(String keyword) {

        ThreadPool.submit(() -> {

            try {

                List<PhieuSuaChua> danhSach
                        = phieuSuaChuaService.timKiem(keyword);

                Platform.runLater(() -> {

                    try {

                        vboxDanhSach.getChildren().clear();

                        for (PhieuSuaChua p : danhSach) {

                            FXMLLoader loader
                                    = new FXMLLoader(getClass().getResource("dscongviec.fxml"));

                            Parent card = loader.load();

                            DsCongViecController controller
                                    = loader.getController();

                            controller.setData(p);

                            controller.setOnCardSelected(phieuDuocChon -> {
                                hienThiThongTinChiTiet(phieuDuocChon);
                            });

                            vboxDanhSach.getChildren().add(card);
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
    ///////////////////////////////////Sự kiện nút thêm dịch vụ//////////////////
    
    @FXML
    private Button btnThemDichVu;

    @FXML
    private TableView tbDichVu;
    @FXML
    private TableColumn<DichVu, Integer> colTenDV;
    @FXML
    private TableColumn<DichVu, BigDecimal> colTienCong;
    @FXML
    private TableColumn colThaoTac;
    private ObservableList<DichVu> dsDichVu = FXCollections.observableArrayList();

    private void handleThemDichVu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chondichvu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ChonDichVuController controller
                    = loader.getController();
            //chặn thao tác với cửa số chính
            stage.initModality(Modality.APPLICATION_MODAL);
            // Không cho resize (tùy chọn)
            stage.setResizable(false);
            controller.setSelectedDichVu(new ArrayList<>(dsDichVu));

            controller.loadDichVu();
            stage.setResizable(false);

            // Tính toán vị trí
            stage.setOnShown(e -> {
                Bounds bounds = btnThemDichVu.localToScreen(btnThemDichVu.getBoundsInLocal());

                // Căn popup ngay phía trên nút
                stage.setX(bounds.getMinX()+100);

                stage.setY(bounds.getMinY() - stage.getHeight() +30);
            });
            // Hiện popup và đợi người dùng đóng
            stage.showAndWait();
            List<DichVu> ds = controller.getSelectedDichVu();
            dsDichVu.clear();
            dsDichVu.addAll(ds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupColThaoTac() {

        colThaoTac.setCellFactory(param -> new TableCell<DichVu, Void>() {

            private final Button btnXoa = new Button("Xóa");

            {

                btnXoa.setOnAction(event -> {

                    DichVu dv = getTableView().getItems().get(getIndex());

                    dsDichVu.remove(dv);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnXoa);
                }
            }

        });

    }

    @FXML
    private void themDichVu() {
        if (phieudangchon != null) {
            handleThemDichVu();
        }

    }
}
