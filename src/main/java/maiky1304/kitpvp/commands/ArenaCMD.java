package maiky1304.kitpvp.commands;

import maiky1304.kitpvp.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("arena")){
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + "Jij bent geen speler.");
                return true;
            }
            Player p = (Player)sender;

            if (args.length == 0){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lKitPvP &7- &fPlugin gemaakt voor DDG Trial"));
                p.sendMessage( " ");
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/arena create <arenaid> &7- &fCreert een arena, arena id kan alleen een Integer zijn."));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/arena delete <arenaid> &7- &fVerwijdert een arena, arena id kan alleen een Integer zijn."));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/arena setspawn <arenaid> <spawnid> &7- &fZet een spawn locatie in de arena."));
            }else if (args.length ==1){
                if (args[0].equalsIgnoreCase("create")){
                    p.sendMessage(ChatColor.RED + "Gebruik /arena create <arenaid>");
                    return true;
                }else if (args[0].equalsIgnoreCase("setspawn")){
                    p.sendMessage(ChatColor.RED + "Gebruik /arena setspawn <arenaid> <spawnid>");
                    return true;
                }else if (args[0].equalsIgnoreCase("delete")){
                    p.sendMessage(ChatColor.RED + "Gebruik /arena delete <arenaid>");
                    return true;
                }else {
                    p.sendMessage(ChatColor.RED + "Gebruik /arena setspawn <arenaid> <spawnid>");
                    return true;
                }
            }else if (args.length >= 2){
                if (args.length == 2){
                    // Sub-commando voor het maken van een arena
                    if (args[0].equalsIgnoreCase("create")) {
                        int arenaID;

                        try {
                            arenaID = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e){
                            p.sendMessage(ChatColor.RED + "Dit is geen geldig integer.");
                            return true;
                        }

                        if (ConfigManager.getData().contains("arenas." + args[1])){
                            p.sendMessage(ChatColor.RED + "Arena " + args[1] + " bestaat al!");
                            return true;
                        }

                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.x", p.getLocation().getX());
                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.y", p.getLocation().getY());
                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.z", p.getLocation().getZ());
                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.yaw", p.getLocation().getYaw());
                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.pitch", p.getLocation().getPitch());
                        ConfigManager.getData().set("arenas." + args[1] + ".spawns.1.world", p.getLocation().getWorld().getName());
                        ConfigManager.saveData();

                        p.sendMessage(ChatColor.GREEN + "Er is een arena aangemaakt! (ID: " + arenaID + ")");
                        return true;
                    }else if (args[0].equalsIgnoreCase("delete")){
                        int arenaID;

                        try {
                            arenaID = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e){
                            p.sendMessage(ChatColor.RED + "Dit is geen geldig integer.");
                            return true;
                        }

                        if (!ConfigManager.getData().contains("arenas." + args[1])){
                            p.sendMessage(ChatColor.RED + "Arena " + args[1] + " bestaat niet!");
                            return true;
                        }

                        ConfigManager.getData().set("arenas." + args[1], null);
                        ConfigManager.saveData();

                        p.sendMessage(ChatColor.GREEN + "Arena " + arenaID + " is succesvol verwijderd!");
                        return true;
                    }else{
                        p.sendMessage(ChatColor.RED + "Onbekend sub-commando.");
                        return true;
                    }
                }else if (args.length == 3){
                    /*
                    Spawn Instellingen Arena's
                    Commando /arena setspawn <arenaid> <spawnid>
                     */

                    if (args[0].equalsIgnoreCase("setspawn")){
                        int arenaID;

                        try {
                            arenaID = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e){
                            p.sendMessage(ChatColor.RED + "Dit is geen geldig integer.");
                            return true;
                        }

                        int spawnID;

                        try {
                            spawnID = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e){
                            p.sendMessage(ChatColor.RED + "Dit is geen geldig integer.");
                            return true;
                        }

                        if (!ConfigManager.getData().contains("arenas." + arenaID)){
                            p.sendMessage(ChatColor.RED + "Deze arena bestaat niet!");
                            return true;
                        }

                        double x = p.getLocation().getX();
                        double y = p.getLocation().getY();
                        double z = p.getLocation().getZ();
                        float yaw = p.getLocation().getYaw();
                        float pitch = p.getLocation().getPitch();
                        String worldName = p.getLocation().getWorld().getName();
                        
                        /*
                        * Config zet nieuwe data voor nieuwe spawn location.
                        */

                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".x", x);
                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".y", y);
                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".z", z);
                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".yaw", yaw);
                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".pitch", pitch);
                        ConfigManager.getData().set("arenas." + arenaID + ".spawns." + spawnID + ".world", worldName);
                        ConfigManager.saveData();

                        p.sendMessage(ChatColor.GREEN + "Spawn #" + spawnID + " ingesteld! (" + x + ", " + y + ", " + z + " in " + worldName + ") Yaw: " + yaw + " Pitch: " + pitch);
                        return true;
                    }else{
                        p.sendMessage(ChatColor.RED + "Onbekend sub-commando.");
                        return true;
                    }

                }else{
                    p.sendMessage(ChatColor.RED + "Je gebruikt teveel argumenten.");
                    return true;
                }
            }
        }
        return false;
    }
}
