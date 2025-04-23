package me.justmaya.cherryGeneration.generator;

import me.justmaya.cherryGeneration.biome.CherryBiomeProvider;
import org.bukkit.HeightMap;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CherryChunkGenerator extends ChunkGenerator {

    private final int currentHeight = 50;
    private SimplexOctaveGenerator generator;
    private List<Material> materials = List.of(
            Material.PINK_CONCRETE,
            Material.PINK_CONCRETE_POWDER,
            Material.PINK_WOOL,
            Material.PINK_GLAZED_TERRACOTTA,
            Material.CHERRY_PLANKS,
            Material.STRIPPED_CHERRY_WOOD,
            Material.CHERRY_LEAVES
    );

    public  Material getRandomMaterial(Random random) {
        if (materials.isEmpty()) {
            return Material.PINK_CONCRETE_POWDER;
        }
        int randomIndex = random.nextInt(materials.size());
        return materials.get(randomIndex);
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), 8);
            generator.setScale(0.005);
        }
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkX * 16 + x;
                int worldZ = chunkZ * 16 + z;
                int height = (int) (generator.noise(worldX, worldZ, 0.5, 0.5) * 15 + 50);

                chunkData.setBlock(x, height, z, getRandomMaterial(random));
                chunkData.setBlock(x, height - 1, z, getRandomMaterial(random));
                for (int y = height - 2; y >= 1; y--) {
                    chunkData.setBlock(x, y, z, getRandomMaterial(random));
                }
                chunkData.setBlock(x, 0, z, Material.BEDROCK);
            }
        }
    }

    @Override
    public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new CherryBiomeProvider();
    }

    @Override
    public int getBaseHeight(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull HeightMap heightMap) {
        if (generator == null) {
            generator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), 8);
            generator.setScale(0.005);
        }
        return (int) (generator.noise(x, z, 0.5, 0.5) * 15 + 50);
    }

    @Override
    public boolean shouldGenerateNoise() {
        return false;
    }

    @Override
    public boolean shouldGenerateSurface() {
        return true;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return false;
    }

    @Override
    public boolean shouldGenerateCaves() { return false; }

    @Override
    public boolean shouldGenerateDecorations() {
        return false;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return false;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return false;
    }
}
