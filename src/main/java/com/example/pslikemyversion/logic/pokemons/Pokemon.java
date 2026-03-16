package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.passive.Passive;
import com.example.pslikemyversion.logic.types.Type;

import java.util.ArrayList;

public class Pokemon implements IPokemon{
    private String name = "";
    private ArrayList<Type> types = new ArrayList<Type>();
    private ArrayList<Move> moveSet = new ArrayList<Move>();
    private ArrayList<Move> movePool = new ArrayList<Move>();
    private ArrayList<Passive> statuses = new ArrayList<Passive>();
    private Passive ability;
    private Object object;

    private int maxHp;
    private Stat hp;
    private Stat attack;
    private Stat specialAttack;
    private Stat defense;
    private Stat specialDefense;
    private Stat speed;

    public Pokemon(String name, ArrayList<Type> types,
                   ArrayList<Move> movePool, int hp,
                   int attack, int specialAttack,
                   int defense, int specialDefense,
                   int speed){
        this.name = name;

        this.maxHp = hp;
        this.hp = new Stat(hp);
        this.attack = new Stat(attack);
        this.specialAttack = new Stat(specialAttack);
        this.defense = new Stat(defense);
        this.specialDefense = new Stat(specialDefense);
        this.speed = new Stat(speed);

        this.types = types;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public ArrayList<Move> getMoveSet() {
        return moveSet;
    }

    public ArrayList<Move> getMovePool() {
        return movePool;
    }

    public ArrayList<Passive> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<Passive> statuses) {
        this.statuses = statuses;
    }

    public Passive getAbility() {
        return this.ability;
    }

    public Object getObject() {
        return object;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Stat getHp() {
        return hp;
    }

    public Stat getAttack() {
        return attack;
    }

    public Stat getSpecialAttack() {
        return specialAttack;
    }

    public Stat getDefense() {
        return defense;
    }

    public Stat getSpecialDefense() {
        return specialDefense;
    }

    public Stat getSpeed() {
        return speed;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void switchMove(Move move, int spot) {
        if (spot >= 0 && spot < this.moveSet.size()) {
            if (move == null) {
                this.moveSet.set(spot, null); // Directly set the element at the specified spot to null
            } else {
                this.moveSet.set(spot, move);
            }
        } else {
            // Handle invalid spot - could throw exception or do nothing
            System.out.println("Invalid spot for switching move");
        }
    }

    @Override
    public ArrayList<Move> getMoves(){
        return this.moveSet;
    }

    @Override
    public void attack(Move move){

    }

    @Override
    public void takeDamages(int amount, String damageType, Type type) {
        // 1. On récupère les PV actuels
        int currentHp = this.hp.getStat();

        // 2. On calcule les nouveaux PV
        int newHp = currentHp - amount;

        // 3. Sécurité : on ne descend pas en dessous de 0
        if (newHp < 0) {
            newHp = 0;
        }

        this.hp.setStat(newHp);

        System.out.println(this.name + " a maintenant " + newHp + " PV.");
    }
}