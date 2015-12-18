package de.canitzp.traincraft;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

/**
 * @author Ellpeck
 * This Method is created by Ellpeck and used in his Mod ActuallyAdditions.
 * You can get this Class here: https://github.com/Ellpeck/ActuallyAdditions/blob/f62b1ede79c5d23a025e1814c32c83641f72dab0/src/main/java/ellpeck/actuallyadditions/network/VanillaPacketSyncer.java
 */
public class VanillaPacketSyncer {

    public static void sendTileToNearbyPlayers(TileEntity tile) {
        List allPlayers = tile.getWorldObj().playerEntities;
        for (Object player : allPlayers) {
            if (player instanceof EntityPlayerMP) {
                sendTileToPlayer(tile, (EntityPlayerMP) player, 64);
            }
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player, int maxDistance) {
        if (player.getDistance(tile.xCoord, tile.yCoord, tile.zCoord) <= maxDistance) {
            sendTileToPlayer(tile, player);
        }
    }

    public static void sendTileToPlayer(TileEntity tile, EntityPlayerMP player) {
        player.playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
    }
}
