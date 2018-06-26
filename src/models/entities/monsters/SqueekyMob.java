/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.monsters;

import java.awt.Point;
import models.entities.player.Player;

/**
 *
 * @author Muhammad
 */
public class SqueekyMob extends Monster {

    public SqueekyMob(Point position) {
        super("SqueekyMob", "src/resources/monster1.png", MotionType.Horizontal, position);
    }

    @Override
    public void die(Player killer) {
        killer.incrementScore(50);

        if (killer.getCurrentWeapon() != null) {
            killer.incrementBullets();
        }
    }
}
