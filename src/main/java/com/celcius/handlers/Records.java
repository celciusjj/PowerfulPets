package com.celcius.handlers;

import com.celcius.PowerfulPets;
import com.kirelcodes.miniaturepets.pets.Pet;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Records {
    private final PowerfulPets plugin = PowerfulPets.getPlugin(PowerfulPets.class);

    public void insertRecord(Player player, Pet pet, List<String> actions){
        Date date = new Date();
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String stringDate = dateFormatGmt.format(date);
        String key = player.getUniqueId()+"/"+stringDate;

        plugin.getRecords().createSection(key);
        plugin.getRecords().set(key+".type","spawn");
        plugin.getRecords().set(key+".name",player.getName());
        plugin.getRecords().set(key+".pet",pet.getType());
        plugin.getRecords().set(key+".date",dateFormatGmt.format(date));
        plugin.getRecords().set(key+".actions",actions);
        plugin.getRecords().set(key+".location",player.getLocation());
        plugin.saveConfig();
    }

    public void removeRecord(Player player, Pet pet, List<String> actions){
        Date date = new Date();
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        String stringDate = dateFormatGmt.format(date);
        String key = player.getUniqueId()+"/"+stringDate;

        plugin.getRecords().createSection(key);
        plugin.getRecords().set(key+".type","despawn");
        plugin.getRecords().set(key+".name",player.getName());
        plugin.getRecords().set(key+".pet",pet.getType());
        plugin.getRecords().set(key+".date",stringDate);
        plugin.getRecords().set(key+".actions",actions);
        plugin.getRecords().set(key+".location",player.getLocation());
        plugin.saveConfig();

        plugin.saveConfig();
    }
}
