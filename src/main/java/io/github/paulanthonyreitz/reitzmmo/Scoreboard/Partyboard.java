package io.github.paulanthonyreitz.reitzmmo.Scoreboard;

import io.github.paulanthonyreitz.reitzmmo.ConfigFiles.API;
import io.github.paulanthonyreitz.reitzmmo.Party_System.Party;
import io.github.paulanthonyreitz.reitzmmo.Party_System.Party_API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Partyboard implements Listener {
    Map<UUID, Scoreboard> scoreboards = new HashMap<>();

    @EventHandler
    public void setupPartyBoard(final PlayerJoinEvent e) {
        (new BukkitRunnable() {
            public void run() {
                Objective obj;
                Player p = e.getPlayer();
                String playerName = p.getName();
                Boolean debug = Boolean.FALSE;
                String pm0 = "";
                String pm1 = "";
                String pm2 = "";
                String pm3 = "";
                String pm4 = "";
                String pm5 = "";
                Party party = null;
                List<String> members = null;
                Scoreboard scoreboard = null;
                int size = 0;
                if (Party_API.Party_Leaders.containsKey(playerName)) {
                    party = Party_API.Party_Leaders.get(playerName);
                    members = party.get_MembersList();
                    size = members.size();
                }
                if (Party_API.inParty.containsKey(playerName)) {
                    String leader = (String)Party_API.inParty.get(playerName);
                    party = (Party)Party_API.Party_Leaders.get(leader);
                    members = party.get_MembersList();
                    size = members.size();
                }
                if (null == members || null == party)
                    return;
                if (!Partyboard.this.scoreboards.containsKey(p.getUniqueId())) {
                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    scoreboard = manager.getNewScoreboard();
                    Partyboard.this.scoreboards.put(p.getUniqueId(), scoreboard);
                    obj = scoreboard.registerNewObjective("test", "dummy", ChatColor.BLUE + "Party List");
                    obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                } else {
                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    scoreboard = Partyboard.this.scoreboards.get(p.getUniqueId());
                    obj = scoreboard.getObjective("test");
                }
                for (String s : scoreboard.getEntries())
                    scoreboard.resetScores(s);
                if (size > 0) {
                    pm0 = members.get(0);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm0),"Level");
                    Score score0 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm0 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm0).getHealth() + "\u2665");
                    score0.setScore(0);
                }
                if (members.size() > 1) {
                    pm1 = members.get(1);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm1),"Level");
                    Score score1 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm1 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm1).getHealth() + "\u2665");
                    score1.setScore(1);
                }
                if (members.size() > 2) {
                    pm2 = members.get(2);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm2),"Level");
                    Score score2 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm2 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm2).getHealth() + "\u2665");
                    score2.setScore(2);
                }
                if (members.size() > 3) {
                    pm3 = members.get(3);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm3),"Level");
                    Score score3 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm3 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm3).getHealth() + "\u2665");
                    score3.setScore(3);
                }
                if (members.size() > 4) {
                    pm4 = members.get(4);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm4),"Level");
                    Score score4 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm4 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm4).getHealth() + "\u2665");
                    score4.setScore(4);
                }
                if (members.size() > 5) {
                    pm5 = members.get(5);
                    int lv = API.getPlayerDataFromAPI(Bukkit.getPlayer(pm5),"Level");
                    Score score5 = obj.getScore("[" + lv + "]" +ChatColor.GOLD + pm5 + " " + ChatColor.RED + (int)Bukkit.getPlayer(pm5).getHealth() + "\u2665");
                    score5.setScore(5);
                }
                p.setScoreboard(scoreboard);
            }
        }).runTaskTimer(API.plugin, 20L, 20L);
    }
}
