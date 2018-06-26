/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import java.awt.Point;

/**
 *
 * @author Muhammad
 */
public interface IEntity {

    public String getName();

    public Point getPosition();

    public int getAttack();

    public int getDefense();

    public void setPosition(Point point);

    public void setAttack(int attack);

    public void setDefense(int defense);
}
