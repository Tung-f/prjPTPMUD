package com.mycompany.quanlyxuongsuaxe;

import java.io.IOException;
import javafx.fxml.FXML;

public class CongViecController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}