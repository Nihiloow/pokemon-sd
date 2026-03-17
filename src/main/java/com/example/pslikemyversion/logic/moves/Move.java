package com.example.pslikemyversion.logic.moves;

import com.example.pslikemyversion.logic.types.Type;

public abstract class Move implements Imove {
    private String name;
    private String description;
    private Type type;
    private int damages; // Correspond à la "Puissance" [cite: 219]
    private String category; // "Physical" ou "Special" [cite: 222]

    public Move(String name, String description, Type type, int damages, String category) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.damages = damages;
        this.category = category;
    }

    public String getName() { return name; }
    public int getDamages() { return damages; } // Ajouté [cite: 219]
    public String getCategory() { return category; } // Ajouté [cite: 222]
    public Type getType() { return type; }
}