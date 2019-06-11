package com.paully104.reitzmmo.Command_Handlers;

import com.paully104.reitzmmo.ConfigFiles.API;
import com.paully104.reitzmmo.Menu.Menu;
import com.paully104.reitzmmo.Party_System.Party_API;
import com.paully104.reitzmmo.PlayerData.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by Paul on 7/24/2016.
 */
public class ReitzRPGMain implements CommandExecutor {

    private static final  String REITZ = "Reitz";
    private static final  String REITZMMO = "ReitzMMO";
    private static final  String LEVEL = "Level";
    private static final  String ATTACK = "Attack";
    private static final  String HEALTH = "Health";
    private static final  String WORLDBASECOMBATEXP = "Scaling.World.WorldBaseCombatEXP.Base";
    private static final  String WORLDBASECOMBATEXP_MULTIPLIER = "Scaling.World.WorldBaseCombatEXP.Multiplier";
    private static final  String PLAYERCOMBATEXP = "Combat-EXP";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player pl = Bukkit.getPlayer(sender.getName());
        int worldEnabled = API.worldConfig.getInt(Objects.requireNonNull(Objects.requireNonNull(pl).getLocation().getWorld()).getName());

        if( (cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && worldEnabled != -1)
        {

            if ((cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && args.length == 0) {
                Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).openInventory(Menu.GUI_MENU);
                //The Idea is to make these obsolute with a nifty menu :)
                //sender.sendMessage(ChatColor.GOLD + "~ReitzRPGMMO main  menu listing commands~");
                //sender.sendMessage(ChatColor.GOLD + "1. /Reitz Stats");
                //sender.sendMessage(ChatColor.GOLD + "2. /Reitz Fix");
                //sender.sendMessage(ChatColor.GOLD + "3. /Rparty");
                return true;
            }
            else if ((cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && args.length == 1 && args[0].equalsIgnoreCase("Stats"))
            {

                sender.sendMessage(ChatColor.GOLD + "|||Current Stats|||");
                sender.sendMessage(ChatColor.GOLD + "     Level: " + API.getPlayerDataFromAPI(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), LEVEL));
                sender.sendMessage(ChatColor.RED + "     Attack: " + API.getPlayerDataFromAPI(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), ATTACK));
                sender.sendMessage(ChatColor.YELLOW + "     Health: " + API.getPlayerDataFromAPI(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), HEALTH));
                sender.sendMessage(ChatColor.DARK_GREEN + "     CombatEXP: " + API.getPlayerDataFromAPI(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), WORLDBASECOMBATEXP));
                int combatexpneeded = API.getPlayerDataFromAPI(Objects.requireNonNull(Bukkit.getPlayer(sender.getName())), LEVEL) * (API.playerConfig.getInt(WORLDBASECOMBATEXP) * API.playerConfig.getInt(WORLDBASECOMBATEXP_MULTIPLIER));
                sender.sendMessage(ChatColor.DARK_GREEN + "     CombatEXP Needed: " + combatexpneeded);
                return true;
            }
            else if ((cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && args.length == 1 && args[0].equalsIgnoreCase("FixHealth"))
            {
                Player p = Bukkit.getPlayer(sender.getName());
                UUID uuid = Objects.requireNonNull(p).getUniqueId();
                int combatexp = API.Players.get(uuid).getData().getInt(PLAYERCOMBATEXP);
                int level = API.Players.get(uuid).getData().getInt(LEVEL);
                int combatexpneeded = level * (API.playerConfig.getInt(WORLDBASECOMBATEXP) * API.playerConfig.getInt(WORLDBASECOMBATEXP_MULTIPLIER));
                //level up occurs
                if (combatexp >= combatexpneeded)
                {
                    //fixing level also
                    level = level + 1;
                    combatexp = combatexp - combatexpneeded;
                    p.sendMessage("~Fixing stats due to plugin changes...");
                    p.sendMessage("You leveled up to: " + level);
                    API.Players.get(uuid).getData().set(LEVEL, level);
                    API.Players.get(uuid).getData().set(ATTACK, (level * API.playerConfig.getInt("AttackScale")));
                    API.Players.get(uuid).getData().set(HEALTH, (18 + (level * API.playerConfig.getInt("HealthScale"))));
                    p.sendMessage("Attack is now: " + API.getPlayerDataFromAPI(p, ATTACK));
                    p.sendMessage("Health is now: " + API.getPlayerDataFromAPI(p, HEALTH));
                    Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(API.getPlayerDataFromAPI(p, HEALTH));
                    API.Players.get(uuid).getData().set(PLAYERCOMBATEXP, combatexp);
                } else
                    {
                        //just fixing stats
                        p.sendMessage("~Fixing stats due to plugin changes...");
                        API.Players.get(uuid).getData().set(ATTACK, (level * API.playerConfig.getInt("AttackScale")));
                        API.Players.get(uuid).getData().set(HEALTH, (18 + (level * API.playerConfig.getInt("HealthScale"))));
                        p.sendMessage("Attack is now: " + API.getPlayerDataFromAPI(p, ATTACK));
                        p.sendMessage("Health is now: " + API.getPlayerDataFromAPI(p, HEALTH));
                        Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(API.getPlayerDataFromAPI(p, HEALTH));

                    }
            }
            else if ((cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && args.length == 1 && args[0].equalsIgnoreCase("FixEXP"))
            {
                World world = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getWorld();
                for (Entity e : world.getEntities()) {
                    if (e instanceof ArmorStand) {
                        e.remove();

                    }

                }
            }
            else if ((cmd.getName().equalsIgnoreCase(REITZ) || cmd.getName().equalsIgnoreCase("RRM") || cmd.getName().equalsIgnoreCase(REITZMMO)) && args.length == 1 && args[0].equalsIgnoreCase("Reload"))
            {
                //reload the configs
                Bukkit.broadcastMessage("[ReitzMMO] reloading... saving all users data");
                for (Player p : Bukkit.getServer().getOnlinePlayers())
                {
                    //Get player information;
                    String name = p.getName();
                    String uuid = p.getUniqueId().toString();
                    PlayerData pd = new PlayerData(uuid);
                    System.out.println(p.getName() + " has exited the game!");

                    //get stats from API
                    Integer level = API.Players.get(uuid).getData().getInt(LEVEL);
                    Integer attack = API.Players.get(uuid).getData().getInt(ATTACK);
                    Integer health = API.Players.get(uuid).getData().getInt(HEALTH);
                    Integer combatexp = API.Players.get(uuid).getData().getInt(PLAYERCOMBATEXP);

                    //Save stats
                    pd.getData().set(LEVEL, level);
                    pd.getData().set(ATTACK, attack);
                    pd.getData().set(HEALTH, health);
                    pd.getData().set(PLAYERCOMBATEXP, combatexp);
                    pd.getData().set("DisplayName",p.getDisplayName());
                    pd.save();


                    //They disconnect make sure their party status is removed!
                    if (Party_API.Party_Leaders.containsKey(name))
                    {
                        p.performCommand("Rparty disband");

                    }
                    else if (Party_API.inParty.containsKey(name))
                    {
                        p.performCommand("Rparty leave");
                    }

                }
                Bukkit.broadcastMessage("[ReitzMMO] All online player's saved");


                //if they reloaded the server people might be on, lets set their stats
                Bukkit.broadcastMessage("[ReitzMMO] Loading players stats from configs");
                for(Player p : Bukkit.getServer().getOnlinePlayers())
                {

                    String uuid = p.getUniqueId().toString();
                    PlayerData pd = new PlayerData(uuid);
                    pd.getData().set("UUID", uuid);

                    int Level = pd.getData().getInt(LEVEL);
                    int Attack = pd.getData().getInt(ATTACK);
                    double Health = pd.getData().getDouble(HEALTH);
                    int CombatEXP = pd.getData().getInt(PLAYERCOMBATEXP);

                    if (Level == 0) {
                        pd.getData().set(LEVEL, 1);

                    }
                    if (Attack == 0) {
                        pd.getData().set(ATTACK, 1);

                    }
                    if (Health == 0.0) {
                        pd.getData().set(HEALTH, 20);
                        Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);


                    } else {

                        Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(Health);
                    }
                    if (CombatEXP == 0) {
                        pd.getData().set(PLAYERCOMBATEXP, 0);

                    }
                    pd.getData().set("DisplayName",p.getDisplayName());
                    pd.save();
                    API.Players.put(p.getUniqueId().toString(), pd); //this loads the player data into the API

                    //Lets give the book
                    //ReitzMMO_Book.setLoginBook(p);

                }

            }
        }


        return false;
    }

}
