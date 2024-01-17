package fr.tathan.linked.util;

import fr.tathan.linked.registry.AttachmentsRegistry;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class Methods {


    public static boolean hasValidLinkUUID(Player player){
        String playerLinkUUID = player.getData(AttachmentsRegistry.MANA);
        Player linkedPlayer = player.level().getPlayerByUUID(UUID.fromString(playerLinkUUID));
        if(linkedPlayer == null) {
            return false;
        } else {
            return true;
        }
    }

    public static LinkInfos getLinkInfos(Player player){
        String playerLinkUUID = player.getData(AttachmentsRegistry.MANA);
        Player linkedPlayer = player.level().getPlayerByUUID(UUID.fromString(playerLinkUUID));
        if(linkedPlayer == null) {
            return null;
        } else {
            return new LinkInfos(player, linkedPlayer);
        }
    }
}
