package com.celcius.listeners;

import com.celcius.PowerfulPets;
import com.celcius.handlers.Records;
import com.kirelcodes.miniaturepets.MiniaturePets;
import com.kirelcodes.miniaturepets.pets.Pet;
import com.kirelcodes.miniaturepets.pets.PetManager;
import fr.nocsy.mcpets.data.PetDespawnReason;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListener implements Listener {

    List actions;
    Records records = new Records();
    private final PowerfulPets plugin = PowerfulPets.getPlugin(PowerfulPets.class);

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if(plugin.isFoundMCPets()){
            if (plugin.getSpawnMCPet().containsKey(e.getPlayer())) {
                plugin.getSpawnMCPet().remove(e.getPlayer(), plugin.getSpawnMinaturePet().get(e.getPlayer()).getType());
                actions = plugin.getConfig().getStringList("pets." + plugin.getSpawnMCPet().get(e.getPlayer()).getId() + ".actions.onremove");
                //records.removeRecord(e.getPlayer(), plugin.getSpawnMCPet().get(e.getPlayer()), actions);
                for (Object action : actions) {
                    plugin.getSetActions().chooseTheOptionforRemove(action.toString(), e.getPlayer());
                }
            }
        }
        if(plugin.isFoundMiniaturePets()) {
            if (plugin.getSpawnMinaturePet().containsKey(e.getPlayer())) {
                plugin.getSpawnMinaturePet().remove(e.getPlayer(), plugin.getSpawnMinaturePet().get(e.getPlayer()).getType());
                actions = plugin.getConfig().getStringList("pets." + plugin.getSpawnMinaturePet().get(e.getPlayer()).getType() + ".actions.onremove");
                records.removeRecord(e.getPlayer(), plugin.getSpawnMinaturePet().get(e.getPlayer()), actions);
                for (Object action : actions) {
                    plugin.getSetActions().chooseTheOptionforRemove(action.toString(), e.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if(plugin.isFoundMCPets()){
            if (plugin.getSpawnMCPet().containsKey(e.getEntity())) {
                actions = plugin.getConfig().getStringList("pets." + plugin.getSpawnMCPet().get(e.getEntity()).getId() + ".actions.onremove");
                //records.removeRecord(e.getEntity(), plugin.getSpawnMinaturePet().get(e.getEntity()), actions);
                for (Object action : actions) {
                    plugin.getSetActions().chooseTheOptionforRemove(action.toString(), e.getEntity());
                }
                plugin.getSpawnMCPet().get(e.getEntity()).despawn(PetDespawnReason.REVOKE);
                plugin.getSpawnMCPet().remove(e.getEntity());
            }
        }

        if (plugin.isFoundMiniaturePets()) {
            if (plugin.getSpawnMinaturePet().containsKey(e.getEntity())) {
                actions = plugin.getConfig().getStringList("pets." + plugin.getSpawnMinaturePet().get(e.getEntity()).getType() + ".actions.onremove");
                records.removeRecord(e.getEntity(), plugin.getSpawnMinaturePet().get(e.getEntity()), actions);
                for (Object action : actions) {
                    plugin.getSetActions().chooseTheOptionforRemove(action.toString(), e.getEntity());
                }
                plugin.getSpawnMinaturePet().get(e.getEntity()).remove();
                plugin.getSpawnMinaturePet().remove(e.getEntity());
            }
        }
    }
}
