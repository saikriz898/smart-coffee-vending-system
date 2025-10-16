package com.cvs.dao;

import com.cvs.models.Ingredient;
import com.cvs.utils.DBConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngredientDAO {
    private static final Logger logger = LoggerFactory.getLogger(IngredientDAO.class);

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredients ORDER BY name";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                ingredients.add(mapResultSetToIngredient(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting all ingredients: {}", e.getMessage());
        }
        return ingredients;
    }

    public boolean updateIngredientQuantity(int ingredientId, int newQuantity) {
        if (ingredientId <= 0 || newQuantity < 0) {
            throw new IllegalArgumentException("Invalid ingredient ID or quantity");
        }
        
        String sql = "UPDATE ingredients SET quantity = ? WHERE ingredient_id = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, ingredientId);
            
            int result = stmt.executeUpdate();
            if (result > 0) {
                logger.info("Ingredient quantity updated: ingredientId={}, quantity={}", ingredientId, newQuantity);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error updating ingredient quantity: {}", e.getMessage());
        }
        return false;
    }

    public List<Ingredient> getLowStockIngredients() {
        List<Ingredient> lowStockItems = new ArrayList<>();
        String sql = "SELECT * FROM ingredients WHERE quantity <= min_threshold ORDER BY name";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lowStockItems.add(mapResultSetToIngredient(rs));
            }
        } catch (SQLException e) {
            logger.error("Error getting low stock ingredients: {}", e.getMessage());
        }
        return lowStockItems;
    }

    private Ingredient mapResultSetToIngredient(ResultSet rs) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientId(rs.getInt("ingredient_id"));
        ingredient.setName(rs.getString("name"));
        ingredient.setQuantity(rs.getInt("quantity"));
        ingredient.setUnit(rs.getString("unit"));
        ingredient.setMinThreshold(rs.getInt("min_threshold"));
        ingredient.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return ingredient;
    }
}