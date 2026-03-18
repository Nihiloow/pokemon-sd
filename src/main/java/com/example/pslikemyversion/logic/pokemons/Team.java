package com.example.pslikemyversion.logic.pokemons;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private List<Pokemon> pokemons;
    private int activePokemonIndex = 0;

    public Team() {
        this.pokemons = new ArrayList<>();
    }

    public void addPokemon(Pokemon p) {
        if (pokemons.size() < 6) {
            pokemons.add(p);
        }
    }

    public void removePokemon(int index) {
        if (index >= 0 && index < pokemons.size()) {
            pokemons.remove(index);
        }
    }

    public boolean isDefeated() {
        return pokemons.stream().allMatch(p -> p.getHp().getStat() <= 0);
    }

    public Pokemon getActivePokemon() {
        return pokemons.get(activePokemonIndex);
    }

    public void setActivePokemonIndex(int index) {
        this.activePokemonIndex = index;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
