package fr.tathan.linked.event;

import fr.tathan.linked.Config;
import fr.tathan.linked.Linked;
import fr.tathan.linked.commands.LinkCommands;
import fr.tathan.linked.registry.AttachmentsRegistry;
import fr.tathan.linked.registry.DamageTypeRegistry;
import fr.tathan.linked.util.LinkInfos;
import fr.tathan.linked.util.Methods;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Linked.MODID)
public class Events {

    @SubscribeEvent
    private static void onCommandsRegister(RegisterCommandsEvent event) {
        Linked.LOGGER.error("Registering commands");
        LinkCommands.register(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

    @SubscribeEvent
    private static void onPlayerDamage(LivingDamageEvent event) {
       Entity entity = event.getEntity();
       if (!event.getSource().is(DamageTypeRegistry.LOVE)){

           if(entity instanceof Player player){
               String test = player.getData(AttachmentsRegistry.MANA);
               Player linkedPlayer = player.level().getPlayerByUUID(UUID.fromString(test));
               if(linkedPlayer == null || linkedPlayer.getUUID() == player.getUUID()) return;

               linkedPlayer.hurt(DamageTypeRegistry.of(player.level(), DamageTypeRegistry.LOVE), event.getAmount());
           }
       }

    }

    @SubscribeEvent
    private static void onPlayerDied(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player player){
            if (Config.dieWithPartner){
                if(!Methods.hasValidLinkUUID(player) ) return;

                LinkInfos linkInfos = Methods.getLinkInfos(player);
                String playerLinkUUID = linkInfos.getPlayerLinkUUID();
                Player linkedPlayer = linkInfos.getLinkedPlayer();
                linkedPlayer.kill();
            }
        }
    }


}
