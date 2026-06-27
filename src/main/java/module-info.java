module com.mycompany.quanlyxuongsuaxe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens com.mycompany.quanlyxuongsuaxe to javafx.fxml;
    opens Model to javafx.base;
    exports Model;
    exports com.mycompany.quanlyxuongsuaxe;
    
}
