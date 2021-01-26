package com.celcius.listeners;

import com.celcius.PowerfulPets;
import com.celcius.utils.SetActions;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class PetHandler implements Listener {

    PowerfulPets plugin;
    List actions;
    SetActions setActions = new SetActions();

    public PetHandler(PowerfulPets plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawnPet(PetSpawnEvent event){
        //Por si no removieron el pet con /pet remove
        if(plugin.spawnPet.containsKey(event.getOwner().getUniqueId())) {
            actions = plugin.getConfig().getStringList("pets."+event.getType()+".actions.onremove");
            for (Object action : actions) {
                setActions.chooseTheOptionforRemove(action.toString(), event.getOwner());
            }
            plugin.spawnPet.remove(event.getOwner().getUniqueId());
        }

        //spawnea el pet
            plugin.spawnPet.put(event.getOwner().getUniqueId(), event.getType());
            actions = plugin.getConfig().getStringList("pets." + event.getType() + ".actions.onspawn");

            if(actions.size() != 0) {
                event.getOwner().sendMessage(plugin.getLang().getString("messages.Spawn_Pet"));
                for (Object action : actions) {
                    setActions.chooseTheOptionforSpawn(action.toString(), event.getOwner());
                }
            }
    }

    @EventHandler
    public void onRemovePet(PetRemovedEvent event){
        String petName = plugin.spawnPet.get(event.getOwner().getUniqueId());
        actions = plugin.getConfig().getStringList("pets."+petName+".actions.onremove");
        for (Object action : actions) {
            setActions.chooseTheOptionforRemove(action.toString(), event.getOwner());
        }
        plugin.spawnPet.remove(event.getOwner().getUniqueId());
    }
}
