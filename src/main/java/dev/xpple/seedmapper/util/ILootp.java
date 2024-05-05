package dev.xpple.seedmapper.util;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.data.Pair;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import com.seedfinding.mcfeature.loot.ChestContent;
import com.seedfinding.mcfeature.loot.ILoot;
import com.seedfinding.mcfeature.loot.LootContext;
import com.seedfinding.mcfeature.loot.LootTable;
import com.seedfinding.mcfeature.loot.item.ItemStack;
import com.seedfinding.mcfeature.structure.generator.Generator;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ILootp implements ILoot {


    @Override
    public List<ChestContent> getLoot(long structureSeed, Generator generator, boolean indexed) {
        return ILoot.super.getLoot(structureSeed, generator, indexed);
    }

    @Override
    public List<ChestContent> getLoot(long structureSeed, Generator generator, ChunkRand rand, boolean indexed) {
        return ILoot.super.getLoot(structureSeed, generator, rand, indexed);
    }

    @Override
    public List<ChestContent> getLootAtPos(long worldSeed, CPos pos, ChunkRand rand, boolean indexed) {
        return ILoot.super.getLootAtPos(worldSeed, pos, rand, indexed);
    }

    @Override
    public HashMap<Generator.ILootType, List<List<ItemStack>>> getLootEx(long structureSeed, Generator generator, ChunkRand rand, boolean indexed) {
        return ILoot.super.getLootEx(structureSeed, generator, rand, indexed);
    }

    @Override
    public boolean shouldAdvanceInChunks() {
        return ILoot.super.shouldAdvanceInChunks();
    }

    @Override
    public int getDecorationSalt() {
        return 0;
    }

    @Override
    public boolean isCorrectGenerator(Generator generator) {
        return false;
    }

    @Override
    public MCVersion getVersion() {
        return null;
    }

    @Override
    public SpecificCalls getSpecificCalls() {
        return null;
    }



}
