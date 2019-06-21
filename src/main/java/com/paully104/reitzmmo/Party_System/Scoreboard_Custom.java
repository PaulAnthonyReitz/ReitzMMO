package com.paully104.reitzmmo.Party_System;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.util.Objects;

/**
 * Created by Paul on 8/11/2016.
 */
public class Scoreboard_Custom implements Listener {

    @EventHandler(priority= EventPriority.HIGHEST)
    public void scoreboard(PlayerJoinEvent e) {
        try {
            Scoreboard sb = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();

            Objective objective = sb.registerNewObjective("showhealth", "health");
            objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
            objective.setDisplayName(ChatColor.RED + "❤");

            for (Player online : Bukkit.getOnlinePlayers()) {

                if(!(online.hasMetadata("NPC"))) {
                    online.setScoreboard(sb);
                    online.setHealth(online.getHealth());
                }
            }
        } catch (IllegalArgumentException ignored) {

        }
    }
}
