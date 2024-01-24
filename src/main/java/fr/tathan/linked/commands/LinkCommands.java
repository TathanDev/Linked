package fr.tathan.linked.commands;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.tathan.linked.registry.AttachmentsRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.KillCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class LinkCommands {

    public LinkCommands() {

    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(((Commands.literal("linked").requires((p_137812_) -> {
            return p_137812_.hasPermission(2);
        }))).then(Commands.literal("link").then(Commands.argument("player1", EntityArgument.player())
                .then(Commands.argument("player2", EntityArgument.player()).executes((player) -> {
                    return link((CommandSourceStack)player.getSource(), EntityArgument.getPlayer(player, "player1"), EntityArgument.getPlayer(player, "player2"));
                }))))
                        .then(Commands.literal("unlink").then(Commands.argument("player", EntityArgument.player()).executes((player) -> {
                            return unlink((CommandSourceStack)player.getSource(), EntityArgument.getPlayer(player, "player"));
                        })))
                        .then(Commands.literal("getLink").then(Commands.argument("player", EntityArgument.player()).executes((player) -> {
                            return getLink((CommandSourceStack)player.getSource(), EntityArgument.getPlayer(player, "player"));
        }))));

    }

    public static int link(CommandSourceStack source, ServerPlayer player1, ServerPlayer player2) {
        player1.setData(AttachmentsRegistry.MANA, player2.getUUID().toString());
        if(player2 == null) {
            source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.no_link", player2.getDisplayName().getString()));
            return 0;
        }
        player2.setData(AttachmentsRegistry.MANA, player1.getUUID().toString());

        source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.link", player1.getDisplayName().getString() ,player2.getDisplayName().getString()));


        return 0;
    }
    public static int unlink(CommandSourceStack source, ServerPlayer player) {
        String test = player.getData(AttachmentsRegistry.MANA);
        Player linkedPlayer = player.level().getPlayerByUUID(UUID.fromString(test));
        if(linkedPlayer == null) {
            source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.no_link", player.getDisplayName().getString()));
            return 0;
        }

        linkedPlayer.setData(AttachmentsRegistry.MANA, linkedPlayer.getUUID().toString());
        player.setData(AttachmentsRegistry.MANA, player.getUUID().toString());
        source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.unlink", player.getDisplayName().getString()));


        return 0;
    }

    public static int getLink(CommandSourceStack source, ServerPlayer player) {
        String test = player.getData(AttachmentsRegistry.MANA);
        Player linkedPlayer = player.level().getPlayerByUUID(UUID.fromString(test));
        if(linkedPlayer == null || linkedPlayer.getUUID() == player.getUUID()) {
            source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.no_link", player.getDisplayName().getString()));
            return 0;
        }
        source.getPlayer().sendSystemMessage(Component.translatable("linked.commands.getlink", player.getDisplayName().getString(), linkedPlayer.getDisplayName().getString() ));


        return 0;
    }

}
