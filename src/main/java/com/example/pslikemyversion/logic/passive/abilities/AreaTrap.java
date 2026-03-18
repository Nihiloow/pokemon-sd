package com.example.pslikemyversion.logic.passive.abilities;

import com.example.pslikemyversion.logic.passive.Passive;
import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class AreaTrap extends Passive {

    public AreaTrap(String name, String desc) {
        super(name, desc); // uses db data from Factory class
    }

    @Override
    public void applyEffect(Pokemon owner, Pokemon opponent) {
        //wip
    }
}
