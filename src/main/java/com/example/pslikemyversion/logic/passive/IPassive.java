package com.example.pslikemyversion.logic.passive;

import com.example.pslikemyversion.logic.pokemons.Pokemon;

public interface IPassive {
    public void mainEffect(Pokemon pokemon);
    public void sideEffect(Pokemon pokemon);
}
