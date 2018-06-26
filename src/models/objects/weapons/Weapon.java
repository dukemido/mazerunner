/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.objects.weapons;

import controllers.CustomEngine;
import java.awt.Graphics;
import java.awt.Point;
import controllers.Game;

/**
 *
 * @author Muhammad
 */
public class Weapon implements IWeapon {

    String name;
    int price, attk, maxBullets, bullets;
    Point position;
    String imgPath;
    boolean fired = false;

    public Weapon(String name, int price, int attk, int maxBullets, String imgPath) {
        this.name = name;
        this.price = price;
        this.attk = attk;
        this.maxBullets = maxBullets;
        this.bullets = maxBullets;
        this.imgPath = imgPath;
        this.position = new Point(0, 0);
    }

    @Override
    public void setFired(boolean f) {
        fired = f;
    }

    @Override
    public String getName() {
        return name;
    }
    

    @Override
    public int getAttk() {
        return attk;
    }

    @Override
    public int getMaxBullets() {
        return maxBullets;
    }

    @Override
    public int getBullets() {
        return bullets;
    }

    @Override
    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public void shoot(ShotDirection dir) {
        fired = true;
        switch (dir) {
            case Down:
                setPosition(new Point((int) getPosition().getX(), (int) (getPosition().getY() + Game.size)));
                break;
            case Top:
                setPosition(new Point((int) getPosition().getX(), (int) (getPosition().getY() - Game.size)));
                break;
            case Right:
                setPosition(new Point((int) getPosition().getX() + Game.size, (int) (getPosition().getY())));
                break;
            case Left:
                setPosition(new Point((int) getPosition().getX() - Game.size, (int) (getPosition().getY())));
                break;

        }

    }

    @Override
    public void draw(Graphics g) {
        if (!fired) {
            return;
        }
        g.drawImage(CustomEngine.readFile(imgPath), (int) getPosition().getX(), (int) getPosition().getY(), null);
    }

    @Override
    public String getImgPath() {
        return this.imgPath;
    }
}
