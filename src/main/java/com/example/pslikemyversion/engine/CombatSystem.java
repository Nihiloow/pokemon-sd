package com.example.pslikemyversion.engine;

import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;

public class CombatSystem {

    public static String performAttack(Pokemon attacker, Pokemon defender, Move move) {
        // 1. Calcul des dégâts de base (Stats)
        double baseDamage = calculateRawDamage(attacker, defender, move);

        // 2. Calcul du multiplicateur de type (Logique de ta collègue déplacée ici)
        double typeMultiplier = getTypeMultiplier(move.getType(), defender);

        int finalDamage = (int) (baseDamage * typeMultiplier);
        defender.takeDamages(finalDamage, move.getCategory(), move.getType());

        // 3. Construction du message de log
        String effectiveness = "";
        if (typeMultiplier > 1) effectiveness = " C'est super efficace !";
        else if (typeMultiplier == 0) effectiveness = " Ça n'affecte pas " + defender.getName() + "...";
        else if (typeMultiplier < 1) effectiveness = " Ce n'est pas très efficace...";

        return attacker.getName() + " utilise " + move.getName() + " !" + effectiveness +
                " (" + finalDamage + " dégâts)";
    }

    private static double calculateRawDamage(Pokemon attacker, Pokemon defender, Move move) {
        double atk = move.getCategory().equalsIgnoreCase("Physical") ?
                attacker.getAttack().getRealStat() : attacker.getSpecialAttack().getRealStat();
        double def = move.getCategory().equalsIgnoreCase("Physical") ?
                defender.getDefense().getRealStat() : defender.getSpecialDefense().getRealStat();

        double rand = 0.85 + (Math.random() * 0.15);
        // Formule équilibrée (Division par 50 pour éviter les OS)
        return ((((22 * move.getDamages() * (atk / def)) / 50) + 2) * rand);
    }

    private static double getTypeMultiplier(Type attackType, Pokemon target) {
        double multiplier = 1.0;
        for (Type targetType : target.getTypes()) {
            // On compare les noms des types pour éviter les erreurs d'objets
            String atkName = attackType.getName();

            if (containsTypeName(targetType.getIsImmuneTo(), atkName)) return 0.0;
            if (containsTypeName(targetType.getIsWeakTo(), atkName)) multiplier *= 2.0;
            if (containsTypeName(targetType.getIsResistTo(), atkName)) multiplier *= 0.5;
        }
        return multiplier;
    }

    private static boolean containsTypeName(java.util.List<Type> list, String name) {
        return list.stream().anyMatch(t -> t.getName().equalsIgnoreCase(name));
    }
}