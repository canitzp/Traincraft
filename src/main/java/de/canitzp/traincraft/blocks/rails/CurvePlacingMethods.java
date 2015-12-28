package de.canitzp.traincraft.blocks.rails;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.tile.TileEntityCurvedTrackBase;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author canitzp
 */
public class CurvePlacingMethods {

    public static void setBlocksMediumCurved(World world, int x, int y, int z, int rotation) {
        if (!world.isRemote) {
            Block ghost = GameRegistry.findBlock(Traincraft.MODID, ((BlockCurvedTrack)TrackBlock.MEDIUM_CURVE.track).getSearchName(TileEntityCurvedTrackBase.TrackType.GHOST));
            Block second = GameRegistry.findBlock(Traincraft.MODID, ((BlockCurvedTrack)TrackBlock.MEDIUM_CURVE.track).getSearchName(TileEntityCurvedTrackBase.TrackType.SECOND));
            switch (rotation) {
                case 0: {
                    world.setBlock(x, y, z + 1, ghost, rotation, 2);
                    world.setBlock(x + 1, y, z + 1, ghost, rotation, 2);
                    world.setBlock(x + 1, y, z + 2, ghost, rotation, 2);
                    world.setBlock(x + 2, y, z+ 2, second, rotation, 2);
                    break;
                }
                case 1: {
                    world.setBlock(x - 1, y, z, ghost, rotation, 2);
                    world.setBlock(x - 1, y, z + 1, ghost, rotation, 2);
                    world.setBlock(x - 2, y, z + 1, ghost, rotation, 2);
                    world.setBlock(x - 2, y, z + 2, second, rotation, 2);
                    break;
                }
                case 2: {
                    world.setBlock(x, y, z - 1, ghost, rotation, 2);
                    world.setBlock(x - 1, y, z - 1, ghost, rotation, 2);
                    world.setBlock(x - 1, y, z - 2, ghost, rotation, 2);
                    world.setBlock(x - 2, y, z - 2, second, rotation, 2);
                    break;
                }
                case 3: {
                    world.setBlock(x + 1, y, z, ghost, rotation, 2);
                    world.setBlock(x + 1, y, z - 1, ghost, rotation, 2);
                    world.setBlock(x + 2, y, z - 1, ghost, rotation, 2);
                    world.setBlock(x + 2, y, z - 2, second, rotation, 2);
                    break;
                }
            }
        }
    }

    /**
     * Checks if a given Block with a given Meta is present in given Positions
     *
     * @param world     The World
     * @param x         The Start X Coord
     * @param y         The Start Y Coord
     * @param z         The Start Z Coord
     * @param positions The Positions, an array of {xCoord, yCoord, zCoord} arrays containing RELATIVE Positions
     * @return Is every block present?
     *
     * This Method is created by Ellpeck and used in his Mod ActuallyAdditions.
     */
    public static boolean hasBlocksInPlacesGiven(World world, int x, int y, int z, int[][] positions){
        for(int[] xYZ : positions){
            if(!(world.isAirBlock(x+xYZ[0], y+xYZ[1], z+xYZ[2])) || !world.getBlock(x+xYZ[0], y+xYZ[1] - 1, z+xYZ[2]).isBlockNormalCube()){
                return false;
            }
        }
        return true;
    }

    private static void setBlockWithTileEntityData(World world, int x, int y, int z, BlockCurvedTrack block, int meta, BlockPos mainPos){
        world.setBlock(x, y, z, block, meta, 2);
        HashMap<Integer, TileEntityCurvedTrackBase> tiles = new HashMap<Integer, TileEntityCurvedTrackBase>();
        if(block.trackType == TileEntityCurvedTrackBase.TrackType.GHOST){
            tiles.put(tiles.size(), (TileEntityCurvedTrackBase) world.getTileEntity(x, y, z));
        } else {
            tiles.get(0).mainBlock = (BlockCurvedTrack) world.getBlock(mainPos.x, mainPos.y, mainPos.z);
        }
    }


    public class BlockPos{
        public int x, y, z;
        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}
