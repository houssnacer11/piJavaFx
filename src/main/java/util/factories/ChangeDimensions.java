/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.factories;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;

/**
 *
 * @author aminos
 */

public interface ChangeDimensions extends ChangeListener<Number> {
    public static final int HEIGHT = 1;
    public static final int WIDTH = 2;
    public static final int HEIGHT_WIDTH = 3;
    public static final int CHANGE_TO_VBOX = 4;
    public static final int CHANGE_TO_HBOX = 5;
}
