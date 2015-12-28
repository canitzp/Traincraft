package de.canitzp.traincraft.gen;

import cpw.mods.fml.common.IWorldGenerator;
import de.canitzp.traincraft.blocks.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

/**
 * @author canitzp
 */
public class WorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId){
            case -1:
                generateNether(world, random, chunkX*16, chunkZ*16);
            case 1:
                generateEnd(world, random, chunkX*16, chunkZ*16);
            default:
                generateWorld(world, random, chunkX*16, chunkZ*16);
        }
    }

    private void generateWorld(World world, Random random, int x, int z) {
        addOreSpawn(BlockRegistry.oilSand, Blocks.sand, world, x, z, MathHelper.getRandomIntegerInRange(random, 5, 8), 10, 50, 70);
    }

    private void generateEnd(World world, Random random, int i, int i1) {
    }

    private void generateNether(World world, Random random, int i, int i1) {
    }

    public void addOreSpawn(Block block, Block inSpawn, World world, int blockXPos, int blockZPos, int maxVeinSize, int chancesToSpawn, int minY, int maxY){
        if(maxY > minY){
            Random random = world.rand;
            int yDiff = maxY-minY;
            for(int i = 0; i < chancesToSpawn; i++){
                int posX = blockXPos+random.nextInt(16);
                int posY = minY+random.nextInt(yDiff);
                int posZ = blockZPos+random.nextInt(16);
                new WorldGenMinable(block, 0, maxVeinSize, inSpawn).generate(world, random, posX, posY, posZ);
            }
        }
    }
}
