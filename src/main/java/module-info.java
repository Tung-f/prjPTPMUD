module com.mycompany.quanlyxuongsuaxe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.quanlyxuongsuaxe to javafx.fxml;
    exports com.mycompany.quanlyxuongsuaxe;
}
