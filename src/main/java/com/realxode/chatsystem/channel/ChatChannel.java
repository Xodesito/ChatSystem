package com.realxode.chatsystem.channel;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.realxode.api.chat.ChatUtil.translate;

public class ChatChannel {

    private Player owner;
    private final List<UUID> playerList;

    public ChatChannel(String name, Player owner) {
        this.owner = owner;
        playerList = new ArrayList<>();
    }

    public void addPlayerToChannel(Player player) {
        if (playerList.contains(player.getUniqueId())) {
            player.sendMessage(translate("&cYou are already part of this channel!"));
            return;
        }
        playerList.add(player.getUniqueId());
        player.sendMessage(translate("You have joined the channel, say hello!"));
    }

    public void removePlayerFromChannel(Player player) {
        if (player == owner) {
            player.sendMessage(translate("&cYou cannot leave the channel if you are the owner of this channel! " +
                    "Use /channel disband to remove it."));
            return;
        }
        playerList.remove(player.getUniqueId());
        player.sendMessage(translate("&cYou left the channel. Sending a message will be sent to the general chat!"));
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public List<UUID> getPlayerList() {
        return playerList;
    }
}
