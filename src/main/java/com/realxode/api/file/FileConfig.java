package com.realxode.api.file;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileConfig {
    private final File file;

    private FileConfiguration configuration;

    public File getFile() {
        return this.file;
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public FileConfig(JavaPlugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to create new file " + fileName);
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }

    public double getDouble(String path) {
        if (this.configuration.contains(path))
            return this.configuration.getDouble(path);
        return 0.0D;
    }

    public int getInt(String path) {
        if (this.configuration.contains(path))
            return this.configuration.getInt(path);
        return 0;
    }

    public boolean getBoolean(String path) {
        if (this.configuration.contains(path))
            return this.configuration.getBoolean(path);
        return false;
    }

    public long getLong(String path) {
        if (this.configuration.contains(path))
            return this.configuration.getLong(path);
        return 0L;
    }

    public String getString(String path) {
        if (this.configuration.contains(path))
            return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        return null;
    }

    public String getString(String path, String callback, boolean colorize) {
        if (this.configuration.contains(path)) {
            if (colorize)
                return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
            return this.configuration.getString(path);
        }
        return callback;
    }

    public List<String> getReversedStringList(String path) {
        List<String> list = getStringList(path);
        if (list != null) {
            int size = list.size();
            List<String> toReturn = new ArrayList<>();
            for (int i = size - 1; i >= 0; i--)
                toReturn.add(list.get(i));
            return toReturn;
        }
        return Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
    }

    public List<String> getStringList(String path) {
        if (this.configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : this.configuration.getStringList(path))
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            return strings;
        }
        return Arrays.asList(new String[] { "ERROR: STRING LIST NOT FOUND!" });
    }

    public List<String> getStringListOrDefault(String path, List<String> toReturn) {
        if (this.configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : this.configuration.getStringList(path))
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            return strings;
        }
        return toReturn;
    }

    public void save() {
        try {
            this.configuration.save(this.file);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Could not save config file " + this.file.toString());
            e.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
    }
}
