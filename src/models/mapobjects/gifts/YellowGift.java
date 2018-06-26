/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.gifts;

import controllers.decorator.BlackArmorDecorator;
import controllers.decorator.SteelArmorDecorator;
import java.util.Random;
import models.entities.player.Player;
import models.objects.armor.BlackArmor;
import models.objects.armor.IArmor;
import models.objects.armor.SteelArmor;
import models.objects.weapons.IWeapon;
import models.objects.weapons.BlackBullet;
import models.objects.weapons.GreenBullet;

/**
 *
 * @author Muhammad
 */
public class YellowGift extends Gift {

    public YellowGift(int x, int y) {
        super("src/resources/yellowbox.png", 0, getRandWep(), getRandArmor(), x, y);
    }

    @Override
    public void affectPlayer(Player player) {
        super.affectPlayer(player);
        player.incrementScore(50);
    }

    static Random rnd = new Random();

    static IWeapon getRandWep() {
        if (rnd.nextInt(100) > 50) {
            return new BlackBullet();
        } else {
            return new GreenBullet();
        }

    }

    static IArmor getRandArmor() {
        if (rnd.nextInt(100) > 50) {
            return new SteelArmorDecorator(new SteelArmor());
        } else {
            return new BlackArmorDecorator(new BlackArmor());
        }
    }
}
