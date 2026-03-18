package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;
import com.example.pslikemyversion.logic.passive.Passive;

public class CombatSystem {

    public static String performAttack(Pokemon attacker, Pokemon defender, Move move) {
        // object + ability before move
        if (attacker.getAbility() != null) attacker.getAbility().applyEffect(attacker, defender);
        if (attacker.getHeldItem() != null) attacker.getHeldItem().applyEffect(attacker, defender);

        // base damages
        double baseDamage = calculateRawDamage(attacker, defender, move);
        double typeMultiplier = getTypeMultiplier(move.getType(), defender);

        // Stab
        double stab = 1.0;
        for (Type t : attacker.getTypes()) {
            if (t.getName().equalsIgnoreCase(move.getType().getName())) {
                stab = 1.5;
                break;
            }
        }

        int finalDamage = (int) (baseDamage * typeMultiplier * stab);
        defender.takeDamages(finalDamage, move.getCategory(), move.getType());

        // status and side effect
        Passive statusBefore = defender.getStatus();
        move.effect(defender);
        Passive statusAfter = defender.getStatus();

        // log message
        StringBuilder msg = new StringBuilder(attacker.getName() + " uses " + move.getName() + "!");

        if (typeMultiplier > 1) msg.append(" It's super effective!");
        else if (typeMultiplier < 1 && typeMultiplier > 0) msg.append(" It's not very effective...");
        else if (typeMultiplier == 0) msg.append(" It doesn't affect " + defender.getName() + "...");

        // side effect log
        if (statusBefore == null && statusAfter != null) {
            msg.append(" ").append(defender.getName()).append(" is ").append(statusAfter.getName()).append("!");
        }

        return msg.toString() + " (" + finalDamage + " damage)";
    } // the size of this method is an optical illusion

    private static double calculateRawDamage(Pokemon attacker, Pokemon defender, Move move) {
        double atk = move.getCategory().equalsIgnoreCase("Physical") ?
                attacker.getAttack().getRealStat() : attacker.getSpecialAttack().getRealStat();
        double def = move.getCategory().equalsIgnoreCase("Physical") ?
                defender.getDefense().getRealStat() : defender.getSpecialDefense().getRealStat();

        double rand = 0.85 + (Math.random() * 0.15);
        return ((((22 * move.getDamages() * (atk / def)) / 50) + 2) * rand);
    }

    private static double getTypeMultiplier(Type attackType, Pokemon target) {
        double multiplier = 1.0;
        for (Type targetType : target.getTypes()) {
            String atkName = attackType.getName();
            // uses db to get weakness strengths etc...
            if (targetType.getIsImmuneTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) return 0.0;
            if (targetType.getIsWeakTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) multiplier *= 2.0;
            if (targetType.getIsResistTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) multiplier *= 0.5;
        }
        return multiplier;
    }
}