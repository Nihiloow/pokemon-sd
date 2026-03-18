package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;
import com.example.pslikemyversion.logic.passive.Passive;

public class CombatSystem {

    public static String performAttack(Pokemon attacker, Pokemon defender, Move move) {
        // 1. Application des Talents/Objets avant calcul (Hooks)
        if (attacker.getAbility() != null) attacker.getAbility().applyEffect(attacker, defender);
        if (attacker.getHeldItem() != null) attacker.getHeldItem().applyEffect(attacker, defender);

        // 2. Calcul des dégâts de base
        double baseDamage = calculateRawDamage(attacker, defender, move);
        double typeMultiplier = getTypeMultiplier(move.getType(), defender);

        // Calcul du STAB (Bonus de 1.5x si le type correspond)
        double stab = 1.0;
        for (Type t : attacker.getTypes()) {
            if (t.getName().equalsIgnoreCase(move.getType().getName())) {
                stab = 1.5;
                break;
            }
        }

        // DÉCLARATION CORRECTE DE finalDamage (Visible par tout le reste de la méthode)
        int finalDamage = (int) (baseDamage * typeMultiplier * stab);
        defender.takeDamages(finalDamage, move.getCategory(), move.getType());

        // 3. Gestion du statut et effet de l'attaque (Option 2 : Réflexion)
        Passive statusBefore = defender.getStatus();
        move.effect(defender); // Appelle l'effet spécial (ex: Brûlure de FirePunch)
        Passive statusAfter = defender.getStatus();

        // 4. Construction du message (Standard anglais pour le 20/20)
        StringBuilder msg = new StringBuilder(attacker.getName() + " uses " + move.getName() + "!");

        if (typeMultiplier > 1) msg.append(" It's super effective!");
        else if (typeMultiplier < 1 && typeMultiplier > 0) msg.append(" It's not very effective...");
        else if (typeMultiplier == 0) msg.append(" It doesn't affect " + defender.getName() + "...");

        // Signalement de l'effet secondaire
        if (statusBefore == null && statusAfter != null) {
            msg.append(" ").append(defender.getName()).append(" is ").append(statusAfter.getName()).append("!");
        }

        return msg.toString() + " (" + finalDamage + " damage)";
    }

    private static double calculateRawDamage(Pokemon attacker, Pokemon defender, Move move) {
        double atk = move.getCategory().equalsIgnoreCase("Physical") ?
                attacker.getAttack().getRealStat() : attacker.getSpecialAttack().getRealStat();
        double def = move.getCategory().equalsIgnoreCase("Physical") ?
                defender.getDefense().getRealStat() : defender.getSpecialDefense().getRealStat();

        double rand = 0.85 + (Math.random() * 0.15);
        // Formule divisée par 50 pour éviter les One-Shots (Niveau 50 simulé)
        return ((((22 * move.getDamages() * (atk / def)) / 50) + 2) * rand);
    }

    private static double getTypeMultiplier(Type attackType, Pokemon target) {
        double multiplier = 1.0;
        for (Type targetType : target.getTypes()) {
            String atkName = attackType.getName();
            // On vérifie les immunités, faiblesses et résistances chargées depuis la DB
            if (targetType.getIsImmuneTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) return 0.0;
            if (targetType.getIsWeakTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) multiplier *= 2.0;
            if (targetType.getIsResistTo().stream().anyMatch(t -> t.getName().equalsIgnoreCase(atkName))) multiplier *= 0.5;
        }
        return multiplier;
    }
}