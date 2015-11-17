package net.lomeli.ec.core.world;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import net.minecraftforge.fml.common.IWorldGenerator;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.lib.ModVars;

public class SilverBlockGenerator implements IWorldGenerator {
    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimensionId() == 0) {
            for (int k = 0; k < 16; k++) {
                int firstBlockXCoord = chunkX + rand.nextInt(16);
                int firstBlockZCoord = chunkZ + rand.nextInt(16);
                int quisqueY = rand.nextInt(40);
                BlockPos quisquePos = new BlockPos(firstBlockXCoord, quisqueY, firstBlockZCoord);
                if (ModVars.silverCreeperSpawn > 0 && isValidBiome(world, quisquePos))
                    new WorldGenMinable(ElementalCreepers.silverCreepBlock.getStateFromMeta(0), ModVars.silverCreeperSpawn).generate(world, rand, quisquePos);
            }
        }
    }

    public boolean isValidBiome(World world, BlockPos pos) {
        BiomeGenBase biome = world.getBiomeGenForCoords(pos);
        return biome != null && (biome == BiomeGenBase.extremeHills || biome == BiomeGenBase.extremeHillsEdge || biome == BiomeGenBase.extremeHillsPlus);
    }
}
