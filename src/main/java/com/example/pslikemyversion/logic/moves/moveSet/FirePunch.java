package com.example.pslikemyversion.logic.moves.moveSet;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.passive.Burn;
import com.example.pslikemyversion.logic.pokemons.Pokemon;
import com.example.pslikemyversion.logic.types.Type;

public class FirePunch extends Move {
    public FirePunch() {
        // Paramètres : Nom, Description, Type (Feu=2), Puissance, Catégorie
        super("FirePunch", "Can apply burn.", new Type("Feu"), 90, "Special");
    }

    @Override
    public void effect(Pokemon enemy) {
        double random = Math.random();
        // 10% de chance d'infliger la brûlure si l'ennemi n'a pas déjà un statut
        if (random >= 0.9 && enemy.getStatus() == null) {
            enemy.setStatus(new Burn());
            // On peut ajouter un print console pour débugger
            System.out.println(enemy.getName() + " is burned!");
        }
    }
}