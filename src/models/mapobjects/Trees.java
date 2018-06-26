/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects;

import controllers.CustomEngine;
import java.awt.Graphics;

/**
 *
 * @author Muhammad
 */
public class Trees extends MapObject {

    public Trees(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(CustomEngine.readFile("src/resources/tree.png"),
                (int) getPosition().getX(),
                (int) getPosition().getY(), null);
    }

}
