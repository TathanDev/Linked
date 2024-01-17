package fr.tathan.linked.util;

import net.minecraft.world.entity.player.Player;

public class LinkInfos {

    Player player;
    Player linkedPlayer;

    public LinkInfos(Player player, Player linkedPlayer){
        this.player = player;
        this.linkedPlayer = linkedPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getLinkedPlayer() {
        return linkedPlayer;
    }

    public String getPlayerUUID() {
        return player.getStringUUID();
    }
    public String getPlayerLinkUUID() {
        return linkedPlayer.getStringUUID();
    }
}
