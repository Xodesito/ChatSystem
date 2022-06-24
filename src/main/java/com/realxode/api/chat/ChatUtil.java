package com.realxode.api.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ChatUtil {

    private ChatUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> in){
        return (List<String>)in.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }

    public static String strip(String in) {
        return ChatColor.stripColor(in);
    }

    public static void message(CommandSender sender, String in){
        sender.sendMessage(translate(in));
    }

    public static void message(Player player, String in){
        player.sendMessage(translate(in));
    }

    public static void broadcast(String in){
        Bukkit.broadcastMessage(translate(in));
    }

    public static void log(String in){
        Bukkit.getConsoleSender().sendMessage(translate(in));
    }

}
