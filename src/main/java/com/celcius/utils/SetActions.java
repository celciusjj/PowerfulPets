package com.celcius.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SetActions {
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

    public void setCommand(Player player, String command){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String removeAction = command.replace("command ", "");
        String newCommand = removeAction.replace("%player%", player.getName());;
        Bukkit.dispatchCommand(console, newCommand);
    }

    public void removeCommand(Player player, String command){
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String removeAction = command.replace("command ", "");
        String newCommand = removeAction.replace("%player%", player.getName());;
        Bukkit.dispatchCommand(console, newCommand);
    }
}
