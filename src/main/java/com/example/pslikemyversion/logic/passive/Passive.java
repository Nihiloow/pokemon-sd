package com.example.pslikemyversion.logic.passive;

import com.example.pslikemyversion.logic.pokemons.Pokemon;

public abstract class Passive {
    protected String name;
    protected String description;

    // Le super() recevra les chaînes de caractères extraites de la DB
    public Passive(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void applyEffect(Pokemon owner, Pokemon opponent);

    public String getName() { return name; }
    public String getDescription() { return description; }
}