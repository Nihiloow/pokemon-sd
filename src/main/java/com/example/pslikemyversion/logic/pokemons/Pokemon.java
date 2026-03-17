package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.passive.Passive;
import com.example.pslikemyversion.logic.types.Type;
import java.util.ArrayList;

public class Pokemon implements IPokemon {
    private String name = "";
    private ArrayList<Type> types = new ArrayList<>();
    private ArrayList<Move> moveSet = new ArrayList<>();
    private ArrayList<Move> movePool = new ArrayList<>();
    private ArrayList<Passive> statuses = new ArrayList<>();
    private Passive ability;
    private Passive heldItem; // Changé de Object à Passive pour la cohérence POO

    private int maxHp;
    private Stat hp, attack, specialAttack, defense, specialDefense, speed;

    public Pokemon(String name, ArrayList<Type> types, ArrayList<Move> movePool,
                   int hp, int attack, int specialAttack, int defense, int specialDefense, int speed) {
        this.name = name;
        this.maxHp = hp;
        this.hp = new Stat(hp);
        this.attack = new Stat(attack);
        this.specialAttack = new Stat(specialAttack);
        this.defense = new Stat(defense);
        this.specialDefense = new Stat(specialDefense);
        this.speed = new Stat(speed);
        this.types = types;
        this.movePool = movePool;
    }

    public void setAbility(Passive ability) {
        this.ability = ability;
    }

    public void setHeldItem(Passive item) {
        this.heldItem = item;
    }

    // --- GETTERS ---
    public String getName() { return name; }
    public ArrayList<Type> getTypes() { return types; }
    public ArrayList<Move> getMoveSet() { return moveSet; }
    public Passive getAbility() { return ability; }
    public Passive getHeldItem() { return heldItem; }
    public Stat getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public Stat getAttack() { return attack; }
    public Stat getSpecialAttack() { return specialAttack; }
    public Stat getDefense() { return defense; }
    public Stat getSpecialDefense() { return specialDefense; }
    public Stat getSpeed() { return speed; }

    @Override
    public ArrayList<Move> getMoves() { return this.moveSet; }

    @Override
    public void attack(Move move) {
        // Logique de déclenchement des effets de l'attaque
        move.mainEffect(this);
    }

    @Override
    public void takeDamages(int amount, String damageType, Type type) {
        int currentHp = this.hp.getStat();
        int newHp = Math.max(0, currentHp - amount);
        this.hp.setStat(newHp);
        System.out.println(this.name + " a maintenant " + newHp + " PV.");
    }
}