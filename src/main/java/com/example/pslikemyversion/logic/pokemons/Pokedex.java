package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.logic.types.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pokedex {

    // Simule la liste des noms disponibles
    public static List<String> getAvailableSpecies() {
        return Arrays.asList("Charmander", "Bulbasaur", "Squirtle", "Pikachu", "Lucario", "Gengar");
    }

    // Simule la récupération des types selon le nom (Très utile pour ton barème)
    public static ArrayList<Type> getTypesFor(String name) {
        ArrayList<Type> types = new ArrayList<>();
        if (name.equals("Charmander")) types.add(new Type("FIRE"));
        else if (name.equals("Bulbasaur")) types.add(new Type("GRASS"));
        else types.add(new Type("NORMAL")); // Type par défaut pour le test
        return types;
    }

    // Listes génériques pour le moment
    public static List<String> getAvailableAbilities() {
        return Arrays.asList("Blaze", "Overgrow", "Torrent", "Static", "Inner Focus", "Levitate");
    }

    public static List<String> getAvailableItems() {
        return Arrays.asList("Life Orb", "Choice Band", "Leftovers", "Focus Sash");
    }
}
