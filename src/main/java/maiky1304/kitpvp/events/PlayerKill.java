package maiky1304.kitpvp.events;

import maiky1304.kitpvp.KitPvP;
import maiky1304.kitpvp.config.ConfigManager;
import maiky1304.kitpvp.scoreboard.KitPvPScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerKill implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setDeathMessage("§8[§c§l!§8] " + ChatColor.WHITE + event.getEntity().getKiller().getName() + " §cheeft §f" + event.getEntity().getName() + "§c gekilled.");
        event.setDroppedExp(0);
        event.setKeepInventory(true);
        event.setKeepLevel(true);

        // Give points
        KitPvP.mysql.setKills(event.getEntity().getKiller().getUniqueId(), KitPvP.mysql.getKills(event.getEntity().getKiller().getUniqueId()) + 1);
        KitPvP.mysql.setDeaths(event.getEntity().getUniqueId(), KitPvP.mysql.getDeaths(event.getEntity().getUniqueId()) + 1);

        KitPvPScoreboard.show(event.getEntity());
        KitPvPScoreboard.show(event.getEntity().getKiller());
        event.getEntity().getInventory().clear();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        double spawnX = ConfigManager.getData().getDouble("spawn.x");
        double spawnY = ConfigManager.getData().getDouble("spawn.y");
        double spawnZ = ConfigManager.getData().getDouble("spawn.z");
        String spawnWorld = ConfigManager.getData().getString("spawn.world");
        float yaw = (float)ConfigManager.getData().getDouble("spawn.yaw");
        float pitch = (float) ConfigManager.getData().getDouble("spawn.pitch");

        Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
        event.getPlayer().teleport(spawnLocation);
    }

}
