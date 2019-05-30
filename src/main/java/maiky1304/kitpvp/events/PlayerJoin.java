package maiky1304.kitpvp.events;

import maiky1304.kitpvp.KitPvP;
import maiky1304.kitpvp.config.ConfigManager;
import maiky1304.kitpvp.scoreboard.KitPvPScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();
        if (ConfigManager.getData().getDouble("spawn.x") != 0.0
                && ConfigManager.getData().getDouble("spawn.y") != 0.0
                && ConfigManager.getData().getDouble("spawn.z") != 0.0
                    && ConfigManager.getData().getString("spawn.world") != ""
                    && ConfigManager.getData().getDouble("spawn.yaw") != 0.0
                    && ConfigManager.getData().getDouble("spawn.pitch") != 0.0){
                double spawnX = ConfigManager.getData().getDouble("spawn.x");
                double spawnY = ConfigManager.getData().getDouble("spawn.y");
                double spawnZ = ConfigManager.getData().getDouble("spawn.z");
                String spawnWorld = ConfigManager.getData().getString("spawn.world");
                float yaw = (float)ConfigManager.getData().getDouble("spawn.yaw");
                float pitch = (float) ConfigManager.getData().getDouble("spawn.pitch");

                Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
                player.teleport(spawnLocation);
        }else{
            if (player.isOp()){
                player.sendMessage(ChatColor.RED + "Je hebt nog geen spawn gezet doe dit met /spawn setspawn");
            }
        }

        if (!KitPvP.mysql.playerExists(player.getUniqueId())){
            KitPvP.mysql.createPlayer(player.getUniqueId(), player);
        }
        KitPvPScoreboard.show(player);
    }

}
