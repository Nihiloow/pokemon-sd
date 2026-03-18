package com.example.pslikemyversion.logic.pokemons;

public class Stat {
    private int stat;
    private double coef = 1.0;

    public Stat(int stat){
        this.stat = stat;
    }

    public int getStat(){
        return this.stat;
    }

    public void setStat(int newValue) {
        this.stat = newValue;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef){
        this.coef = coef;
    }

    public int getRealStat(){
        // prevents division by 0
        if (this.coef == 0) return this.stat;
        return (int)(this.stat * this.coef);
    }
}