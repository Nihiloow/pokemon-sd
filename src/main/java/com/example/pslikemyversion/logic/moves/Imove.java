package com.example.pslikemyversion.logic.moves;

import com.example.pslikemyversion.logic.pokemons.Pokemon;

public interface Imove {
    public void mainEffect(Pokemon pokemon);
    public void sideEffect(Pokemon pokemon);
}
