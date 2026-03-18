package com.example.pslikemyversion.logic.passive;

import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class Burn extends Passive{

    public Burn (){
        super("brulure",
                "Le pokemon perd 1/6 de sa vie par tour et -50% d'attaque physique.");
    }

    @Override
    public void applyEffect(Pokemon pokemon, Pokemon enemy){
        int damages = (int)(pokemon.getMaxHp()/6);
        pokemon.getHp().setStat(pokemon.getHp().getStat() - damages);
    }
}
