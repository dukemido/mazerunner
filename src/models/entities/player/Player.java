/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.player;

import controllers.CustomEngine;
import controllers.Game;
import controllers.state.State;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import models.objects.armor.IArmor;
import models.objects.weapons.IWeapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import models.entities.Entity;
import models.objects.weapons.ShotDirection;
import views.MazePanel;

/**
 *
 * @author Muhammad
 */
public class Player extends Entity implements Cloneable {

    PlayerCharacter character;
    int hp;
    BufferedImage img;
    State state;
    Game myMaze;
    ArrayList<IWeapon> allWeapons;
    ArrayList<IArmor> allArmors;
    IWeapon currentWeapon;
    IArmor currentArmor;
    Timer timer;

    public Player(PlayerCharacter character, String name) {
        super(name, 100, 100, new Point(0, 0));
        this.character = character;
        this.hp = 100;
        this.allWeapons = new ArrayList<>();
        this.allArmors = new ArrayList<>();
        this.img = CustomEngine.readFile(imgPath());
        this.state = null;
    }

    public void setGame(Game game) {
        this.myMaze = game;
    }

    @Override
    public int getDefense() {
        int def = 100;// player defense.
        if (currentArmor != null) {
            def += currentArmor.getDefense();
        }
        return def;
    }

    @Override
    public int getAttack() {
        int atk = 100;// player attk.
        if (currentWeapon != null) {
            atk += currentWeapon.getAttk();
        }
        return atk;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void incrementBullets() {
        if (currentWeapon != null) {
            currentWeapon.setBullets(currentWeapon.getMaxBullets());
        }
    }

    public void addWeapon(IWeapon wep) {

        boolean found = false;
        for (IWeapon w : allWeapons) {
            if (w.getName().equals(wep.getName())) {
                w.setBullets(w.getMaxBullets());
                found = true;
            }
        }

        if (!found) {
            allWeapons.add(wep);
        }

        this.currentWeapon = wep;
    }

    public void addArmor(IArmor armor) {

        boolean found = false;
        for (IArmor w : allArmors) {
            if (w.getName().equals(armor.getName())) {
                found = true;
            }
        }

        if (!found) {
            allArmors.add(armor);
        }

        this.currentArmor = armor;
    }

    private String imgPath() {
        switch (character) {
            default:
            case Mario:
                return "src/resources/mario.png";
            case Robot:
                return "src/resources/robot.png";
            case Mushroom:
                return "src/resources/mushroom.png";
        }
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public PlayerCharacter getCharacter() {
        return character;
    }

    public int getHp() {
        return hp;
    }

    public ArrayList<IWeapon> getAllWeapons() {
        return allWeapons;
    }

    public ArrayList<IArmor> getAllArmors() {
        return allArmors;
    }

    public void resetGame() {
        if (myMaze != null) {
            this.myMaze.setScore(0);
        }
        this.allArmors.clear();
        this.allWeapons.clear();
        this.currentArmor = null;
        this.currentWeapon = null;
        this.hp = 100;
    }

    public void decrementHP(int i) {
        int dmg = Math.max(0, i - getDefense());

        if (dmg < hp) {
            hp -= dmg;
        } else {
            hp = 0;
        }

    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
    Point bulletPos;

    public void shootWeapon(MazePanel r, ShotDirection dir) {
        if (currentWeapon == null) {
            return;
        }

        if (this.myMaze == null) {
            return;
        }
        if (myMaze.isEnded()) {
            return;
        }

        bulletPos = getPosition();
        if (timer != null) {
            timer.stop();
        }

        if (currentWeapon.getBullets() == 0) {
            r.repaint();
            return;
        }
        currentWeapon.setBullets(currentWeapon.getBullets() - 1);

        Player p = this;

        CustomEngine.playAudio("src/resources/shot.wav");
        timer = new Timer(100 / myMaze.getSpeed(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWeapon.setPosition(bulletPos);
                currentWeapon.shoot(dir);

                bulletPos = currentWeapon.getPosition();

                if (myMaze.handleBullet(r, p, currentWeapon)) {
                    timer.stop();
                }

                r.repaint();
            }
        });
        timer.start();
    }

    public void incrementHP(int extraHP) {
        if (extraHP + hp > 100) {
            hp = 100;
        } else {
            hp += extraHP;
        }
    }

    public IWeapon getCurrentWeapon() {
        return currentWeapon;
    }

    public IArmor getCurrentArmor() {
        return currentArmor;
    }

    public void decrementScore(int i) {
        if (myMaze != null) {
            myMaze.setScore(Math.max(0, myMaze.getScore() - i));
        }
    }

    public void incrementScore(int i) {
        if (myMaze != null) {
            myMaze.setScore(myMaze.getScore() + i);
        }
    }

    @Override
    public Player clone() {
        try {
            return (Player) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setBullets(int i) {
        this.currentWeapon.setBullets(i);
    }

}
