package fr.tathan.linked.registry;

import com.mojang.serialization.Codec;
import fr.tathan.linked.Linked;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class AttachmentsRegistry {
    // Create the DeferredRegister for attachment types
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Linked.MODID);

    public static final Supplier<AttachmentType<String>> MANA = ATTACHMENT_TYPES.register(
            "link", () -> AttachmentType.builder(() -> "76b0e8ee-1acb-457e-88e4-3dece8eedca3").serialize(Codec.STRING).copyOnDeath().build());



}
