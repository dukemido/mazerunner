/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.mapobjects;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Muhammad
 */
public abstract class MapObject implements IMapObject {

    Point position;

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    public MapObject(int x, int y) {
        this.position = new Point(x, y);
    }

    public abstract void draw(Graphics g);
}
