package com.paully104.reitzmmo.ItemData;

import com.paully104.reitzmmo.Command_Handlers.ReitzRPGMain;
import com.paully104.reitzmmo.ConfigFiles.API;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class nameSpaceKey {
    public static NamespacedKey itemDamageKey = new NamespacedKey(API.plugin, "ReitzMMOItemDamageKey");
    public static NamespacedKey itemDefenseKey = new NamespacedKey(API.plugin, "ReitzMMOItemDefenseKey");
    public static NamespacedKey itemWeaponSkillKey = new NamespacedKey(API.plugin,"ReitzMMOWeaponSkillKey");
    public static NamespacedKey itemSpeedKey = new NamespacedKey(API.plugin,"ReitzMMOItemSpeedlKey");

    public static NamespacedKey getItemDamageKey() { return itemDamageKey; }
    public static NamespacedKey getItemDefenseKey() {
        return itemDefenseKey;
    }
    public static NamespacedKey getItemWeaponSkillKey() { return itemWeaponSkillKey;}
    public static NamespacedKey getItemSpeedKey() { return itemSpeedKey;}

    public static void setItemDamageContainer(ItemStack item, int number)
    {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(itemDamageKey, PersistentDataType.INTEGER,number);
        meta.setLore(Arrays.asList(ChatColor.RED+"Bonus Damage: " +number));
        item.setItemMeta(meta);

    }

    public static void setItemDefenseContainer(ItemStack item, int number)
    {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(itemDefenseKey, PersistentDataType.INTEGER,number);
        meta.setLore(Arrays.asList(ChatColor.BLUE+"Bonus Defense: " +number));
        item.setItemMeta(meta);

    }

    public static void setSpeedContainer(ItemStack item, int number)
    {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(itemSpeedKey, PersistentDataType.INTEGER,number);
        meta.setLore(Arrays.asList(ChatColor.GREEN+"Bonus Speed: " +number));
        item.setItemMeta(meta);

    }

    public static void setItemWeaponSkillContainer(ItemStack item,String skill)
    {
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(itemWeaponSkillKey, PersistentDataType.STRING,skill);
        ArrayList lore = new ArrayList(0);
        if(!(null == meta.getLore()))
        {
            for (Object eachLoreLine : meta.getLore())
            {
                String eachString = eachLoreLine.toString();
                if (eachString.contains("Weapon")) {
                    //do nothing
                }
                else
                {
                    //add the stat to the weapon back
                    lore.add(eachLoreLine);
                }


            }

        }
            lore.add(ChatColor.GOLD + "Weapon Skill: " + skill);
            System.out.println(lore.toString());
            meta.setLore(lore);
            item.setItemMeta(meta);


    }



    public static int getItemDamageFromContainer(ItemStack item)
    {
        if (item.hasItemMeta())
        {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(itemDamageKey, PersistentDataType.INTEGER)) {

                int foundvalue = container.get(itemDamageKey, PersistentDataType.INTEGER);
                return foundvalue;
            } else {
                return 0;
            }
        }
        else
        {
            return  0;
        }
    }

    public static int getItemDefenseFromContainer(ItemStack item) {
        if(!(null == item))
        {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(itemDefenseKey, PersistentDataType.INTEGER)) {

                    int foundvalue = container.get(itemDefenseKey, PersistentDataType.INTEGER);
                    return foundvalue;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static int getItemSpeedFromContainer(ItemStack item) {
        if(!(null == item))
        {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(itemSpeedKey, PersistentDataType.INTEGER)) {

                    int foundvalue = container.get(itemSpeedKey, PersistentDataType.INTEGER);
                    return foundvalue;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static String getItemWeaponSkillFromContainer(ItemStack item) {
        if(!(null == item))
        {
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(itemWeaponSkillKey, PersistentDataType.STRING)) {

                    String foundvalue = container.get(itemWeaponSkillKey, PersistentDataType.STRING);
                    return foundvalue;
                } else {
                    return "None";
                }
            } else {
                return "None";
            }
        }
        return "None";
    }
}
