package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }
    public PlayerModel getPlayerModel(String name){
        return playerDao.get(name);
    }
    public void saveGameState(byte[] serializedCurrentMap, PlayerModel playerModel) {
        GameState gameState = new GameState(serializedCurrentMap, playerModel);
        gameStateDao.add(gameState);
    }
    public void updatePlayer(Player player){
        PlayerModel model = new PlayerModel(player);
        playerDao.update(model);
    }
    public boolean checkPlayerNameInDb(String name) {
        if (name == null){
            return false;
        }
        boolean test = playerDao.isTherePlayerName(name);
        return test;

    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeon-crawler";
        String user = "csaba";
        String password = "jelszo";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
