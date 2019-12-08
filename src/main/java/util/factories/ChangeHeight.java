/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.factories;

import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import util.exceptions.WidgetNotFoundException;

/**
 *
 * @author aminos
 */
public class ChangeHeight implements ChangeDimensions {

    private Parent rootNode;
    private Region child;
    private double factor;
    private int prop;
    
    private ChangeHeight() {}
    
    public ChangeHeight(Parent root, String cssId, double factor, int prop) throws WidgetNotFoundException {
        this.rootNode = root;
        
        this.child = (Region) rootNode.lookup(cssId);
        if (child == null) {
            throw new WidgetNotFoundException("No widget with id " + cssId + " is found.");
        }
        
        this.prop = prop;
        this.factor = factor;
    }

  

    @Override
    public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
                            this.child.setPrefHeight(newVal.doubleValue() * this.factor);

    }

    
}
