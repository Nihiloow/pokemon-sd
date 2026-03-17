package com.example.pslikemyversion.logic.passive.abilities;

import com.example.pslikemyversion.logic.passive.Passive;
import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class Frisk extends Passive {

    public Frisk(String name, String desc) {
        super(name, desc); // Utilise les données de la DB transmises par la Factory
    }

    @Override
    public void applyEffect(Pokemon owner, Pokemon opponent) {
        // Logique : Multiplie la puissance des attaques par 1.3
        // (Sera utilisé par le CombatSystem)
    }
}
