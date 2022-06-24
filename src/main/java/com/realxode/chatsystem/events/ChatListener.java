package com.realxode.chatsystem.events;

import com.realxode.chatsystem.ChatSystem;
import com.realxode.chatsystem.channel.ChatChannelManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

import static com.realxode.api.chat.ChatUtil.translate;

public class ChatListener implements Listener {

    private final ChatSystem plugin;

    public ChatListener(ChatSystem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        ChatChannelManager channelManager = new ChatChannelManager();
        String format = translate(PlaceholderAPI.setPlaceholders(player, plugin.getChatConfig().getString("CHAT-FORMAT")));
        if (channelManager.isInChannel(player)) {
            player.sendMessage("PASSED");
            e.getRecipients().clear();
            for (UUID recipientUuid : channelManager.getChannel(channelManager.getChannelNameByPlayer(player)).getPlayerList()) {
                Player recipient = Bukkit.getPlayer(recipientUuid);
                e.getRecipients().add(recipient);
            }
            e.setFormat(format.replace("{player-name}", "%1$s").replace("{message}", "%2$s")
                    .replace("{channelName}", channelManager.getChannelNameByPlayer(player)));
        } else {
            player.sendMessage(String.valueOf(channelManager.isInChannel(player)));
            e.setFormat(format.replace("{player-name}", "%1$s").replace("{message}", "%2$s"));
        }
    }
}
