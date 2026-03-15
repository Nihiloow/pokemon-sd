package com.example.pslikemyversion.logic.types;

import java.util.ArrayList;
import java.util.Arrays;

public class Type {
    private String name;
    private ArrayList<Type> isWeakTo = new ArrayList<Type>();
    private ArrayList<Type> isImmuneTo = new ArrayList<Type>();
    private ArrayList<Type> isResistTo = new ArrayList<Type>();

    public Type(String name) {
        this.name = name;
    }

    public ArrayList<Type> getIsResistTo() {
        return isResistTo;
    }

    public void setIsResistTo(Type... types) {
        setList(isResistTo, types);
    }

    public ArrayList<Type> getIsImmuneTo() {
        return isImmuneTo;
    }

    public void setIsImmuneTo(Type... types) {
        setList(isImmuneTo, types);
    }

    public ArrayList<Type> getIsWeakTo() {
        return isWeakTo;
    }

    public void setIsWeakTo(Type... types) {
        setList(isWeakTo, types);
    }

    public String getName() {
        return name;
    }

    // Generic method to set the list
    private void setList(ArrayList<Type> list, Type... elements) {
        list.clear();
        list.addAll(Arrays.asList(elements));
    }
}