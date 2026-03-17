package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.core.DatabaseManager;
import com.example.pslikemyversion.logic.moves.Move;
import com.example.pslikemyversion.logic.types.Type;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pokedex {

    public static List<String> getAvailableSpecies() {
        List<String> species = new ArrayList<>();
        String query = "SELECT name FROM pokemon"; // Utilise la colonne 'name'

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
                        new ArrayList<>(), // Types à charger séparément
                        new ArrayList<>(), // Movepool
                        rs.getInt("hp"),
                        rs.getInt("attack"),
                        rs.getInt("sp_attack"),  // <--- C'était 'spe_attack'
                        rs.getInt("defense"),
                        rs.getInt("sp_defense"), // <--- C'était 'spe_defense'
                        rs.getInt("speed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupère uniquement les talents autorisés pour UN Pokémon précis
    public static List<String> getAbilitiesForPokemon(String pokemonName) {
        List<String> abilities = new ArrayList<>();
        String query = "SELECT a.name FROM ability a " +
                "JOIN pokemon p ON p.ability_id = a.id " +
                "WHERE p.name = ?";
        // Note: Si tu as plusieurs talents par Poké, utilise une table de liaison
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pokemonName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) abilities.add(rs.getString("name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return abilities;
    }

    // Récupère uniquement les attaques autorisées (Movepool)
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

    // Récupère la liste de tous les noms d'objets pour la ComboBox
    public static List<String> getAllItems() {
        List<String> items = new ArrayList<>();
        String query = "SELECT name FROM item"; // Basé sur ta structure DB

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

    // Récupère la description d'un objet spécifique (Exigence du PDF )
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
        // On récupère le nom du type1 et du type2 (si existant) via des JOIN
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
                // Le premier type est obligatoire
                types.add(new Type(rs.getString("type1")));

                // Le deuxième type est optionnel (LEFT JOIN + check null)
                String type2Name = rs.getString("type2");
                if (type2Name != null) {
                    types.add(new Type(type2Name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static Move getMoveByName(String moveName) {
        String query = "SELECT * FROM attack WHERE name = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, moveName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // On crée une version concrète de Move (ex: PhysicalMove ou SpecialMove)
                // Pour simplifier ici, on utilise une classe anonyme ou une classe générique
                return new Move(
                        rs.getString("name"),
                        "Description",
                        new Type("Normal"), // À lier au vrai type plus tard
                        rs.getInt("power"), // On récupère la puissance de la DB
                        rs.getString("category") // On récupère Physical/Special
                ) {};
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}