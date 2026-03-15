package com.example.pslikemyversion.logic.pokemons;

public class Stat {
    private int stat;
    private double coef;

    public Stat(int stat){
        this.stat = stat;
    }

    public int getStat(){
        return this.stat;
    }

    public double getCoef() {
        return coef;
    }

    public void setCoef(double coef){
        this.coef = coef;
    }

    public int getRealStat(){
        return (int)(this.stat * this.coef);
    }
}
