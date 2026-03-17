package com.example.pslikemyversion.logic.passive;

import com.example.pslikemyversion.core.DatabaseManager;
import com.example.pslikemyversion.logic.passive.abilities.SheerForce;
import com.example.pslikemyversion.logic.passive.abilities.Static;
import com.example.pslikemyversion.logic.passive.abilities.SwordOfRuin;

import java.sql.*;

public class PassiveFactory {

    public static Passive createAbility(String abilityName) {
        String query = "SELECT name, description FROM ability WHERE name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, abilityName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String desc = rs.getString("description");

                return switch (name) {
                    case "Sans Limite" -> new SheerForce(name, desc);
                    case "Statik" -> new Static(name, desc);
                    case "Épée du Fléau" -> new SwordOfRuin(name, desc);
//                    default -> new DefaultAbility(name, desc);
                    default -> throw new IllegalStateException("Unexpected value: " + name);
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
