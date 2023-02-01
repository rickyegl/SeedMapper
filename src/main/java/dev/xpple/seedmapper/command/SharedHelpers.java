package dev.xpple.seedmapper.command;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.seedfinding.mccore.state.Dimension;
import com.seedfinding.mccore.version.MCVersion;
import dev.xpple.seedmapper.util.DatabaseHelper;
import dev.xpple.seedmapper.util.config.Configs;
import dev.xpple.seedmapper.util.config.SeedResolution;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.xpple.seedmapper.SeedMapper.CLIENT;

public final class SharedHelpers {

    public interface Exceptions {
        DynamicCommandExceptionType NULL_POINTER_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.nullPointerException", arg));
        DynamicCommandExceptionType DIMENSION_NOT_SUPPORTED_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.dimensionNotSupported", arg));
        DynamicCommandExceptionType VERSION_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.versionNotFound", arg));
        SimpleCommandExceptionType INVALID_DIMENSION_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("commands.exceptions.invalidDimension"));
        DynamicCommandExceptionType BIOME_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.biomeNotFound", arg));
        DynamicCommandExceptionType STRUCTURE_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.structureNotFound", arg));
        DynamicCommandExceptionType DECORATOR_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.decoratorNotFound", arg));
        DynamicCommandExceptionType ITEM_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.itemNotFound", arg));
        DynamicCommandExceptionType LOOT_ITEM_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.lootItemNotFound", arg));
        DynamicCommandExceptionType BLOCK_NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType(arg -> Text.translatable("commands.exceptions.blockNotFound", arg));
    }

    public final long seed;
    public final Dimension dimension;
    public final MCVersion mcVersion;

    public SharedHelpers(FabricClientCommandSource source) throws CommandSyntaxException {
        this.seed = getSeed(source);
        if (source.getMeta("dimension") == null) {
            this.dimension = getDimension(source.getWorld().getRegistryKey().getValue().getPath());
        } else {
            this.dimension = getDimension(((Identifier) source.getMeta("dimension")).getPath());
        }
        if (source.getMeta("version") == null) {
            this.mcVersion = getMCVersion(CLIENT.getGame().getVersion().getName());
        } else {
            this.mcVersion = (MCVersion) source.getMeta("version");
        }
    }

    public static long getSeed(FabricClientCommandSource source) throws CommandSyntaxException {
        Long seed;
        for (SeedResolution.Method method : Configs.SeedResolutionOrder) {
            seed = switch (method) {
                case COMMAND_SOURCE -> (Long) source.getMeta("seed");
                case SAVED_SEEDS_CONFIG -> {
                    String key = CLIENT.getNetworkHandler().getConnection().getAddress().toString();
                    yield Configs.SavedSeeds.get(key);
                }
                case ONLINE_DATABASE -> {
                    String key = CLIENT.getNetworkHandler().getConnection().getAddress().toString();
                    yield DatabaseHelper.getSeed(key);
                }
                case SEED_CONFIG -> Configs.Seed;
            };
            if (seed != null) {
                return seed;
            }
        }
        throw Exceptions.NULL_POINTER_EXCEPTION.create("Seed");
    }

    public static Dimension getDimension(String dimensionPath) throws CommandSyntaxException {
        Dimension dimension = Dimension.fromString(dimensionPath);
        if (dimension == null) {
            throw Exceptions.DIMENSION_NOT_SUPPORTED_EXCEPTION.create(dimensionPath);
        }
        return dimension;
    }

    public static MCVersion getMCVersion(String version) throws CommandSyntaxException {
        MCVersion mcVersion = MCVersion.fromString(version);
        if (mcVersion == null) {
            throw Exceptions.VERSION_NOT_FOUND_EXCEPTION.create(version);
        }
        return mcVersion;
    }
}
