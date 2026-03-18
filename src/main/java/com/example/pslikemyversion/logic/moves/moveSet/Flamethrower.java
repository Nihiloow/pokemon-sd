package com.example.pslikemyversion.logic.moves.moveSet;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.passive.Burn;
import com.example.pslikemyversion.logic.pokemons.Pokemon;

public class Flamethrower extends Move {

    public Flamethrower(){
        super("lance flamme", "Throws flames at the ennemy, can apply burn.",
                null, 90, "physical");
    }

    @Override
    public void effect(Pokemon enemy){
        double random  = Math.random();
        if (random >= 0.9 && enemy.get){
            enemy.setStatus(new Burn());
        }
    }
}
