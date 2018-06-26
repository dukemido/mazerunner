/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.entities.player.PlayerCharacter;
import models.entities.player.Player;
import models.entities.*;

/**
 *
 * @author Muhammad
 */

// Singleton pattern design
public class PlayerFactory {

    public static Player getNewPlayer(PlayerCharacter character, String name) {
        return new Player(character, name);
    }
}
