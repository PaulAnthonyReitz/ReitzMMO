package com.paully104.reitzmmo.Skills;

import com.paully104.reitzmmo.ConfigFiles.API;
import com.paully104.reitzmmo.ItemData.nameSpaceKey;
import com.paully104.reitzmmo.Party_System.Party_Queue;
import com.paully104.reitzmmo.PlayerData.PlayerData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class onRightClickWeaponSkills implements Listener {

    ArrayList<String> underFireUsers = new ArrayList<>();

    private final boolean underFireEnabled = API.weaponskillConfig.getBoolean("Swords.WeaponSkills.Under_Fire.Enabled");
    private final int underFireDuration = API.weaponskillConfig.getInt("Swords.WeaponSkills.Under_Fire.DurationInSeconds");
    private final int underFireSpeedIncrease = API.weaponskillConfig.getInt("Swords.WeaponSkills.Under_Fire.MovementSpeedIncreasePercent");
    private final int underFireLevelNeeded = API.weaponskillConfig.getInt("Swords.WeaponSkills.Under_Fire.LevelRequirement");
    private final int underFireDurabilityLoss = API.weaponskillConfig.getInt("Swords.WeaponSkills.Under_Fire.DurabilityLoss");


    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        String uuid = p.getUniqueId().toString();
        PlayerData pd = API.Players.get(uuid);
        int level = pd.getData().getInt("Level");

        if (p.getInventory().getItemInMainHand().hasItemMeta()) {

            String weaponSkill = nameSpaceKey.getItemWeaponSkillFromContainer(p.getInventory().getItemInMainHand());

                if (level >= underFireLevelNeeded && !(underFireUsers.contains(p.getUniqueId().toString())) && underFireEnabled && weaponSkill.equalsIgnoreCase("Under Fire") && (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {


                    //default walk speed is .2
                    p.getPlayer().setWalkSpeed((float)0.2 * (underFireSpeedIncrease/100));
                    underFireUsers.add(p.getUniqueId().toString());
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Under Fire [Activated]").color(ChatColor.GREEN).create());

                    final ItemStack modifiedItem = p.getInventory().getItemInMainHand();
                    final Damageable im = (Damageable) modifiedItem.getItemMeta();
                    final int damage = underFireDurabilityLoss; // you should handle this as a NumberFormatException can be thrown.
                    final int currentDamage = im.getDamage();
                    im.setDamage(currentDamage + damage);
                    modifiedItem.setItemMeta((ItemMeta)im);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(API.plugin, new Runnable() {
                        public void run() {
                            p.getPlayer().setWalkSpeed((float)0.2);
                            underFireUsers.remove(p.getUniqueId().toString());
                            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Under Fire " + ChatColor.RED + "[Deactivated]").color(ChatColor.GREEN).create());
                        }
                    }, underFireDuration *20L);

                }


        }
        return;
    }


}

