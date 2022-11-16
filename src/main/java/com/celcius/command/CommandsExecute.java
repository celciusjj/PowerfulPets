package com.celcius.command;

import com.celcius.PowerfulPets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsExecute implements CommandExecutor {

    private final PowerfulPets plugin = PowerfulPets.getPlugin(PowerfulPets.class);

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
            sender.sendMessage(String.valueOf(ex));
        }
        return false;
        }
    }
