package com.celcius.listeners;

import com.celcius.PowerfulPets;
import com.celcius.handlers.Records;
import com.celcius.utils.SetActions;
import com.kirelcodes.miniaturepets.api.events.pets.PetFinishedSpawnEvent;
import com.kirelcodes.miniaturepets.api.events.pets.PetRemovedEvent;
import com.kirelcodes.miniaturepets.pets.Pet;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class MinPetHandler implements Listener {

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
                if(plugin.getSpawnMinaturePet().containsKey(player)) {
                    Pet petName = plugin.getSpawnMinaturePet().get(player);
                    actions = plugin.getConfig().getStringList("pets."+petName.getType()+".actions.onremove");
                    records.removeRecord(player, petName, actions);
                    for (Object action : actions) {
                        setActions.chooseTheOptionforRemove(action.toString(), player);
                    }
                    plugin.getSpawnMinaturePet().remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onSpawnPet(PetFinishedSpawnEvent event){
        //Por si no removieron el pet con /pet remove

        if(plugin.getSpawnMinaturePet().containsKey(event.getOwner())) {
            Pet pet = plugin.getSpawnMinaturePet().get(event.getOwner());
            actions = plugin.getConfig().getStringList("pets."+pet.getType()+".actions.onremove");
            records.removeRecord(event.getOwner(), pet, actions);
            for (Object action : actions) {
                setActions.chooseTheOptionforRemove(action.toString(), event.getOwner());
            }
            plugin.getSpawnMinaturePet().remove(event.getOwner());
        }
        //spawnea el pet
            plugin.getSpawnMinaturePet().put(event.getOwner(), event.getPet());
            actions = plugin.getConfig().getStringList("pets." + event.getPet().getType() + ".actions.onspawn");
            records.insertRecord(event.getOwner(),event.getPet(),actions);
            if(actions.size() != 0) {
                event.getOwner().sendMessage(plugin.getLang().getString("messages.Spawn_Pet"));
                for (Object action : actions) {
                    setActions.chooseTheOptionforSpawn(action.toString(), event.getOwner());
                }
            }
    }

    @EventHandler
    public void onRemovePet(PetRemovedEvent event){
        Pet petName = plugin.getSpawnMinaturePet().get(event.getOwner());
        actions = plugin.getConfig().getStringList("pets."+petName.getType()+".actions.onremove");
        records.removeRecord(event.getOwner(), petName, actions);
        for (Object action : actions) {
            setActions.chooseTheOptionforRemove(action.toString(), event.getOwner());
        }
        plugin.getSpawnMinaturePet().remove(event.getOwner());
    }
}
