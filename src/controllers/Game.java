/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.memento.CareTaker;
import controllers.memento.Originator;
import controllers.observer.Observer;
import controllers.observer.Subject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import models.entities.monsters.Monster;
import models.entities.monsters.Octupus;
import models.entities.monsters.SqueekyMob;
import models.entities.player.Player;
import models.game.GameDifficulty;
import models.mapobjects.MapObject;
import models.mapobjects.MapObjectTypes;
import models.mapobjects.Trees;
import models.mapobjects.bombs.BlackBomb;
import models.mapobjects.bombs.BlueBomb;
import models.mapobjects.bombs.Bomb;
import models.mapobjects.gifts.IGift;
import models.mapobjects.gifts.PinkGift;
import models.mapobjects.gifts.YellowGift;
import models.objects.weapons.IWeapon;
import models.mapobjects.BrickWalls;
import models.mapobjects.CheckPoint;
import views.MainFrame;
import views.MazePanel;

/**
 *
 * @author Muhammad
 */
public class Game extends Observer implements Cloneable {

    /**
     * Conventions:
     *
     * maze[row][col]
     *
     * Values: 0 = not-visited node 1 = wall, BrickWall = 2 , 3 = tree , 4 = end
     * 5 = start 6 = checkpoint 7 = starting point for a monster and valid tile
     *
     */
    MainFrame frame;// i used this for observer only.

    MazePanel panel;
    boolean ended = false;
    int[][] maze;
    Random rnd;
    public Player player;
    int score, speed, lifes, seconds_left;
    Point startingPoint;
    GameDifficulty difficulty;
    public ArrayList<MapObject> mapObjs;
    public ArrayList<IGift> gifts;
    public ArrayList<Monster> monsters;
    public ArrayList<String> checkPointsStr;
    public CareTaker memento;
    public Originator originator;
    int checkPoints = 0;

    public Game(Subject subject, MainFrame frame, MazePanel panel, int[][] maze, Player player, GameDifficulty difficulty) {
        this.checkPointsStr = new ArrayList<>();
        this.subject = subject;
        this.subject.attach(this);
        this.frame = frame;
        this.maze = maze;
        this.rnd = new Random();
        this.score = 0;
        this.speed = 1;
        this.lifes = 3;
        this.player = player;
        this.difficulty = difficulty;
        this.mapObjs = new ArrayList<>();
        this.gifts = new ArrayList<>();
        this.ended = false;
        this.panel = panel;
        this.seconds_left = 120;
        this.memento = new CareTaker();
        this.originator = new Originator();
        this.monsters = new ArrayList<>();
    }

    public MazePanel getPanel() {
        return panel;
    }

    public MapObjectTypes getTileType(int x, int y) {
        x -= startingPoint.getX();
        y -= startingPoint.getY();
        for (int k = 0; k < 28; k++) {
            for (int l = 0; l < 30; l++) {
                if (l * size == x && k * size == y) {
                    int t = maze[k][l];
                    switch (t) {
                        case 0:
                            return MapObjectTypes.Tile;
                        case 1:
                            return MapObjectTypes.NotValid;
                        case 2:
                            return MapObjectTypes.BrickWall;
                        case 3:
                            return MapObjectTypes.Trees;
                        case 4:
                            return MapObjectTypes.EndPoint;
                        case 5:
                            return MapObjectTypes.StartPoint;
                        case 6:
                            return MapObjectTypes.CheckPoint;
                        case 7:
                        case 8:
                            return MapObjectTypes.MonsterStart;
                    }
                }
            }
        }
        return MapObjectTypes.NotValid;
    }

    public boolean canMove(int new_x, int new_y) {
        MapObjectTypes type = getTileType(new_x, new_y);
        if (type == MapObjectTypes.BrickWall || type == MapObjectTypes.Trees) {
            for (MapObject p : mapObjs) {
                if (p instanceof BrickWalls) {
                    if (p.getPosition().equals(new Point(new_x, new_y))) {
                        return false;
                    }
                }
                if (p instanceof Trees) {
                    if (p.getPosition().equals(new Point(new_x, new_y))) {
                        return false;
                    }
                }
            }

            return true;
        }
        return type == MapObjectTypes.Tile
                || type == MapObjectTypes.CheckPoint
                || type == MapObjectTypes.StartPoint
                || type == MapObjectTypes.EndPoint
                || type == MapObjectTypes.MonsterStart;
    }

    int bombsSetted = 0;
    public static final int size = 25;
    boolean started = false;

