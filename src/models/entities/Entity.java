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
public abstract class Entity implements IEntity {

    String name;
    int attack, defense;
    Point position;

    public Entity(String name, int attack, int defense, Point position) {
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.position = position;
    }

    @Override
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

}
