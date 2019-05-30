package maiky1304.kitpvp.scoreboard;

import maiky1304.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KitPvPScoreboard {

    public static List<Player> playerList = new ArrayList<Player>();

    public static void show(Player p){
        double kills = KitPvP.mysql.getKills(p.getUniqueId());
        double deaths = KitPvP.mysql.getDeaths(p.getUniqueId());
        double kdRatio = kills / deaths;

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("kitpvp", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§f§lKitPvP");
        obj.getScore("    ").setScore(7);
        obj.getScore("§cStats").setScore(6);
        obj.getScore("§7Kills: §f" + kills).setScore(5);
        obj.getScore("§7Deaths: §f" + deaths).setScore(4);

        DecimalFormat df = new DecimalFormat("0.00");

        obj.getScore("§7KD Ratio: §f" + df.format(kdRatio)).setScore(3);
        obj.getScore("   ").setScore(2);
        obj.getScore("§cplay.server.nl").setScore(1);

        p.setScoreboard(board);
    }

}
