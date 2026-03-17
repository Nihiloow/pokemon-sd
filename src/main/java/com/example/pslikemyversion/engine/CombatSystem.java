package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.pokemons.Team;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.moves.Move;

public class CombatSystem {

    // On utilise la logique de Team pour savoir si le combat continue [cite: 32]
    public static boolean isBattleOver(Team playerTeam, Team cpuTeam) {
        return playerTeam.isDefeated() || cpuTeam.isDefeated();
    }

    public static String performAttack(Pokemon attacker, Pokemon defender, Move move) {
        // Utilisation de ta formule de dégâts (Physique vs Spécial) [cite: 211, 214]
        int damage = calculateDamage(attacker, defender, move);

        defender.takeDamages(damage, move.getCategory(), move.getType());

        return attacker.getName() + " utilise " + move.getName() + " ! " +
                defender.getName() + " perd " + damage + " PV.";
    }

    private static int calculateDamage(Pokemon attacker, Pokemon defender, Move move) {
        double atk = move.getCategory().equalsIgnoreCase("Physical") ?
                attacker.getAttack().getRealStat() : attacker.getSpecialAttack().getRealStat();
        double def = move.getCategory().equalsIgnoreCase("Physical") ?
                defender.getDefense().getRealStat() : defender.getSpecialDefense().getRealStat();

        // Multiplicateur aléatoire (0.85 à 1.0) [cite: 213, 216]
        double rand = 0.85 + (Math.random() * 0.15);

        return (int) (move.getDamages() * (atk / def) * rand);
    }
}