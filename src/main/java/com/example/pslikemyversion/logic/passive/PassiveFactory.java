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
        String query = table.equals("ability")
                ? "SELECT name, description FROM ability WHERE name = ?"
                : "SELECT name, name AS description FROM item WHERE name = ?";

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
                    default -> new AreaTrap(dName, dDesc); //defaults to area trap object
                };
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}