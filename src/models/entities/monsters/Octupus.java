/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.monsters;

import java.awt.Point;
import models.entities.player.Player;
import views.MazePanel;

/**
 *
 * @author Muhammad
 */
public class Octupus extends Monster {

    public Octupus(Point position) {
        super("Octupus", "src/resources/monster2.png", MotionType.Vertical, position);
    }

    @Override
    public void die(Player killer) {
        killer.incrementScore(60);

        if (killer.getCurrentWeapon() != null) {
            killer.incrementHP(50);
        }
    }
}
