package com.celcius.listeners;

import com.celcius.PowerfulPets;
import com.celcius.handlers.Records;
import com.celcius.utils.SetActions;
import com.kirelcodes.miniaturepets.api.events.pets.PetFinishedSpawnEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.pets.Pet;
import fr.nocsy.mcpets.MCPets;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class MCPetsHandler implements Listener {
    private final PowerfulPets plugin = PowerfulPets.getPlugin(PowerfulPets.class);
    List actions;
    SetActions setActions = new SetActions();
    Records records = new Records();

    @EventHandler
    public void ChangeWorldEvent(PlayerChangedWorldEvent event) {
        Player player =  event.getPlayer();
        World world = player.getWorld();
        List<String> disabledWorlds = plugin.getConfig().getStringList("disabled_worlds");
        for(String worldDisabled: disabledWorlds){
            if(world.getName().equals(worldDisabled)){
                if(plugin.getSpawnMCPet().containsKey(player)) {
                    fr.nocsy.mcpets.data.Pet petName = plugin.getSpawnMCPet().get(player);
                    actions = plugin.getConfig().getStringList("pets."+petName.getId()+".actions.onremove");
                    //records.removeRecord(player, petName, actions);
                    for (Object action : actions) {
                        setActions.chooseTheOptionforRemove(action.toString(), player);
                    }
                    plugin.getSpawnMCPet().remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onSpawnPet(fr.nocsy.mcpets.events.PetSpawnedEvent event){
        //Por si no removieron el pet con /pet remove
        Player player = Bukkit.getPlayer(event.getPet().getOwner());
        if(plugin.getSpawnMCPet().containsKey(player)) {
            fr.nocsy.mcpets.data.Pet pet = plugin.getSpawnMCPet().get(player);
            actions = plugin.getConfig().getStringList("pets."+pet.getId()+".actions.onremove");
            //records.removeRecord(event.getPet().getOwner(), pet, actions);
            for (Object action : actions) {
                setActions.chooseTheOptionforRemove(action.toString(), player);
            }
            plugin.getSpawnMCPet().remove(player);
        }
        //spawnea el pet
        plugin.getSpawnMCPet().put(player, event.getPet());
        actions = plugin.getConfig().getStringList("pets." + event.getPet().getId() + ".actions.onspawn");
        //records.insertRecord(event.getOwner(),event.getPet(),actions);
        if(actions.size() != 0) {
            player.sendMessage(plugin.getLang().getString("messages.Spawn_Pet"));
            for (Object action : actions) {
                setActions.chooseTheOptionforSpawn(action.toString(), player);
            }
        }
    }

    @EventHandler
    public void onRemovePet(fr.nocsy.mcpets.events.PetDespawnEvent event){
        Player player = Bukkit.getPlayer(event.getPet().getOwner());
        fr.nocsy.mcpets.data.Pet petName = plugin.getSpawnMCPet().get(player);
        actions = plugin.getConfig().getStringList("pets."+petName.getId()+".actions.onremove");
        //records.removeRecord(event.getOwner(), petName, actions);
        for (Object action : actions) {
            setActions.chooseTheOptionforRemove(action.toString(), player);
        }
        plugin.getSpawnMCPet().remove(player);
    }


}
