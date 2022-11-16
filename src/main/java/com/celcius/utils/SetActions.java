package com.celcius.utils;
import com.celcius.PowerfulPets;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttributes;
import net.Indyuce.mmocore.api.player.stats.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SetActions {

    PowerfulPets plugin;
    MMOCore mmoCore;


    public void chooseTheOptionforSpawn(String action, Player player) {
        String[] splitAction = action.split(" ");
        switch(splitAction[0])
        {
            case "potionEffect":
                setPotionEffect(player, splitAction[1], Integer.parseInt(splitAction[2]));
                break;
            case "command":
                setCommand(player, action);
                break;
            case "attribute":
                attribute(player, splitAction[1], Integer.parseInt(splitAction[2]));
                break;

            default :
                // Declaraciones
        }
    }

    public void chooseTheOptionforRemove(String action, Player player) {
        String[] splitAction = action.split(" ");
        switch(splitAction[0])
        {
            case "potionEffect":
                removePotionEffect(player, splitAction[1]);
                break;
            case "command":
                removeCommand(player, action);
                break;
            case "attribute":
                attribute(player, splitAction[1], Integer.parseInt(splitAction[2]));
                break;
            default :
                // Declaraciones
        }
    }

    public void setPotionEffect(Player player, String effect, int amplifier) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), Integer.MAX_VALUE, amplifier));
    }

    public void removePotionEffect(Player player, String effect){
        player.removePotionEffect(PotionEffectType.getByName(effect));
    }

    public void setCommand(OfflinePlayer player, String command){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String removeAction = command.replace("command ", "");
        String newCommand = removeAction.replace("%player%", player.getName());;
        Bukkit.dispatchCommand(console, newCommand);
    }

    public void removeCommand(OfflinePlayer player, String command){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String removeAction = command.replace("command ", "");
        String newCommand = removeAction.replace("%player%", player.getName());;
        Bukkit.dispatchCommand(console, newCommand);
    }

    public void attribute(OfflinePlayer player, String attribute, int points){
        PlayerAttribute currentAttribute = MMOCore.plugin.attributeManager.get(attribute);
        PlayerAttributes.AttributeInstance instance = PlayerData.get(player).getAttributes().getInstance(currentAttribute);
        PlayerData pData = PlayerData.get(player);
        pData.setAttribute(attribute, instance.getTotal()+points);
        //PlayerStats stats = pData.getStats();
        //stats.

    }
}
