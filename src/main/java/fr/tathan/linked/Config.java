package fr.tathan.linked;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Linked.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue DIE_WITH_PARTNER = BUILDER
            .comment("Should the player die when his partner dies ?")
            .define("dieWithPartner", true);
    private static final ModConfigSpec.BooleanValue SHARE_EFFECTS = BUILDER
            .comment("Should the player share his effects with his link ?")
            .define("shareEffects", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean dieWithPartner;
    public static boolean shareEffects;


    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        dieWithPartner = DIE_WITH_PARTNER.get();
        shareEffects = SHARE_EFFECTS.get();
    }
}
