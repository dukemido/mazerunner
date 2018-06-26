/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.objects.weapons;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Muhammad
 */
public interface IWeapon {

    public Point getPosition();

    public void setPosition(Point point);

    public String getName();

    public String getImgPath();

    public int getAttk();

    public void setBullets(int bullets);

    public int getMaxBullets();

    public int getBullets();

    public void shoot(ShotDirection shotDir);

    public void setFired(boolean f);

    public void draw(Graphics g);
}
