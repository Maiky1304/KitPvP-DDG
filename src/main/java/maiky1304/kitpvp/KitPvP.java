package maiky1304.kitpvp;

import maiky1304.kitpvp.commands.ArenaCMD;
import maiky1304.kitpvp.commands.KitPvPCMD;
import maiky1304.kitpvp.config.ConfigManager;
import maiky1304.kitpvp.database.MySQL;
import maiky1304.kitpvp.events.JoinGameSign;
import maiky1304.kitpvp.events.PlayerJoin;
import maiky1304.kitpvp.events.PlayerKill;
import maiky1304.kitpvp.scoreboard.KitPvPScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


/*
KitPvP Project voor DDG
 */

public final class KitPvP extends JavaPlugin {

    public static MySQL mysql;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        loadConfigs();
        loadListeners();
        loadCommands();

        String databaseName = ConfigManager.getConfig().getString("database.dbName");
        String tableName = ConfigManager.getConfig().getString("database.table");
        String ip = ConfigManager.getConfig().getString("database.host");
        String username = ConfigManager.getConfig().getString("database.username");
        String password = ConfigManager.getConfig().getString("database.password");
        int port = ConfigManager.getConfig().getInt("database.port");

        mysql = new MySQL(this, ip, port, databaseName, username, password, tableName);

        mysql.connectMySQL();

        long end = System.currentTimeMillis();
        getLogger().info("The plugin has been loaded! (Took: " + ((end-start)) + "ms)");
        runScoreboardTimer();
    }

    public void loadListeners(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new JoinGameSign(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKill(), this);
    }

    public void loadCommands(){
        getCommand("kitpvp").setExecutor(new KitPvPCMD());
        getCommand("arena").setExecutor(new ArenaCMD());
    }

    public void loadConfigs(){
        ConfigManager.loadConfig();
        ConfigManager.loadKits();
        ConfigManager.loadUsers();
    }

    public static void runScoreboardTimer(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KitPvP.getPlugin(KitPvP.class), new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    if (KitPvPScoreboard.playerList.contains(p)){
                        KitPvPScoreboard.show(p);
                    }
                }
            }
         },0L,600L);
    }

}
