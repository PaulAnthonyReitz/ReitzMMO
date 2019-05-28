package com.paully104.reitzmmo.MonsterCombatRelated;

import com.paully104.reitzmmo.ConfigFiles.API;
import com.paully104.reitzmmo.Enum.Armor_Defense;
import com.paully104.reitzmmo.Enum.Weapon_Damage;
import com.paully104.reitzmmo.Party_System.Party_API;
import com.paully104.reitzmmo.Party_System.Scoreboard_Party;
import com.paully104.reitzmmo.PlayerData.PlayerData;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;

/**
 * Created by Paul on 3/22/2016.
 */


/*
[NOTES]
If the attribute modifier is equal to the default value on the item it won't detect it.
(ie: armor and armor toughness on armor)



 */
public class MonsterLevelsDamage implements Listener {

    private final int zombieBaseAttack = API.monsterConfig.getInt("zombie.base_attack");
    private final int wolfBaseAttack  = API.monsterConfig.getInt("Wolf.base_attack");
    private final int villagerBaseAttack  = API.monsterConfig.getInt("Villager.base_attack");
    private final int squidBaseAttack  = API.monsterConfig.getInt("Squid.base_attack");
    private final int spiderBaseAttack = API.monsterConfig.getInt("Spider.base_attack");
    private final int snowmanBaseAttack  = API.monsterConfig.getInt("Snowman.base_attack");
    private final int slimeBaseAttack = API.monsterConfig.getInt("Slime.base_attack");
    private final int skeletonBaseAttack  = API.monsterConfig.getInt("Skeleton.base_attack");
    private final int silverfishBaseAttack  = API.monsterConfig.getInt("silverfish_base_attack");
    private final int sheepBaseAttack = API.monsterConfig.getInt("sheep.base_attack");
    private final int rabbitBaseAttack  = API.monsterConfig.getInt("rabbit.base_attack");
    private final int pigzombieBaseAttack  = API.monsterConfig.getInt("Pigzombie.base_attack");
    private final int pigBaseAttack  = API.monsterConfig.getInt("Pig.base_attack");
    private final int mushroomcowBaseAttack  = API.monsterConfig.getInt("Mushroomcow.base_attack");
    private final int magmacubeBaseAttack  = API.monsterConfig.getInt("Magmacube.base_attack");
    private final int guardianBaseAttack  = API.monsterConfig.getInt("Guardian.base_attack");
    private final int giantBaseAttack  = API.monsterConfig.getInt("Giant.base_attack");
    private final int ghastBaseAttack = API.monsterConfig.getInt("Ghast.base_attack");
    private final int endermiteBaseAttack  = API.monsterConfig.getInt("Endermite.base_attack");
    private final int endermanBaseAttack = API.monsterConfig.getInt("Enderman.base_attack");
    private final int enderdragonBaseAttack = API.monsterConfig.getInt("Enderdragon.base_attack");
    private final int creeperBaseAttack  = API.monsterConfig.getInt("Creeper.base_attack");
    private final int cowBaseAttack  = API.monsterConfig.getInt("Cow.base_attack");
    private final int chickenBaseAttack  = API.monsterConfig.getInt("Chicken.base_attack");
    private final int cavespiderBaseAttack = API.monsterConfig.getInt("Cavespider.base_attack");
    private final int blazeBaseAttack  = API.monsterConfig.getInt("Blaze.base_attack");
    private final int witchBaseAttack  = API.monsterConfig.getInt("Witch.base_attack");
    private final int witherSkeletonBaseAttack = API.monsterConfig.getInt("Witherskeleton.base_attack");
    private final int shulkerBaseAttack = API.monsterConfig.getInt("Shulker.base_attack");
    private final int pillagerBaseAttack = API.monsterConfig.getInt("Pillager.base_attack");
    private final int illusionerBaseAttack = API.monsterConfig.getInt("Illusioner.base_attack");
    private final int evokerBaseAttack = API.monsterConfig.getInt("Evoker.base_attack");
    private final int ravagerBaseAttack = API.monsterConfig.getInt("Ravager.base_attack");
    private final int batBaseAttack = API.monsterConfig.getInt("Bat.base_attack");
    private final int drownedBaseAttack = API.monsterConfig.getInt("Drowned.base_attack");

    //debug section
    private final boolean debugEnabled = API.debugConfig.getBoolean("MonsterAttackingPlayer");



