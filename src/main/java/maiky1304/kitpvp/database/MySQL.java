package maiky1304.kitpvp.database;

import maiky1304.kitpvp.KitPvP;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySQL {

    private Connection connection;
    private String host, database, username, password, table;
    private int port;
    private KitPvP plugin;

    public MySQL(KitPvP plugin, String host, int port, String database, String username, String password, String table){
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.table = table;
        this.plugin = plugin;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void connectMySQL() {//576,000
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis ();

        try {

            synchronized (this) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(
                        DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,
                                username, password));
                createTableIfNotExists ( );
                end = System.currentTimeMillis ();
                plugin.getLogger ().info ( "Database was connected in " + ((end-start)) + "ms." );
            }
        } catch (SQLException e) {
            end = System.currentTimeMillis ();
            plugin.getLogger ().info ( "Database could not connect in " + ((end-start)) + "ms." );
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            plugin.getLogger ().info ( "Database could not connect in " + ((end-start)) + "ms." );
            e.printStackTrace();
        }
    }

    public void createTableIfNotExists() {
        if (this.connection != null) {
            try {
                PreparedStatement stat = this.getConnection().prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " + this.table +
                                " (UUID varchar(255)," +
                                "NAME varchar(255)," +
                                "KILLS int(255)," +
                                "DEATHS varchar(255));"
                );
                stat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = this.getConnection()
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = this.getConnection()
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            if (playerExists(uuid) != true) {
                PreparedStatement insert = this.getConnection()
                        .prepareStatement("INSERT INTO " + this.table + " (UUID,NAME,KILLS,DEATHS) VALUES (?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setInt ( 4, 0);
                insert.setDouble ( 5, 0.00);

                insert.executeUpdate ();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getKills(UUID uuid) {
        int kills = 0;
        try {
            PreparedStatement statement = this.getConnection()
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            kills = results.getInt ("KILLS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kills;
    }

    public void setKills(UUID uuid, int kills) {
        try {
            PreparedStatement statement = this.getConnection ( )
                    .prepareStatement ( "UPDATE " + this.table + " SET KILLS=? WHERE UUID=?" );
            statement.setInt ( 1, kills );
            statement.setString ( 2, uuid.toString ( ) );
            statement.executeUpdate ( );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDeaths(UUID uuid) {
        int deaths = 0;
        try {
            PreparedStatement statement = this.getConnection()
                    .prepareStatement("SELECT * FROM " + this.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            deaths = results.getInt ("DEATHS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deaths;
    }

    public void setDeaths(UUID uuid, int deaths) {
        try {
            PreparedStatement statement = this.getConnection ( )
                    .prepareStatement ( "UPDATE " + this.table + " SET DEATHS=? WHERE UUID=?" );
            statement.setInt ( 1, deaths );
            statement.setString ( 2, uuid.toString ( ) );
            statement.executeUpdate ( );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
