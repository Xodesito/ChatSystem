package com.realxode.chatsystem;

import com.realxode.api.file.FileConfig;
import com.realxode.chatsystem.channel.cmds.ChannelCommand;
import com.realxode.chatsystem.events.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatSystem extends JavaPlugin {

    private final FileConfig chatConfig = new FileConfig(this, "chatCfg.yml");

    @Override
    public void onEnable() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            throw new RuntimeException("Could not find PlaceholderAPI!! Plugin can not work without it!");
        }
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("channel").setExecutor(new ChannelCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public FileConfig getChatConfig() {
        return chatConfig;
    }
}
