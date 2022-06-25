package com.realxode.chatsystem.channel.cmds;

import com.realxode.chatsystem.ChatSystem;
import com.realxode.chatsystem.channel.ChatChannelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.realxode.api.chat.ChatUtil.translate;

public class ChannelCommand implements CommandExecutor {

    private final ChatSystem plugin;

    public ChannelCommand(ChatSystem plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("chatsystem.channel")) {
            sender.sendMessage(translate("You don't have enough permissions!"));
            return true;
        }
        if (!(sender instanceof Player)) {
            System.out.println("This command is only for players!");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(translate("&cYou must specify a name to create or join a channel!"));
            return true;
        }
        String name = args[0];
        ChatChannelManager channelManager = plugin.getChannelManager();
        if (channelManager.isInChannel(player)) {
            player.sendMessage("&cYou are already in a channel!");
            return true;
        }
        channelManager.createChannel(name, player);
        return false;
    }
}
