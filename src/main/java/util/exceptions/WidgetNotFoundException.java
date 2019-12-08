/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exceptions;

/**
 *
 * @author aminos
 */
public class WidgetNotFoundException extends Exception {
    public WidgetNotFoundException() {
        
    }
    public WidgetNotFoundException(String err) {
        super(err);
    }
}
