/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects.bombs;

import controllers.CustomEngine;
import java.awt.Graphics;
import models.entities.player.Player;

/**
 *
 * @author Muhammad
 */
public class BlueBomb extends Bomb {
    
    public BlueBomb(int x, int y) {
        super(x, y);
    }
    
    @Override
    public void affectPlayer(Player player) {
        super.affectPlayer(player);
        player.decrementHP(130);
        player.decrementScore(10);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(CustomEngine.readFile("src/resources/bomb2.png"),
                (int) getPosition().getX(),
                (int) getPosition().getY(), null);
    }
    
}
