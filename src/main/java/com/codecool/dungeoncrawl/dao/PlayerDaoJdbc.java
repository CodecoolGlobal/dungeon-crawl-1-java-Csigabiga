package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            // A SQL statement is precompiled and stored in a PreparedStatement object.
            // This object can then be used to efficiently execute this statement multiple times.
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
        String playerName = player.getPlayerName();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE player SET hp = ?, x = ?, y = ? WHERE player_name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, player.getHp());
            st.setInt(2, player.getX());
            st.setInt(3, player.getY());
            st.setString(4, playerName);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public PlayerModel get(String playerName) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, hp, x, y FROM player WHERE player_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,playerName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            PlayerModel player = new PlayerModel(rs.getString(2), rs.getInt(4),rs.getInt(5));
            player.setHp(rs.getInt(3));
            player.setId(rs.getInt(1));
            return player;
        } catch (SQLException e) {
            throw new RuntimeException("Error while getting player with playerName", e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }

    @Override
    public boolean isTherePlayerName(String playerName){
        try(Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_name FROM player WHERE player_name = ?";
            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, playerName);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
