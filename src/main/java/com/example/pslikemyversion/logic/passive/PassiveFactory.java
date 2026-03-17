package com.example.pslikemyversion.logic.passive;

import com.example.pslikemyversion.core.DatabaseManager;
import com.example.pslikemyversion.logic.passive.abilities.*;
import java.sql.*;

public class PassiveFactory {

    public static Passive createAbility(String abilityName) {
        return fetchPassiveFromDB("ability", abilityName);
    }

    public static Passive createItem(String itemName) {
        return fetchPassiveFromDB("item", itemName);
    }

    private static Passive fetchPassiveFromDB(String table, String name) {
        // On adapte la requête selon la table car 'item' n'a pas de colonne description
        String query = table.equals("ability")
                ? "SELECT name, description FROM ability WHERE name = ?"
                : "SELECT name, name AS description FROM item WHERE name = ?";
        // Note : On utilise "name AS description" pour ne pas faire planter rs.getString("description")

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String dName = rs.getString("name");
                String dDesc = rs.getString("description");

                return switch (dName) {
                    case "Sans Limite" -> new SheerForce(dName, dDesc);
                    case "Statik" -> new Static(dName, dDesc);
                    case "Épée du Fléau" -> new SwordOfRuin(dName, dDesc);
                    // Si c'est un objet (ou un talent sans classe propre), on utilise AreaTrap par défaut
                    default -> new AreaTrap(dName, dDesc);
                };
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}