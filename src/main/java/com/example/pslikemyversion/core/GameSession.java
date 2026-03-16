package com.example.pslikemyversion.core;

import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class GameSession {
    private static GameSession instance = new GameSession();
    private Pokemon[] playerTeam = new Pokemon[6];

    private GameSession() {}
    public static GameSession getInstance() { return instance; }

    public void savePokemon(int slot, Pokemon p) {
        playerTeam[slot - 1] = p;
    }

    public Pokemon getPokemon(int slot) {
        return playerTeam[slot - 1];
    }
}
