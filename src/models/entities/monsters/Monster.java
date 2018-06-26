/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities.monsters;

import controllers.CustomEngine;
import controllers.Game;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import models.entities.Entity;
import models.entities.player.Player;
import models.game.GameDifficulty;
import models.objects.weapons.ShotDirection;
import views.MazePanel;

/**
 *
 * @author Muhammad
 */
public abstract class Monster extends Entity {

    String imgPath;
    MotionType motion;
    ShotDirection motionDirection;
    boolean killed = false;

    public boolean isKilled() {
        return killed;
    }
    GameDifficulty difficulty;

    public Monster(String name, String imgPath, MotionType motion, Point position) {
        super(name, 0, 0, position);
        this.imgPath = imgPath;
        this.motion = motion;
        this.difficulty = difficulty;
        // Intializing it with some values to avoid null
        if (this.motion == MotionType.Vertical) {
            motionDirection = ShotDirection.Top;
        } else {
            motionDirection = ShotDirection.Right;
        }
    }

    void reverseMotion() {
        if (motion == MotionType.Vertical) {
            if (motionDirection == ShotDirection.Top) {
                motionDirection = ShotDirection.Down;
            } else {
                motionDirection = ShotDirection.Top;
            }
        } else {
            if (motionDirection == ShotDirection.Right) {
                motionDirection = ShotDirection.Left;
            } else {
                motionDirection = ShotDirection.Right;
            }
        }

    }
    public Timer timer;

    int getTime(Game maze) {
        switch (maze.getDifficulty()) {
            default:
            case Easy:
                return 300;
            case Medium:
                return 200;
            case Hard:
                return 100;
        }
    }

    public void motion_timer(Game maze, Graphics g) {

        timer = new Timer(getTime(maze), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (killed) {
                    return;
                }
                if (motion == MotionType.Vertical) {
                    int new_y = (int) getPosition().getY();
                    if (motionDirection == ShotDirection.Top) {
                        new_y += Game.size;
                    } else {
                        new_y -= Game.size;
                    }
                    if (maze.canMove((int) getPosition().getX(), new_y)) {
                        setPosition(new Point((int) getPosition().getX(), new_y));
                    } else {
                        reverseMotion();
                    }
                } else {
                    int new_x = (int) getPosition().getX();
                    if (motionDirection == ShotDirection.Right) {
                        new_x += Game.size;
                    } else {
                        new_x -= Game.size;
                    }
                    if (maze.canMove(new_x, (int) getPosition().getY())) {
                        setPosition(new Point(new_x, (int) getPosition().getY()));
                    } else {
                        reverseMotion();
                    }
                }
                if (maze.getPlayer().getPosition().equals(getPosition()) && !killed) {
                    if (CustomEngine.Rnd.nextInt(100) > 50) {
                        maze.player.setBullets(0);
                    } else {

                        timer.stop();
                        if (maze.getLifes() == 0) {
                            maze.gameOver();
                        } else {
                            maze.restartGame();
                        }
                        JOptionPane.showMessageDialog(null, "You were killed by the monster.");
                    }
                    CustomEngine.playAudio("src/resources/gold.wav");
                }
                maze.getPanel().repaint();
            }
        });
        timer.start();

    }

    public void die(Player killer) {
        timer.stop();
        killed = true;
    }

    public void draw(Graphics g) {
        if (killed) {
            return;
        }
        g.drawImage(CustomEngine.readFile(imgPath), (int) getPosition().getX(), (int) getPosition().getY(), null);
    }

}
