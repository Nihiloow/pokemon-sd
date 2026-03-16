package com.example.pslikemyversion.logic.pokemons;

import com.example.pslikemyversion.core.DatabaseManager;
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
                // On extrait les stats de la table
                return new Pokemon(
                        rs.getString("name"),
                        new ArrayList<>(),
                        null,
                        rs.getInt("hp"),
                        rs.getInt("attack"),
                        rs.getInt("spe_attack"),
                        rs.getInt("defense"),
                        rs.getInt("spe_defense"),
                        rs.getInt("speed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}