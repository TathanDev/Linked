package fr.tathan.linked.registry;

import fr.tathan.linked.Linked;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DamageTypeRegistry {

    public static final DeferredRegister<DamageType> DAMAGE_TYPE_REGISTER =
            DeferredRegister.create(Registries.DAMAGE_TYPE, Linked.MODID);

    public static final ResourceKey<DamageType> LOVE = registerKey("love");

    private static ResourceKey<DamageType> registerKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Linked.MODID, name));
    }

    public static DamageSource of(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

}
