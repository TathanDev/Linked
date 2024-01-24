package fr.tathan.linked.event;

import fr.tathan.linked.Config;
import fr.tathan.linked.Linked;
import fr.tathan.linked.commands.LinkCommands;
import fr.tathan.linked.registry.AttachmentsRegistry;
import fr.tathan.linked.registry.DamageTypeRegistry;
import fr.tathan.linked.util.LinkInfos;
import fr.tathan.linked.util.Methods;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.server.command.ConfigCommand;

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

    @SubscribeEvent
    private static void playerHurt(LivingHurtEvent event){

        if(event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Player secondPlayer){
            if(!Methods.hasValidLinkUUID(player)) return;

            LinkInfos linkInfos = Methods.getLinkInfos(player);
            String playerLinkUUID = linkInfos.getPlayerLinkUUID();
            if(playerLinkUUID.equals(secondPlayer.getUUID().toString())) {
                Linked.LOGGER.error("Player hurt");

                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    private static void playerEffectEvent(MobEffectEvent.Added event){
        Entity entity = event.getEntity();

        if(Config.shareEffects){
            if(entity instanceof Player player){
                if(player.getPersistentData().getBoolean("canReceiveEffect") ){
                    if(!Methods.hasValidLinkUUID(player)) return;

                    LinkInfos linkInfos = Methods.getLinkInfos(player);
                    Player linkedPlayer = linkInfos.getLinkedPlayer();
                    if(linkedPlayer.getPersistentData().getBoolean("canReceiveEffect")) {
                        linkedPlayer.getPersistentData().putBoolean("canReceiveEffect", false);
                        linkedPlayer.addEffect(event.getEffectInstance());
                        return;
                    } else {
                        linkedPlayer.getPersistentData().putBoolean("canReceiveEffect", true);
                    }
                    player.getPersistentData().putBoolean("canReceiveEffect", false);
                    linkedPlayer.getPersistentData().putBoolean("canReceiveEffect", false);
                } else {
                    player.getPersistentData().putBoolean("canReceiveEffect", true);
                }
            }
        }
    }


}
