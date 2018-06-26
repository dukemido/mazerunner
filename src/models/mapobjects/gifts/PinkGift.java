/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.gifts;

import models.entities.player.Player;

/**
 *
 * @author Muhammad
 */
public class PinkGift extends Gift {

    public PinkGift(int x, int y) {
        super("src/resources/pinkbox.png", 50, null, null, x, y);
    }

    @Override
    public void affectPlayer(Player player) {
        super.affectPlayer(player);
        player.incrementScore(30);
    }
}
