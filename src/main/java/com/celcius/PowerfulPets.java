package com.celcius;
import com.celcius.command.Commands;
import com.celcius.listeners.PetHandler;
import com.kirelcodes.miniaturepets.MiniaturePets;
import com.kirelcodes.miniaturepets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class PowerfulPets extends JavaPlugin {
    private File configFile;
    private File langFile;
    private FileConfiguration config;
    private FileConfiguration lang;
    public static HashMap<UUID, String> spawnPet = new HashMap<>();
    Pet pet;

    @Override
    public void onEnable() {
        createFiles();
        Bukkit.getConsoleSender().sendMessage("§5 The plugin has been enabled §b 1.0");
        Bukkit.getConsoleSender().sendMessage("§5 Developed by §b Celcius");
        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender()
                .sendMessage("§5 The plugin has been disable");
    }

    public void registerCommands() {
        this.getCommand("powerfulpets").setExecutor(new Commands(this));
    }

    public void registerEvents() {
        PluginManager mg = getServer().getPluginManager();
        mg.registerEvents(new PetHandler(this), this);
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

    }

    @Override
    public void reloadConfig(){

        if (this.config != null) this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.lang != null) this.lang = YamlConfiguration.loadConfiguration(this.langFile);

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
}