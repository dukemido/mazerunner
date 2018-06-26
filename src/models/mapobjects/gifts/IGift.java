/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.gifts;

import java.awt.Graphics;
import java.awt.Point;
import models.entities.player.Player;
import models.objects.armor.IArmor;
import models.objects.weapons.IWeapon;

/**
 *
 * @author Muhammad
 */
public interface IGift {

    public int getGiftHP();

    public IWeapon getGiftWeapon();

    public IArmor getGiftArmor();

    public void draw(Graphics g);

    public String getImgPath();

    public Point getPosition();

    public void affectPlayer(Player p);
}
