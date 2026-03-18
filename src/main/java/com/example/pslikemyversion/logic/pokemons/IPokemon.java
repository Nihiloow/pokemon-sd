package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;

import java.util.ArrayList;

public interface IPokemon {
    public ArrayList<Move> getMoves();
    public void takeDamages(int amount, String damageType, Type type);
}
