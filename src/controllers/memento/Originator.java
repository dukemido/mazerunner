/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.memento;

import controllers.Game;

/**
 *
 * @author Muhammad
 */
public class Originator {

    private Game state;

    public void setState(Game state) {
        this.state = state;
    }

    public Game getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
}
