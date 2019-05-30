package maiky1304.kitpvp.events;

import maiky1304.kitpvp.config.ConfigManager;
import maiky1304.kitpvp.inventories.KitChooseMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JoinGameSign implements Listener {

    public static List<Player> list = new ArrayList<>();

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if (event.getLine(0).equalsIgnoreCase("[DDGPvP]")){
            if (event.getLine(1).equalsIgnoreCase("joingame")){
                event.setLine(0, "§c[DDG]");
                event.setLine(1, "§fKlik om deze game");
                event.setLine(2, "§fte joinen!");
            }
        }
    }

    @EventHandler
    public void close(InventoryClickEvent event){
        Player p = (Player)event.getWhoClicked();

        if (event.getInventory().getName().equalsIgnoreCase("§cKies een kit!")){
            String displayName = event.getCurrentItem().getItemMeta().getDisplayName();

            displayName = displayName.replaceAll("§", "&");

            for (String key : ConfigManager.getKits().getKeys(false)){
                if (displayName.equalsIgnoreCase(ConfigManager.getKits().getString(key + ".options.kitname"))){
                    for (String item : ConfigManager.getKits().getStringList(key + ".items")){
                        if (item.contains("HELMET")){
                            ItemStack it = new ItemStack(Material.valueOf(item));
                            p.getInventory().setHelmet(it);
                        }
                        if (item.contains("CHESTPLATE")){
                            ItemStack it = new ItemStack(Material.valueOf(item));
                            p.getInventory().setChestplate(it);
                        }
                        if (item.contains("LEGGINGS")){
                            ItemStack it = new ItemStack(Material.valueOf(item));
                            p.getInventory().setLeggings(it);
                        }
                        if (item.contains("BOOTS")){
                            ItemStack it = new ItemStack(Material.valueOf(item));
                            p.getInventory().setBoots(it);
                        }
                        if (!item.contains("HELMET") && !item.contains("CHESTPLATE") && !item.contains("LEGGINGS") && !item.contains("BOOTS")){
                            ItemStack it = new ItemStack(Material.valueOf(item));
                            p.getInventory().addItem(it);
                        }
                    }

                    Random rand = new Random();
                    int max = ConfigManager.getData().getConfigurationSection("arenas.1.spawns").getKeys(false).size();
                    int min = 1;

                    int randomSpawnpoint = rand.nextInt((max - min) + 1) + min;

                    double x = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".x");
                    double y = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".y");
                    double z = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".z");
                    double yaw = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".yaw");
                    double pitch = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".pitch");
                    String worldName = ConfigManager.getData().getString("arenas.1.spawns." + randomSpawnpoint + ".world");

                    Location location = new Location(Bukkit.getWorld(worldName), x, y, z, (float)yaw, (float)pitch);
                    p.teleport(location);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event){
        if (event.getClickedBlock().getType() == Material.WALL_SIGN){
            Sign s = (Sign) event.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase("§c[DDG]")
            && s.getLine(1).equalsIgnoreCase("§fKlik om deze game")){
                KitChooseMenu.open(event.getPlayer());
            }
        }

        if (event.getClickedBlock().getType() == Material.SIGN){
            Sign s = (Sign) event.getClickedBlock().getState();
            if (s.getLine(0).equalsIgnoreCase("§c[DDG]")
                    && s.getLine(1).equalsIgnoreCase("§fKlik om deze game")){
                Random rand = new Random();
                int max = ConfigManager.getData().getConfigurationSection("arenas.1.spawns").getKeys(false).size();
                int min = 1;

                int randomSpawnpoint = getRandomNumberInRange(min, max);

                double x = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".x");
                double y = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".y");
                double z = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".z");
                double yaw = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".yaw");
                double pitch = ConfigManager.getData().getDouble("arenas.1.spawns." + randomSpawnpoint + ".pitch");
                String worldName = ConfigManager.getData().getString("arenas.1.spawns." + randomSpawnpoint + ".world");

                Location location = new Location(Bukkit.getWorld(worldName), x, y, z, (float)yaw, (float)pitch);
                event.getPlayer().teleport(location);
            }
        }
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
