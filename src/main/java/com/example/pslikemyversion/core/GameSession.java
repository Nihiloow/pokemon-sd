package com.example.pslikemyversion.core;

import com.example.pslikemyversion.logic.pokemons.Team;
import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class GameSession {
    private static GameSession instance = new GameSession();
    private Team playerTeam = new Team();

    private GameSession() {}
    public static GameSession getInstance() { return instance; }

    public Team getPlayerTeam() { return playerTeam; }

    public Pokemon getPokemon(int slot) {
        // if Pokémon in list
        if (slot > 0 && slot <= playerTeam.getPokemons().size()) {
            return playerTeam.getPokemons().get(slot - 1);
        }
        return null;
    }

    public void savePokemon(int slot, Pokemon p) {
        if (slot > playerTeam.getPokemons().size()) {
            playerTeam.addPokemon(p);
        } else {
            playerTeam.getPokemons().set(slot - 1, p);
        }
    }
}