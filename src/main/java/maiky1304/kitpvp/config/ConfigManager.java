package maiky1304.kitpvp.config;

import maiky1304.kitpvp.KitPvP;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File usersFile;
    private static FileConfiguration usersConfig;

    private static File configFile;
    private static FileConfiguration configConfig;

    private static File kitsFile;
    private static FileConfiguration kitsConfig;

    private static KitPvP plugin = KitPvP.getPlugin(KitPvP.class);


    public static FileConfiguration getConfig() {
        return configConfig;
    }

    public static void saveConfig(){
        try {
            configConfig.save ( configFile );
        } catch (IOException e){
            e.printStackTrace ();
        }
    }

    public static void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        configConfig= new YamlConfiguration ();
        try {
            configConfig.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /* Users Config
     */

    public static FileConfiguration getData() {
        return usersConfig;
    }

    public static void loadUsers() {
        usersFile = new File(plugin.getDataFolder(), "data.yml");
        if (!usersFile.exists()) {
            usersFile.getParentFile().mkdirs();
            plugin.saveResource("data.yml", false);
        }

        usersConfig= new YamlConfiguration ();
        try {
            usersConfig.load(usersFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveData(){
        try {
            usersConfig.save ( usersFile );
        } catch (IOException e){
            e.printStackTrace ();
        }
    }

    /* Messages Config
     */

    public static FileConfiguration getKits() {
        return kitsConfig;
    }

    public static void loadKits() {
        kitsFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitsFile.exists()) {
            kitsFile.getParentFile().mkdirs();
            plugin.saveResource("kits.yml", false);
        }

        kitsConfig= new YamlConfiguration ();
        try {
            kitsConfig.load(kitsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveKits(){
        try {
            kitsConfig.save ( kitsFile );
        } catch (IOException e){
            e.printStackTrace ();
        }
    }

}
