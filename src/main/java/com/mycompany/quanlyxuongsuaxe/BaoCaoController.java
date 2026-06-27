/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quanlyxuongsuaxe;

/**
 *
 * @author Admin
 */
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import Services.PhieuSuaChuaService;
import Services.KhachHangService;
import Model.PhieuSuaChua;

import java.math.BigDecimal;
import java.util.List;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class BaoCaoController {
    @FXML
    private DatePicker dateTuNgay;

    @FXML
    private DatePicker dateDenNgay;


    @FXML
    private LineChart<String, Number> lineChart;


    @FXML
    private BarChart<String, Number> barChart;


    @FXML
    private Label lblTongCongViec;


    @FXML
    private Label lblTongDoanhThu;


    @FXML
    private Label lblSoKhachHang;

    private PhieuSuaChuaService pscService = new PhieuSuaChuaService();
    private KhachHangService khService = new KhachHangService();


    @FXML
    public void initialize(){

        // mặc định chọn ngày hiện tại
        dateTuNgay.setValue(LocalDate.now().minusMonths(1));
        dateDenNgay.setValue(LocalDate.now());
        
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        lineChart.setAnimated(false);

        loadBaoCao();
    }



    @FXML
    private void xemBaoCao(){

        loadBaoCao();

    }



    private void loadBaoCao(){

        List<PhieuSuaChua> list = pscService.getAll();


        // tổng công việc
        int tongCongViec = list.size();


        // tổng doanh thu
        BigDecimal doanhThu = BigDecimal.ZERO;

        for(PhieuSuaChua p : list){

            if(p.getTongTien()!=null){
                doanhThu = doanhThu.add(p.getTongTien());
            }

        }


        // số khách hàng
        int soKhachHang = khService.getAll().size();

        lblTongCongViec.setText(String.valueOf(tongCongViec));

        lblTongDoanhThu.setText(
            doanhThu.toString()+" VNĐ"
        );

        lblSoKhachHang.setText(String.valueOf(soKhachHang));
        
        lineChart.getData().clear();
        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Doanh thu");
        series.getData().add(
             new XYChart.Data<>("Hôm nay", doanhThu.doubleValue())
        );
        
        lineChart.getData().add(series);
        
        barChart.getData().clear();

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();

        barSeries.setName("Thống kê");

        barSeries.getData().add(
                new XYChart.Data<>("Công việc", tongCongViec)
        );

        barSeries.getData().add(
                new XYChart.Data<>("Khách hàng", soKhachHang)
        );

        barChart.getData().add(barSeries);
    }
}