    @EventHandler
    public void monsterAttackingPlayer(EntityDamageByEntityEvent e) {
        //The world is set to -1 means don't apply to mobs
        int worldEnabled = API.worldConfig.getInt(e.getEntity().getLocation().getWorld().getName());
        if(worldEnabled != -1) {
            Entity defender = e.getEntity();//player
            Entity attacker = e.getDamager();//monster
            int player_defense;
            int monster_attack;
            int damage_done;
            Arrow arrow;
            String monster_level_from_name;

            EntityType attackerType = attacker.getType();
            EntityType defenderType = defender.getType();
            if (defenderType == EntityType.PLAYER) {
                String defenderUUID = defender.getUniqueId().toString();
                PlayerData pd = API.Players.get(defenderUUID);
                Player defendingPlayer = (Player) defender;
                int helmet;
                int helmetEnchant;
                int chestplate;
                int chestplateEnchant;
                int leggings;
                int leggignsEnchant;
                int boots;
                int bootsEnchant;
                int armorTotal;
                switch (attackerType) {
                    case PLAYER:
                        //PVP stuff
                        player_defense = pd.getData().getInt("Level");
                        String attackerUUID = attacker.getUniqueId().toString();
                        PlayerData personAttacking = API.Players.get(attackerUUID);
                        int personAttacking_Attack = personAttacking.getData().getInt("Attack");
                        int weaponDamage = 0;

                        //Lets check for their weapon
                        try {
                            //if in list
                            Player human = (Player) attacker;
                            if (!(human.getInventory().getItemInMainHand().toString().contains("AIR"))) {

                                try {
                                    weaponDamage = (Weapon_Damage.Weapon_Damages.valueOf(human.getInventory().getItemInMainHand().getType().toString().toUpperCase()).getValue());

                                } catch (IllegalArgumentException error) {
                                    weaponDamage = 0;
                                    if (debugEnabled == true) {
                                        System.out.println("weapon damaged set to 0");

                                    }

                                } finally {


                                    if (debugEnabled) {
                                        System.out.println("Finally statement happening");

                                    }
                                    //if the weapon has special stats
                                    if (human.getInventory().getItemInMainHand().getItemMeta().hasAttributeModifiers()) {
                                        if (debugEnabled) {
                                            System.out.println("Item has bonus stats for main attack");
                                        }
                                        Collection<AttributeModifier> weaponStats = human.getInventory().getItemInMainHand().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE);
                                        int weaponBonus = (int) weaponStats.iterator().next().getAmount();
                                        //should be like 0+1
                                        weaponDamage = weaponDamage + weaponBonus;


                                        //[14:07:39 INFO]: AttributeModifier{uuid=00000000-0000-0b38-0000-0000000da6be, name=generic.attackDamage, operation=ADD_NUMBER, amount=20.0, slot=}
                                        //int weaponBonus = human.getInventory().getItemInMainHand().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE).;
                                        //weaponDamage = weaponDamage + weaponBonus;
                                    }


                                }

                                //after the finally lets set the damage done once
                                //should be like 2 damage for lv 1 + 1 weapon damage - 1 for defense so 2 damage


                            } else {
                                //empty handed

                                if (debugEnabled == true) {
                                    System.out.print("empty hands");
                                }
                            }
                        } catch (IllegalArgumentException error) {
                            //trying to get main hand fails for whatever reason
                            if (debugEnabled) {
                                System.out.println(error);
                            }

                        }

                        damage_done = (personAttacking_Attack + weaponDamage) - player_defense;//if not in list
                        if (damage_done < 1) {
                            damage_done = 1;
                            if (debugEnabled) {
                                System.out.println("Cant do less then 1 damage!");
                            }


                        }
                        e.setDamage((personAttacking_Attack + weaponDamage) - player_defense);

                        if (debugEnabled) {
                            System.out.println("Personattacking_Attack: " + personAttacking_Attack);
                            System.out.println("WeaponDamage: " + weaponDamage);
                            System.out.println("[PVP]: " + attacker.getName() + " " + (personAttacking_Attack + weaponDamage) +
                                    " -> " + defender.getName() + " " + player_defense);
                        }
                        break;


                    case ZOMBIE:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * zombieBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }

                        armorTotal = helmet + chestplate + leggings + boots;


                        damage_done = monster_attack - (player_defense + armorTotal);
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);

                        }
                        break;
                    case WOLF:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * wolfBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SQUID:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * squidBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SNOWMAN:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * snowmanBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SLIME:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * slimeBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SILVERFISH:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * silverfishBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SHEEP:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * sheepBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case RABBIT:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * rabbitBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case PIG_ZOMBIE:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * pigzombieBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case PIG:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * pigBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case MUSHROOM_COW:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * mushroomcowBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case MAGMA_CUBE:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * magmacubeBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case GUARDIAN:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * guardianBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case GIANT:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * giantBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case GHAST:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * ghastBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case ENDERMITE:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * endermiteBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case ENDERMAN:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * endermanBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case ENDER_DRAGON:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * enderdragonBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case CREEPER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * creeperBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case COW:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * cowBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case CHICKEN:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * chickenBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case CAVE_SPIDER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * cavespiderBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case SPIDER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * spiderBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case WITHER_SKELETON:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * witherSkeletonBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case BLAZE:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * blazeBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case PILLAGER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * pillagerBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case ILLUSIONER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * illusionerBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case EVOKER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * evokerBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                            int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                            helmet = helmet + helmetBonus;
                        } catch (NullPointerException error) {
                            helmet = 0;
                        }
                        try {
                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());
                            Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                            int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                            chestplate = chestplate + chestplateBonus;
                        } catch (NullPointerException error) {
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                            int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                            leggings = leggings + leggingsBonus;
                        } catch (NullPointerException error) {
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                            int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                            boots = boots + bootsBonus;
                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case BAT:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * batBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case RAVAGER:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * ravagerBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case DROWNED:
                        player_defense = pd.getData().getInt("Level");
                        monster_level_from_name = attacker.getCustomName().replaceAll("\\D+", "");
                        monster_attack = Integer.parseInt(monster_level_from_name) * drownedBaseAttack;
                        try {

                            helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                            try {
                                Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                helmet = helmet + helmetBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }
                        } catch (NullPointerException error) {
                            //no helmet armor so 0 it is
                            helmet = 0;
                        }
                        try {

                            chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                            try {

                                Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                chestplate = chestplate + chestplateBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything
                            }

                        } catch (NullPointerException error) {
                            //no chestplate armor so 0
                            chestplate = 0;
                        }
                        try {
                            leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                leggings = leggings + leggingsBonus;

                            } catch (NullPointerException er) {
                                //theres no itemmeta so no need to do anything

                            }

                        } catch (NullPointerException error) {
                            //no leggings so 0
                            leggings = 0;
                        }
                        try {
                            boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                            try {

                                Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                boots = boots + bootsBonus;

                            } catch (NullPointerException er) {
                                //no enchant so no need to do anything
                            }


                        } catch (NullPointerException error) {
                            boots = 0;
                        }
                        armorTotal = helmet + chestplate + leggings + boots;
                        damage_done = monster_attack - (player_defense + armorTotal);
                        ;
                        if (damage_done < 1) {
                            damage_done = 1;
                        }
                        e.setDamage(damage_done);
                        if (debugEnabled) {
                            System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                            System.out.println("Armor Total: " + armorTotal);
                        }
                        break;
                    case ARROW:
                        arrow = (Arrow) attacker;
                        if (arrow.getShooter() instanceof Skeleton) {
                            player_defense = pd.getData().getInt("Level");
                            monster_level_from_name = ((Skeleton) arrow.getShooter()).getCustomName().replaceAll("\\D+", "");
                            monster_attack = Integer.parseInt(monster_level_from_name) * skeletonBaseAttack;
                            try {

                                helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                                try {
                                    Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                    helmet = helmet + helmetBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }
                            } catch (NullPointerException error) {
                                //no helmet armor so 0 it is
                                helmet = 0;
                            }
                            try {

                                chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                                try {

                                    Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                    chestplate = chestplate + chestplateBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }

                            } catch (NullPointerException error) {
                                //no chestplate armor so 0
                                chestplate = 0;
                            }
                            try {
                                leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                    leggings = leggings + leggingsBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything

                                }

                            } catch (NullPointerException error) {
                                //no leggings so 0
                                leggings = 0;
                            }
                            try {
                                boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                    boots = boots + bootsBonus;

                                } catch (NullPointerException er) {
                                    //no enchant so no need to do anything
                                }


                            } catch (NullPointerException error) {
                                boots = 0;
                            }
                            armorTotal = helmet + chestplate + leggings + boots;
                            damage_done = monster_attack - (player_defense + armorTotal);
                            ;
                            if (damage_done < 1) {
                                damage_done = 1;
                            }
                            e.setDamage(damage_done);
                            if (debugEnabled) {
                                System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                                System.out.println("Armor Total: " + armorTotal);
                            }
                        } else {

                            //Not from skeleton archer
                        }
                        break;
                    case SPLASH_POTION:
                        SplashPotion potion = (SplashPotion) attacker;
                        if (potion.getShooter() instanceof Witch) {
                            player_defense = pd.getData().getInt("Level");
                            monster_level_from_name = ((Witch) potion.getShooter()).getCustomName().replaceAll("\\D+", "");
                            monster_attack = Integer.parseInt(monster_level_from_name) * witchBaseAttack;
                            try {

                                helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                                try {
                                    Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                    helmet = helmet + helmetBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }
                            } catch (NullPointerException error) {
                                //no helmet armor so 0 it is
                                helmet = 0;
                            }
                            try {

                                chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                                try {

                                    Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                    chestplate = chestplate + chestplateBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }

                            } catch (NullPointerException error) {
                                //no chestplate armor so 0
                                chestplate = 0;
                            }
                            try {
                                leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                    leggings = leggings + leggingsBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything

                                }

                            } catch (NullPointerException error) {
                                //no leggings so 0
                                leggings = 0;
                            }
                            try {
                                boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                    boots = boots + bootsBonus;

                                } catch (NullPointerException er) {
                                    //no enchant so no need to do anything
                                }


                            } catch (NullPointerException error) {
                                boots = 0;
                            }
                            armorTotal = helmet + chestplate + leggings + boots;
                            damage_done = monster_attack - (player_defense + armorTotal);
                            ;
                            if (damage_done < 1) {
                                damage_done = 1;
                            }
                            e.setDamage(damage_done);
                            if (debugEnabled) {
                                System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                                System.out.println("Armor Total: " + armorTotal);
                            }
                        }
                        break;
                    case SHULKER_BULLET:
                        arrow = (Arrow) attacker;
                        if (arrow.getShooter() instanceof Shulker) {
                            player_defense = pd.getData().getInt("Level");
                            monster_level_from_name = ((Witch) arrow.getShooter()).getCustomName().replaceAll("\\D+", "");
                            monster_attack = Integer.parseInt(monster_level_from_name) * shulkerBaseAttack;
                            try {

                                helmet = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getHelmet().getType().toString().toUpperCase()).getValue());
                                try {
                                    Collection<AttributeModifier> helmetEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int helmetBonus = (int) helmetEnchantAttribute.iterator().next().getAmount();
                                    helmet = helmet + helmetBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }
                            } catch (NullPointerException error) {
                                //no helmet armor so 0 it is
                                helmet = 0;
                            }
                            try {

                                chestplate = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getChestplate().getType().toString().toUpperCase()).getValue());

                                try {

                                    Collection<AttributeModifier> chestplateEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int chestplateBonus = (int) chestplateEnchantAttribute.iterator().next().getAmount();
                                    chestplate = chestplate + chestplateBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything
                                }

                            } catch (NullPointerException error) {
                                //no chestplate armor so 0
                                chestplate = 0;
                            }
                            try {
                                leggings = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getLeggings().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> leggingsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int leggingsBonus = (int) leggingsEnchantAttribute.iterator().next().getAmount();
                                    leggings = leggings + leggingsBonus;

                                } catch (NullPointerException er) {
                                    //theres no itemmeta so no need to do anything

                                }

                            } catch (NullPointerException error) {
                                //no leggings so 0
                                leggings = 0;
                            }
                            try {
                                boots = (Armor_Defense.Armor_Defenses.valueOf(defendingPlayer.getInventory().getBoots().getType().toString().toUpperCase()).getValue());
                                try {

                                    Collection<AttributeModifier> bootsEnchantAttribute = defendingPlayer.getInventory().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
                                    int bootsBonus = (int) bootsEnchantAttribute.iterator().next().getAmount();
                                    boots = boots + bootsBonus;

                                } catch (NullPointerException er) {
                                    //no enchant so no need to do anything
                                }


                            } catch (NullPointerException error) {
                                boots = 0;
                            }
                            armorTotal = helmet + chestplate + leggings + boots;
                            damage_done = monster_attack - (player_defense + armorTotal);
                            ;
                            if (damage_done < 1) {
                                damage_done = 1;
                            }
                            e.setDamage(damage_done);
                            if (debugEnabled) {
                                System.out.println("[MAP]: " + attacker.getType() + " " + attacker.getCustomName() + " -> " + defender.getName() + " " + player_defense);
                                System.out.println("Armor Total: " + armorTotal);
                            }
                        }
                }
                //update scoreboards on damage?
                if(e.getEntity() instanceof Player)
                {
                    Player p = (Player)e.getEntity();
                    Scoreboard_Party scoreboard = new Scoreboard_Party();
                    if (Party_API.Party_Leaders.containsKey((p.getName())))
                    {
                        scoreboard.Scoreboard_PartySetup(p);
                    }
                    else
                    {

                    }
                    if (Party_API.inParty.containsKey(p.getName())) {
                        scoreboard.Scoreboard_PartySetup(p);
                    }
                    else
                    {

                    }

                }

            }


        }

    }

}


