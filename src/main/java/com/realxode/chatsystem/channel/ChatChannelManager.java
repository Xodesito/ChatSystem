package com.realxode.chatsystem.channel;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.realxode.api.chat.ChatUtil.translate;

public class ChatChannelManager {

    private final Map<String, ChatChannel> channels = new HashMap<>();
    private final Map<ChatChannel, Player> playerChannel = new HashMap<>();

    public void createChannel(String name, Player owner) {
        if (channels.containsKey(name)) {
            owner.sendMessage(translate("There is already a channel with that name! Use /channel " + name + " to join that channel."));
            return;
        }
        channels.put(name, new ChatChannel(name, owner));
        channels.get(name).addPlayerToChannel(owner);
        owner.sendMessage(translate("Channel " + name + " successfully created."));
        playerChannel.put(getChannel(name), owner);
    }

    public void deleteChannel(String name) {
        if (!channels.containsKey(name)) {
            throw new RuntimeException("&cThat channel does not exist!");
        }
        channels.get(name).getPlayerList().clear();
        channels.remove(name);
    }

    public boolean isInChannel(Player player) {
        player.sendMessage(playerChannel.values().toString());
        return playerChannel.containsValue(player);
    }

    public String getChannelNameByPlayer(Player player) {
        for (ChatChannel channel : channels.values()) {
            if (channel.getPlayerList().contains(player.getUniqueId())) {
                AtomicReference<String> name = null;
                channels.forEach((key, value) -> {
                    if (value.equals(channel)) {
                        name.set(key);
                    }
                });
                return name.get();
            }
        }
        return null;
    }

    public ChatChannel getChannel(String name) {
        return channels.get(name);
    }
}
