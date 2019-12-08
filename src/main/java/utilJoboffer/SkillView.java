/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilJoboffer;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author pc
 */
public class SkillView {
    private final SimpleStringProperty skill;
    private CheckBox select;

    public SkillView(String skill) {
        this.skill = new SimpleStringProperty(skill);
        this.select = new CheckBox();
    }

    public String getSkill() {
        return skill.get();
    }

    public void setSkill(String skill) {
        this.skill.set(skill);
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
    
    
}
