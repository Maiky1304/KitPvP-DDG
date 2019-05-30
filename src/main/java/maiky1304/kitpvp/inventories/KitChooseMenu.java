package maiky1304.kitpvp.inventories;

import maiky1304.kitpvp.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitChooseMenu {

    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(player, 3*9, "§cKies een kit!");

        for (String key : ConfigManager.getKits().getKeys(false)){
            ItemStack item = new ItemStack(Material.valueOf(ConfigManager.getKits().getString(key + ".options.item")));

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ConfigManager.getKits().getString(key + ".options.kitname")));

            List<String> loreToSet = new ArrayList<>();
            for (String s : ConfigManager.getKits().getStringList(key + ".options.lore")){
                loreToSet.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(loreToSet);

            item.setItemMeta(meta);

            inv.addItem(item);
        }
        player.openInventory(inv);
    }

}
