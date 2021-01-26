package com.celcius.command;

import com.celcius.PowerfulPets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    PowerfulPets plugin;

    public Commands(PowerfulPets plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {

            if (cmd.getName().equalsIgnoreCase("powerfulpets") || cmd.getName().equalsIgnoreCase("pp")) {
                if (args[0].equals("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage(plugin.getLang().getString("messages.Config_Reload"));
                }
            }
        }catch (Exception ex) {

        }
        return false;
        }
    }
