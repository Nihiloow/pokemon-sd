package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.moves.Move;

public class CombatSystem {

    public static String executeMove(Pokemon attacker, Pokemon target, Move move) {
        int power = 40;

        int damage = (attacker.getAttack().getRealStat() / target.getDefense().getRealStat()) * power;

        int newHp = target.getHp().getStat() - damage;
        // target.getHp().setStat(Math.max(0, newHp));

        return attacker.getName() + " utilise " + move.getName() + " ! "
                + target.getName() + " perd " + damage + " PV.";
    }
}