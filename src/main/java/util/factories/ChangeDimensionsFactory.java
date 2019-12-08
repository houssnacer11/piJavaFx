/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.factories;

import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import util.exceptions.InvalidArgumentException;
import util.exceptions.WidgetNotFoundException;

/**
 *
 * @author aminos
 */
public class ChangeDimensionsFactory {

    public ChangeListener<Number> createListener(Parent rootNode, String cssId, double factor, int prop) throws WidgetNotFoundException, InvalidArgumentException {

        if (factor < 0)
            throw new InvalidArgumentException("the resizing factor should be > 0.");
        switch (prop) {
            case ChangeDimensions.HEIGHT:
                return new ChangeHeight(rootNode, cssId, factor, prop);
            case ChangeDimensions.WIDTH:
                return new ChangeWidth(rootNode, cssId, factor, prop);
            case ChangeDimensions.HEIGHT_WIDTH:
                return new ChangeHeightWidth(rootNode, cssId, factor, prop);
            default:
                throw new InvalidArgumentException("ChangeDimensions.{HEIGHT or WIDTH or HEIGHT_WIDTH");
                
        }
    }

}
