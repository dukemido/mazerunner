/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.decorator;

import models.objects.armor.Armor;

/**
 *
 * @author Muhammad
 */
public class ArmorDecorator extends Armor {

    protected Armor decoratedArmor;

    public ArmorDecorator(Armor armor) {
        super(armor.getImgPath(), armor.getDefense(), armor.getImgPath());
    }
    
}
