package com.celcius;
import com.celcius.command.CommandsExecute;
import com.celcius.handlers.Records;
import com.celcius.listeners.MCPetsHandler;
import com.celcius.listeners.MinPetHandler;
import com.celcius.listeners.PlayerListener;
import com.celcius.utils.SetActions;
import com.kirelcodes.miniaturepets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


public class PowerfulPets extends JavaPlugin {
    private File configFile;
    private File langFile;
    private File recordsFile;
    private FileConfiguration records;
    private FileConfiguration config;
    private FileConfiguration lang;
    private static HashMap<Player, Pet> spawnMinaturePet = new HashMap<>();
    private static HashMap<Player, fr.nocsy.mcpets.data.Pet> spawnMCPet = new HashMap<>();
    private SetActions setActions;
    private Records recordClass;
    List actions;
    private boolean foundMCPets;
    private boolean foundMiniaturePets;
    @Override
    public void onEnable() {
        if (!setupPetPlugins()) {
            this.getLogger().severe("Disabled due to no pet plugin found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        createFiles();
        Bukkit.getConsoleSender().sendMessage("§5 The plugin has been enabled §b 2.0");
        Bukkit.getConsoleSender().sendMessage("§5 Developed by §b Celcius");
        registerCommands();
        registerGlobalEvents();
        this.setActions = new SetActions();
        this.recordClass = new Records();
    }

    private boolean setupPetPlugins() {
        boolean foundPetPlugin = false;
        if (Bukkit.getPluginManager().getPlugin("MCPets") != null) {
            Bukkit.getConsoleSender().sendMessage("§5[PowerfulPets] Hooked onto MCPets");
            registerMCPetsEvents();
            foundPetPlugin = true;
            foundMCPets = true;
        }
        if (Bukkit.getPluginManager().getPlugin("MiniaturePets") != null){
            Bukkit.getConsoleSender().sendMessage("§5[PowerfulPets] Hooked onto MiniaturePets");
            registerMiniatureEvents();
            foundPetPlugin = true;
            foundMiniaturePets = true;
        }
        return foundPetPlugin;
    }


    public void registerCommands() {
        this.getCommand("powerfulpets").setExecutor(new CommandsExecute());
    }

    public void removeAllMiniaturePetsWhenServerClose() {
        for (Map.Entry<Player, Pet> entry : spawnMinaturePet.entrySet()) {
            actions = this.getConfig().getStringList("pets." +  entry.getValue().getType() + ".actions.onremove");
            recordClass.removeRecord(entry.getKey(), entry.getValue(), actions);
            for (Object action : actions) {
                getSetActions().chooseTheOptionforRemove(action.toString(), entry.getKey());
            }
        }
    }

    @Override
    public void onDisable() {
        removeAllMiniaturePetsWhenServerClose();
        Bukkit.getConsoleSender()
                .sendMessage("§5 The plugin has been disable");
    }

    public void registerGlobalEvents() {
        PluginManager mg = getServer().getPluginManager();
        mg.registerEvents(new PlayerListener(), this);
    }

    public void registerMiniatureEvents(){
        PluginManager mg = getServer().getPluginManager();
        mg.registerEvents(new MinPetHandler(), this);
    }

    public void registerMCPetsEvents(){
        PluginManager mg = getServer().getPluginManager();
        mg.registerEvents(new MCPetsHandler(), this);
    }

    public static HashMap<Player, Pet> getSpawnMinaturePet() {
        return spawnMinaturePet;
    }

    public static HashMap<Player, fr.nocsy.mcpets.data.Pet> getSpawnMCPet() {
        return spawnMCPet;
    }

    public void createFiles() {

        if (!(getDataFolder().exists())) {
            getDataFolder().mkdirs();
        }

        this.configFile = new File(getDataFolder(), "config.yml");

        if (!(this.configFile.exists())) {
            try {
                Files.copy(getResource(this.configFile.getName()), this.configFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.langFile = new File(getDataFolder(), "lang.yml");

        if (!(this.langFile.exists())) {
            try {
                Files.copy(getResource(this.langFile.getName()), this.langFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.recordsFile = new File(getDataFolder(), "records.yml");

        if (!(this.recordsFile.exists())) {
            try {
                Files.copy(getResource(this.recordsFile.getName()), this.recordsFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void reloadConfig(){

        if (this.config != null) this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.lang != null) this.lang = YamlConfiguration.loadConfiguration(this.langFile);

        if (this.records != null) this.records = YamlConfiguration.loadConfiguration(this.recordsFile);
    }

    @Override
    public void saveConfig() {

        if (this.config != null) {
            try {
                this.config.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.lang != null) {
            try {
                this.lang.save(this.langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (this.records != null) {
            try {
                this.records.save(this.recordsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public FileConfiguration getRecords() {

        if (this.records != null) return records;

        this.records = new YamlConfiguration();

        try {

            assert false;
            this.records.load(recordsFile);

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return records;
    }

    public FileConfiguration getLang() {

        if (this.lang != null) return lang;

        this.lang = new YamlConfiguration();

        try {

            assert false;
            this.lang.load(langFile);

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return lang;
    }

    @Override
    public FileConfiguration getConfig() {

        if (this.config != null) return config;

        this.config = new YamlConfiguration();

        try {

            assert false;
            this.config.load(configFile);

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return config;
    }

    public SetActions getSetActions() {
        return setActions;
    }

    public boolean isFoundMCPets() {
        return foundMCPets;
    }

    public void setFoundMCPets(boolean foundMCPets) {
        this.foundMCPets = foundMCPets;
    }

    public boolean isFoundMiniaturePets() {
        return foundMiniaturePets;
    }

    public void setFoundMiniaturePets(boolean foundMiniaturePets) {
        this.foundMiniaturePets = foundMiniaturePets;
    }
}