    private int getRate() {
        switch (difficulty) {
            case Easy:
                return 95;
            case Medium:
                return 92;
            case Hard:
                return 90;
        }
        return 0;
    }

    public void refresh(Graphics g) {
        boolean drawn = false;
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                int x = size * col, y = size * row;
                Color color;
                int type = maze[row][col];
                switch (type) {
                    case 1:
                        color = Color.GRAY;
                        break;
                    case 4:
                        color = Color.RED;
                        break;
                    default:
                        color = Color.GREEN;
                }
                g.setColor(color);
                g.fillRect(x, y, size, size);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, size, size);

                if (!started) {
                    if (type == 5) {
                        g.drawImage(player.getImg(), x, y, null);
                        player.setPosition(new Point(x, y));
                        startingPoint = player.getPosition();// Set the starting point for relative co-ordinates.
                    }
                    // Map Objects.
                    MapObject obj = null;
                    switch (type) {
                        case 3:
                            obj = new Trees(x, y);
                            break;
                        case 0:
                            // bombs randomly generated
                            if (rnd.nextInt(100) > getRate()) {
                                if (rnd.nextInt(100) > 50) {
                                    // Blue Bombs
                                    obj = new BlueBomb(x, y);
                                } else {
                                    // Black bombs.
                                    obj = new BlackBomb(x, y);
                                }

                            } else if (rnd.nextInt(100) > 90)// Another try
                            {
                                IGift gift = null;
                                if (rnd.nextInt(100) > 90) { // Yellow gift is kinda unique.
                                    gift = new PinkGift(x, y);
                                } else {
                                    gift = new YellowGift(x, y);
                                }
                                if (gift != null) {
                                    gifts.add(gift);
                                    gift.draw(g);
                                }
                            }
                            break;
                        case 2:
                            // brick walls
                            obj = new BrickWalls(x, y);
                            break;
                        case 6:
                            obj = new CheckPoint(x, y);
                            break;
                        case 7: {
                            Octupus mob = new Octupus(new Point(x, y));
                            monsters.add(mob);
                            mob.draw(g);
                            mob.motion_timer(this, g);
                            break;
                        }
                        case 8: {
                            SqueekyMob mob = new SqueekyMob(new Point(x, y));
                            monsters.add(mob);
                            mob.draw(g);
                            mob.motion_timer(this, g);
                            break;
                        }
                        default:
                            break;
                    }
                    if (obj != null) {
                        mapObjs.add(obj);
                        obj.draw(g);
                    }
                    drawn = true;// To validate player not drawn twice.

                }
            }
        }
        if (!started) {
            started = true;// First time only.
        }
        if (!drawn) {
            if (player.getCurrentWeapon() != null) {
                player.getCurrentWeapon().draw(g);
            }
            mapObjs.forEach((p) -> {
                p.draw(g);
            });
            gifts.forEach((p) -> {
                p.draw(g);
            });
            monsters.forEach((p) -> {
                p.draw(g);
            });
            g.drawImage(player.getImg(), (int) player.getPosition().getX(), (int) player.getPosition().getY(), null);
        }
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getScore() {
        return score;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLifes() {
        return lifes;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public boolean isEnded() {
        return ended;
    }
    Game lastState;
    Player lastPlayerState;

    public ArrayList<String> getScoreBoard() {
        return checkPointsStr;
    }

    public void movePlayer(Player basePlayer, int x, int y) {
        if (ended) {
            return;
        }

        basePlayer.setPosition(new Point(x, y));

        ArrayList<Bomb> bombsRemoved = new ArrayList<>();// Create a new array to hold bombs to remove.. so it wont cause concurrency 
        ArrayList<IGift> giftsRemoved = new ArrayList<>();
        ArrayList<CheckPoint> checkRemoved = new ArrayList<>();

        for (MapObject p : mapObjs) {
            if (p instanceof Bomb) {
                if (p.getPosition().equals(basePlayer.getPosition())) {
                    ((Bomb) p).affectPlayer(player);
                    bombsRemoved.add((Bomb) p);
                }
            }
            if (p instanceof CheckPoint) {
                if (p.getPosition().equals(basePlayer.getPosition())) {
                    // Memento DP 
                    // get clones to be saved.
                    Game cloned = this.clone();
                    cloned.player = player.clone();

                    originator.setState(cloned);
                    memento.add(originator.saveStateToMemento());

                    // Player Stuff
                    score += 100;
                    checkPointsStr.add("Score at checkpoint " + checkPoints + " is: " + score);
                    checkPoints++;
                    checkRemoved.add((CheckPoint) p);

                    // Observer DP
                    subject.notifyAllObservers();
                }
            }
        }

        for (IGift p : gifts) {
            if (p.getPosition().equals(basePlayer.getPosition())) {
                p.affectPlayer(player);
                giftsRemoved.add(p);
            }

        }

        if (basePlayer.getHp() == 0) {
            ended = true;

            if (lifes > 0) {
                this.restartGame();
            } else {
                gameOver();
            }
            return;
        }

        bombsRemoved.forEach((obj) -> {
            mapObjs.remove(obj);
        });

        giftsRemoved.forEach((obj) -> {
            gifts.remove(obj);
        });

        checkRemoved.forEach((obj) -> {
            CustomEngine.playAudio("src/resources/checkpoint.wav");
            mapObjs.remove(obj);
        });

        if (getTileType(x, y) == MapObjectTypes.EndPoint) {
            score += seconds_left * 10;// 10 Score for each second left...
            ended = true;
            CustomEngine.playAudio("src/resources/won.wav");
            JOptionPane.showMessageDialog(null, "Game ended!\nYou took " + (120 - seconds_left) + " seconds.\nYour score:" + score);
        }
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(null, "Game over...");
    }

    public boolean handleBullet(MazePanel r, Player p, IWeapon bullet) {
        MapObjectTypes tileType = getTileType((int) bullet.getPosition().getX(), (int) bullet.getPosition().getY());
        if (tileType == MapObjectTypes.NotValid) {
            bullet.setFired(false);
            return true;
        }
        if (tileType == MapObjectTypes.BrickWall || tileType == MapObjectTypes.Trees) {
            MapObject removedObj = null;
            for (MapObject obj : mapObjs) {
                if (obj instanceof BrickWalls) {
                    if (obj.getPosition().equals(bullet.getPosition())) {
                        removedObj = (BrickWalls) obj;
                        break;
                    }
                }
                if (obj instanceof Trees) {
                    if (obj.getPosition().equals(bullet.getPosition())) {
                        removedObj = (Trees) obj;
                        break;
                    }
                }
            }
            if (removedObj != null) {
                mapObjs.remove(removedObj);
                r.repaint();

                bullet.setFired(false);
                return true;
            }
        }
        if (tileType == MapObjectTypes.Tile) {
            IGift gRemove = null;
            for (IGift g : gifts) {
                if (g instanceof YellowGift) {
                    if (g.getPosition().equals(bullet.getPosition())) {
                        gRemove = (YellowGift) g;
                        break;
                    }
                }
            }
            if (gRemove != null) {
                gifts.remove(gRemove);
                r.repaint();

                bullet.setFired(false);
                return true;
            }

            // Bombs
            MapObject removedObj = null;
            for (MapObject obj : mapObjs) {
                if (obj instanceof BlueBomb) {
                    if (obj.getPosition().equals(bullet.getPosition())) {
                        removedObj = (BlueBomb) obj;
                        break;
                    }
                }
            }
            if (removedObj != null) {
                mapObjs.remove(removedObj);
                r.repaint();
                bullet.setFired(false);
                return true;
            }

            Monster killed = null;
            for (Monster mob : monsters) {
                if (mob.getPosition().equals(bullet.getPosition())) {
                    killed = mob;
                    mob.die(player);
                    break;
                }
            }
            if (killed != null) {
                monsters.remove(killed);
                r.repaint();
                bullet.setFired(false);
                return true;
            }

        }

        return false;
    }

    public void restartGame() {
        this.started = false;
        this.lifes--;
        this.ended = false;
        this.mapObjs.clear();
        this.gifts.clear();
        this.monsters.clear();
        this.seconds_left = 120;
        this.checkPoints = 0;
        this.player.resetGame();
        this.memento = new CareTaker();
        this.originator = new Originator();
    }

    public int getTimeLeft() {
        return seconds_left;
    }

    public void decrementSeconds() {
        if (seconds_left > 0) {
            seconds_left--;
        } else {
            JOptionPane.showMessageDialog(null, "Time-out Resetting game.");
            restartGame();
        }
    }

    @Override
    public void update() {
        frame.updateCheckPoints();
    }

    public Player getPlayer() {
        return player;
    }

    public void save(String absolutePath) {
        if (checkPoints == 0) {
            JOptionPane.showMessageDialog(null, "You didnt reach any check point yet.");
        } else {
            SaveFactory.saveGame(memento.get(checkPoints - 1).getState(), absolutePath);
        }
    }

    public void setTimer(int t) {
        this.seconds_left = t;
    }

    @Override
    public Game clone() {
        try {
            return (Game) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
