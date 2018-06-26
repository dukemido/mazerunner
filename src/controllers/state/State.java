/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.state;

import models.entities.player.Player;

/**
 *
 * @author Muhammad
 */
public interface State {
    public void doAction(Player player);
}
