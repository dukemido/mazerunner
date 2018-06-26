/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.observer.Subject;
import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import models.entities.monsters.Monster;
import models.entities.monsters.Octupus;
import models.entities.monsters.SqueekyMob;
import models.entities.player.Player;
import models.entities.player.PlayerCharacter;
import models.game.GameDifficulty;
import models.mapobjects.MapObject;
import models.mapobjects.*;
import models.mapobjects.bombs.BlackBomb;
import models.mapobjects.bombs.BlueBomb;
import models.mapobjects.bombs.Bomb;
import models.mapobjects.gifts.IGift;
import models.mapobjects.gifts.PinkGift;
import models.mapobjects.gifts.YellowGift;
import models.objects.armor.BlackArmor;
import models.objects.armor.IArmor;
import models.objects.armor.SteelArmor;
import models.objects.weapons.BlackBullet;
import models.objects.weapons.GreenBullet;
import models.objects.weapons.IWeapon;
import org.json.*;
import views.MainFrame;
import views.MazePanel;

/**
 *
 * @author Muhammad
 */
public class SaveFactory {

    public static void saveGame(Game maze_game, String path) {
        try {
            JSONObject obj = new JSONObject();
            JSONArray mapObjsarray = new JSONArray();

            for (MapObject s : maze_game.mapObjs) {
                JSONObject ob = new JSONObject();
                ob.put("x", (int) s.getPosition().getX());
                ob.put("y", (int) s.getPosition().getY());
                if (s instanceof BlueBomb) {
                    ob.put("type", "BlueBomb");
                } else if (s instanceof BlackBomb) {
                    ob.put("type", "BlackBomb");
                } else if (s instanceof Trees) {
                    ob.put("type", "tree");
                } else if (s instanceof BrickWalls) {
                    ob.put("type", "wall");
                } else if (s instanceof CheckPoint) {
                    ob.put("type", "checkpoint");
                }
                mapObjsarray.put(ob);
            }

            JSONArray giftsarray = new JSONArray();
            for (IGift g : maze_game.gifts) {
                JSONObject ob = new JSONObject();
                ob.put("x", (int) g.getPosition().getX());
                ob.put("y", (int) g.getPosition().getY());
                if (g instanceof YellowGift) {
                    ob.put("type", "yellow");
                } else if (g instanceof PinkGift) {
                    ob.put("type", "pink");
                }
                giftsarray.put(ob);
            }
            obj.put("gifts", giftsarray);

            obj.put("mapobjs", mapObjsarray);

            JSONArray monsters = new JSONArray();
            for (Monster s : maze_game.monsters) {
                JSONObject ob = new JSONObject();
                ob.put("x", (int) s.getPosition().getX());
                ob.put("y", (int) s.getPosition().getY());
                if (s instanceof Octupus) {
                    ob.put("type", "octupus");
                } else if (s instanceof SqueekyMob) {
                    ob.put("type", "squeeky");
                }
                monsters.put(ob);
            }
            obj.put("monsters", monsters);

            obj.put("player_name", maze_game.player.getName());
            obj.put("player_char", maze_game.player.getCharacter());
            obj.put("player_hp", maze_game.player.getHp());
            obj.put("player_x", (int) maze_game.player.getPosition().getX());
            obj.put("player_y", (int) maze_game.player.getPosition().getY());

            IWeapon wep = maze_game.player.getCurrentWeapon();
            if (wep != null) {
                if (wep instanceof BlackBullet) {
                    obj.put("weapon_type", "black");
                } else if (wep instanceof GreenBullet) {
                    obj.put("weapon_type", "green");
                }
                obj.put("weapon_bullets", wep.getBullets());
            }

            IArmor armor = maze_game.player.getCurrentArmor();
            if (armor != null) {
                if (armor instanceof SteelArmor) {
                    obj.put("armor_type", "steel");
                } else if (armor instanceof BlackArmor) {
                    obj.put("armor_type", "black");
                }
            }
            obj.put("difficulty", maze_game.getDifficulty());
            obj.put("lifes", maze_game.getLifes());
            obj.put("score", maze_game.getScore());
            obj.put("timer", maze_game.getTimeLeft());

            try (FileWriter file = new FileWriter(path)) {
                file.write(obj.toString());
                file.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }

    public static Game loadGame(Subject subject, MazePanel panel, MainFrame frame, String path) {
        try {
            JSONObject jsonObject = parseJSONFile(path);

            // Player
            Player p = new Player(PlayerCharacter.valueOf(jsonObject.getString("player_char")), jsonObject.getString("player_name"));
            p.setHp(jsonObject.getInt("player_hp"));
            p.setPosition(new Point(jsonObject.getInt("player_x"), jsonObject.getInt("player_y")));

            // Armor
            if (jsonObject.has("armor_type")) {
                String type = jsonObject.getString("armor_type");
                if (type.equals("black")) {
                    p.addArmor(new BlackArmor());
                } else {
                    p.addArmor(new SteelArmor());
                }
            }

            // Weapon
            if (jsonObject.has("weapon_type")) {
                String type = jsonObject.getString("weapon_type");
                if (type.equals("black")) {
                    p.addWeapon(new BlackBullet());
                } else {
                    p.addWeapon(new GreenBullet());
                }
                p.setBullets(jsonObject.getInt("weapon_bullets"));
            }

            // Game configuration
            Game g = new Game(subject, frame, panel, MainFrame.maze, p, GameDifficulty.valueOf(jsonObject.getString("difficulty")));
            g.setLifes(jsonObject.getInt("lifes"));
            g.setScore(jsonObject.getInt("score"));
            g.setTimer(jsonObject.getInt("timer"));
            g.setLifes(jsonObject.getInt("lifes"));

            // MapObjects.
            JSONArray mapObjs = jsonObject.getJSONArray("mapobjs");
            for (int i = 0; i < mapObjs.length(); i++) {
                JSONObject j = mapObjs.getJSONObject(i);
                switch (j.getString("type")) {
                    case "BlueBomb":
                        g.mapObjs.add(new BlueBomb(j.getInt("x"), j.getInt("y")));
                        break;
                    case "BlackBomb":
                        g.mapObjs.add(new BlackBomb(j.getInt("x"), j.getInt("y")));
                        break;
                    case "tree":
                        g.mapObjs.add(new Trees(j.getInt("x"), j.getInt("y")));
                        break;
                    case "wall":
                        g.mapObjs.add(new BrickWalls(j.getInt("x"), j.getInt("y")));
                        break;
                    case "checkpoint":
                        g.mapObjs.add(new CheckPoint(j.getInt("x"), j.getInt("y")));
                        break;
                }
            }

            // Monsters.
            JSONArray monsters = jsonObject.getJSONArray("monsters");
            for (int i = 0; i < monsters.length(); i++) {
                JSONObject j = monsters.getJSONObject(i);
                String type = j.getString("type");
                Monster m = null;
                switch (type) {
                    case "octupus":
                        m = new Octupus(new Point(j.getInt("x"), j.getInt("y")));
                        break;
                    case "squeeky":
                        m = new SqueekyMob(new Point(j.getInt("x"), j.getInt("y")));
                        break;
                }
                if (m != null) {
                    m.motion_timer(g, panel.getGraphics());
                    g.monsters.add(m);

                }
            }

            // Gifts.
            JSONArray gifts = jsonObject.getJSONArray("gifts");
            for (int i = 0; i < gifts.length(); i++) {
                JSONObject j = gifts.getJSONObject(i);
                switch (j.getString("type")) {
                    case "yellow":
                        g.gifts.add(new YellowGift(j.getInt("x"), j.getInt("y")));
                        break;
                    case "pink":
                        g.gifts.add(new PinkGift(j.getInt("x"), j.getInt("y")));
                        break;
                }
            }

            g.startingPoint = new Point(0, 0);
            g.started = true;
            return g;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
