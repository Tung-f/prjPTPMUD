package com.mycompany.quanlyxuongsuaxe.dao; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Cấu hình thông tin máy chủ
    private static final String SERVER = "localhost";
    private static final String PORT = "1433"; 
    private static final String DATABASE = "QLCHSCXM"; 
    
    // 2. TÀI KHOẢN & MẬT KHẨU SQL SERVER
    private static final String USER = "sa"; 
    private static final String PASSWORD = "123"; 

    // 3. Hàm kết nối chính
    public static Connection getConnection() {
        // trustServerCertificate=true để vượt qua tường lửa và chứng chỉ SSL
        String url = "jdbc:sqlserver://" + SERVER + ":" + PORT + ";databaseName=" + DATABASE + ";encrypt=true;trustServerCertificate=true;";
        try {
            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (SQLException ex) {
            System.out.println("LOI KET NOI  " + ex.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("KET NOI THANH CONG");
            System.out.println("SAN SANG KEO DU LIEU LEN JAVAFX");
        } else {
            System.out.println("KET NOI THAT BAI");
        }
    }
}