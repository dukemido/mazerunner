/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.objects.armor;

/**
 *
 * @author Muhammad
 */
public abstract class Armor implements IArmor {

    int defense;
    String imgPath, name;

    public Armor(String name, int defense, String imgPath) {
        this.defense = defense;
        this.imgPath = imgPath;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public String getImgPath() {
        return imgPath;
    }

}
