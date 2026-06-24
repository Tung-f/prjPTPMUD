package com.mycompany.quanlyxuongsuaxe;

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
import Services.KhachHangService;
import Services.PhieuSuaChuaService;
import Services.XeService;
import Utils.ThreadPool;
import java.net.URL;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

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
        txtTimKiem.textProperty().addListener((obs, oldValue, newValue) ->{
        handleTim(newValue);});
        
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
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    ////////////////////////////////////////Sự kiện nút thêm mới /////////////////////////////////////
    
    @FXML
    private void handleThemMoi() {

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
                    success = phieuSuaChuaService.taoPhieuSuaChua(finalTenKH, finalSdt, finalLoaiXe, finalBienSo, ngayLap, finalTrangThai,finalHangXe);
                } else {
                    // Gọi hàm cập nhật
                    success = phieuSuaChuaService.capNhatPhieuSuaChua(phieudangchon, finalTenKH, finalSdt, finalLoaiXe, finalBienSo, ngayLap, finalTrangThai,finalHangXe);
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
    
    public Image hienThiAnh(String TenXe){
        
        if(TenXe == null||TenXe.trim().isEmpty())
            return new Image(getClass().getResourceAsStream("/AnhXe/default.png"));
        String tenXe = "/AnhXe/" + TenXe.trim() + ".png";
        try{
            return new Image(getClass().getResourceAsStream(tenXe));
        }catch(Exception e){
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
}
