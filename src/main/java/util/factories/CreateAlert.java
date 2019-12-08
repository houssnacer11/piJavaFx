/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.factories;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Yassine
 */
public class CreateAlert {

    public static Optional<ButtonType> CreateAlert(AlertType alertTypeValue, String title, String headerText, String contentText) {

        Alert alert = new Alert(alertTypeValue);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();

    }

}
