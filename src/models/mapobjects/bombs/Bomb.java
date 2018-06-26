/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.bombs;

import controllers.CustomEngine;
import java.awt.Graphics;
import models.entities.player.Player;
import models.mapobjects.MapObject;

/**
 *
 * @author Muhammad
 */
public abstract class Bomb extends MapObject {

    boolean exploded = false;

    public Bomb(int x, int y) {
        super(x, y);
    }

    public void playExplosionSound() {
        CustomEngine.playAudio("src/resources/bomb.wav");
    }

    public void affectPlayer(Player player) {
        if (exploded) {
            return;
        }
        exploded = true;
        playExplosionSound();
    }

}
