package com.example.pslikemyversion.logic.types;

import java.util.ArrayList;

public interface IType {
    public ArrayList<Type> getIsResistTo();
    public ArrayList<Type> getIsWeakTo();
    public ArrayList<Type> getIsImmuneTo();
    public ArrayList<Type> setIsResistTo();
    public ArrayList<Type> setIsWeakTo();
    public ArrayList<Type> setIsImmuneTo();
}
