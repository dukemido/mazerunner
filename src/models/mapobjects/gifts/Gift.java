/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.gifts;

import controllers.CustomEngine;
import java.awt.Graphics;
import models.entities.player.Player;
import models.mapobjects.MapObject;
import models.objects.armor.IArmor;
import models.objects.weapons.IWeapon;

/**
 *
 * @author Muhammad
 */
public abstract class Gift extends MapObject implements IGift {

    int extraHP;
    IWeapon weapon;
    IArmor armor;
    String imgPath;

    public Gift(String imgPath, int extraHP, IWeapon weapon, IArmor armor, int x, int y) {
        super(x, y);
        this.imgPath = imgPath;
        this.extraHP = extraHP;
        this.weapon = weapon;
        this.armor = armor;

    }

    @Override
    public String getImgPath() {
        return imgPath;
    }

    @Override
    public int getGiftHP() {
        return extraHP;
    }

    @Override
    public IWeapon getGiftWeapon() {
        return weapon;
    }

    @Override
    public IArmor getGiftArmor() {
        return armor;
    }
    boolean opened = false;

    @Override
    public void draw(Graphics g) {
        if (opened) {
            return;
        }
        g.drawImage(CustomEngine.readFile(imgPath),
                (int) getPosition().getX(),
                (int) getPosition().getY(), null);
    }

    @Override
    public void affectPlayer(Player p) {
        CustomEngine.playAudio("src/resources/tada.wav");
        if (extraHP != 0) {
            p.incrementHP(extraHP);
        }
        if (weapon != null) {
            p.addWeapon(weapon);
        }

        if (armor != null) {
            p.addArmor(armor);
        }
       
    }
}
