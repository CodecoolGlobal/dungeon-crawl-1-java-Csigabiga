package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public void add(GameState state) {
        // Date currentDate = new Date(System.currentTimeMillis());
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state (current_map, player_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setBytes(1, state.getCurrentMap());
            statement.setInt(2, state.getPlayerId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET current_map = ?, saved_at = CURRENT_TIMESTAMP WHERE player_id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBytes(1, state.getCurrentMap());
            st.setInt(2, state.getPlayerId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int playerId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, current_map , player_id FROM game_state WHERE player_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1,playerId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            GameState gameState = new GameState(rs.getBytes(2));
            gameState.setId(rs.getInt(1));
            return gameState;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting gameState with player_id", e);
        }
    }

    @Override
    public List<GameState> getAll() {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, current_map, saved_at, player_id FROM game_state";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            List<GameState> result = new ArrayList<>();
            while (rs.next()) {
                GameState gameState = new GameState(rs.getBytes(2));
                gameState.setId(rs.getInt(1));
                result.add(gameState);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading all gamestates", e);
        }
    }
}
