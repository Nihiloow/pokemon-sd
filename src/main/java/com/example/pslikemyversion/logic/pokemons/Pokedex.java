package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.core.DatabaseManager;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Constructor;

public class Pokedex {

    public static List<String> getAvailableSpecies() {
        List<String> species = new ArrayList<>();
        String query = "SELECT name FROM pokemon";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                species.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return species;
    }

    public static Pokemon getPokemonByName(String name) {
        String query = "SELECT * FROM pokemon WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Pokemon(
                        rs.getString("name"),
                        new ArrayList<>(), // loads types separately
                        new ArrayList<>(), // Movepool
                        rs.getInt("hp"),
                        rs.getInt("attack"),
                        rs.getInt("sp_attack"),
                        rs.getInt("defense"),
                        rs.getInt("sp_defense"),
                        rs.getInt("speed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // gets talents allowed on said Pokémon
    public static List<String> getAbilitiesForPokemon(String pokemonName) {
        List<String> abilities = new ArrayList<>();
        String query = "SELECT a.name FROM ability a " +
                "JOIN pokemon p ON p.ability_id = a.id " +
                "WHERE p.name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pokemonName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) abilities.add(rs.getString("name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return abilities;
    }

    // Rgets movepool for Pokémon
    public static List<String> getMovesForPokemon(String pokemonName) {
        List<String> moves = new ArrayList<>();
        String query = "SELECT a.name FROM attack a " +
                "JOIN pokemon_attacks pa ON a.id = pa.attack_id " +
                "JOIN pokemon p ON p.id = pa.pokemon_id " +
                "WHERE p.name = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pokemonName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) moves.add(rs.getString("name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return moves;
    }

    // Gets list of objects
    public static List<String> getAllItems() {
        List<String> items = new ArrayList<>();
        String query = "SELECT name FROM item";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                items.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static String getItemDescription(String itemName) {
        String query = "SELECT description FROM item WHERE name = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, itemName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("description");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Aucune description disponible.";
    }

    public static ArrayList<Type> getTypesFor(String pokemonName) {
        ArrayList<Type> types = new ArrayList<>();
        String query = "SELECT t1.name AS type1, t2.name AS type2 " +
                "FROM pokemon p " +
                "JOIN type t1 ON p.type1_id = t1.id " +
                "LEFT JOIN type t2 ON p.type2_id = t2.id " +
                "WHERE p.name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, pokemonName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                types.add(new Type(rs.getString("type1")));
                String type2Name = rs.getString("type2");
                if (type2Name != null) {
                    types.add(new Type(type2Name));
                }
            }
        } catch (SQLException e) {e.printStackTrace();}
        return types;
    }

    public static Move getMoveByName(String moveName) {
        try {
            // looks for class of move
            String className = "com.example.pslikemyversion.logic.moves.moveSet." + formatClassName(moveName);
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor();
            return (Move) ctor.newInstance();

        } catch (Exception e) {
            // case no class exists
            String query = "SELECT a.*, t.name AS type_name FROM attack a " +
                    "JOIN type t ON a.type_id = t.id WHERE a.name = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, moveName);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new Move(rs.getString("name"),
                            "Generic Move",
                            new Type(rs.getString("type_name")),
                            rs.getInt("power"),
                            rs.getString("category")) {};
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return null;
    }

    private static String formatClassName(String name) {
        if (name.equalsIgnoreCase("Poing-Feu")) return "FirePunch";
        return name.replace(" ", "").replace("-", "");
    }

    public static void loadTypeInteractions(Type type) {
        String query = "SELECT t_atk.name AS attacker, ti.multiplier " +
                "FROM type_interaction ti " +
                "JOIN type t_atk ON ti.attacker_type_id = t_atk.id " +
                "JOIN type t_def ON ti.defender_type_id = t_def.id " +
                "WHERE t_def.name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, type.getName());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                double mult = rs.getDouble("multiplier");
                Type attackerType = new Type(rs.getString("attacker"));

                if (mult == 0) type.getIsImmuneTo().add(attackerType);
                else if (mult == 0.5) type.getIsResistTo().add(attackerType);
                else if (mult == 2.0) type.getIsWeakTo().add(attackerType);
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}