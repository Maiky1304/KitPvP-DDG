package maiky1304.kitpvp.commands;

import maiky1304.kitpvp.config.ConfigManager;
import maiky1304.kitpvp.inventories.KitChooseMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitPvPCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("kitpvp")){
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "Jij bent geen speler.");
                return true;
            }

            Player p = (Player)sender;

            if (!p.hasPermission("kitpvp.admin")){
                p.sendMessage(ChatColor.RED + "You don't have access to this command.");
                return true;
            }


            if (args.length == 0){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lKitPvP &7- &fPlugin gemaakt voor DDG Trial"));
                p.sendMessage( " ");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/kitpvp spawn &7- &fTeleporteert je naar de ingestelde spawn/lobby."));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/kitpvp setspawn &7- &fVeranderd de ingestelde spawn/lobby naar jouw huidige locatie."));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/kitpvp kits &7- &fOpent het kit menu deze kun je ook openen door [DDGKits] op de 1e sign line de zetten."));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/kitpvp reload &7- &fReload alle configuraties."));
                return true;
            }else if (args.length == 1){
                if (args[0].equalsIgnoreCase("spawn")){
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
                        float pitch = (float)ConfigManager.getData().getDouble("spawn.pitch");

                        Location spawnLocation = new Location(Bukkit.getWorld(spawnWorld), spawnX, spawnY, spawnZ, yaw, pitch);
                        p.teleport(spawnLocation);
                        p.sendMessage(ChatColor.GREEN + "Je bent geteleporteerd naar de ingestelde spawn location.");
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.RED + "Je hebt nog geen spawn gezet doe dit met /spawn setspawn");
                        return true;
                    }
                }else if (args[0].equalsIgnoreCase("setspawn")){
                    double x = p.getLocation().getX();
                    double y = p.getLocation().getY();
                    double z = p.getLocation().getZ();
                    float yaw = p.getLocation().getYaw();
                    float pitch = p.getLocation().getPitch();
                    String worldName = p.getLocation().getWorld().getName();

                    ConfigManager.getData().set("spawn.x", x);
                    ConfigManager.getData().set("spawn.y", y);
                    ConfigManager.getData().set("spawn.z", z);
                    ConfigManager.getData().set("spawn.yaw", (double)yaw);
                    ConfigManager.getData().set("spawn.pitch", (double)pitch);
                    ConfigManager.getData().set("spawn.world", worldName);
                    ConfigManager.saveData();

                    p.sendMessage(ChatColor.GREEN + "Je hebt de spawn gezet op (" + x + ", " + y + ", " + z + " in " + worldName + ") Pitch: " + pitch + " Yaw: " + yaw);
                    return true;
                }else if (args[0].equalsIgnoreCase("kits")){
                    // kits
                    KitChooseMenu.open(p);
                    p.sendMessage(ChatColor.GREEN +"Je hebt het kit keuze menu geopend!");
                    return true;
                }else if (args[0].equalsIgnoreCase("reload")){
                    ConfigManager.loadKits();
                    ConfigManager.loadUsers();
                    ConfigManager.loadConfig();

                    p.sendMessage(ChatColor.GREEN + "Je hebt de configuratie succesvol herladen!");
                    return true;
                }else{
                    p.sendMessage(ChatColor.RED + "Dit sub-commando wordt niet herkend!");
                    return true;
                }
            }

        }
        return false;
    }
}
