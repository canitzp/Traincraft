package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.blocks.BlockRail;
import de.canitzp.traincraft.items.ItemTrack;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.obj.OBJLoader;

import java.io.IOException;

/**
 * @author canitzp
 */
public enum Tracks {

    LONG_STRAIGHT(Length.LONG, Curve.STRAIGHT, new BlockRail(Length.LONG, Curve.STRAIGHT), new ModelResourceLocation("traincraft:models/tracks/untitled.obj"));

    public Length length;
    public Curve curve;
    public BlockRail rail;
    public ModelResourceLocation modelResourceLocation;
    Tracks(Length length, Curve curve, BlockRail rail, ModelResourceLocation modelResourceLocation){
        this.length = length;
        this.curve = curve;
        this.rail = rail;
        this.modelResourceLocation = modelResourceLocation;
    }

    public IModel getOBJ(){
        try {
            return OBJLoader.instance.loadModel(this.modelResourceLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void preInitBlocks(){
        for(Tracks track : Tracks.values()){
            track.rail.register();
        }
    }

    public enum Length {
        SHORT("short", 1),
        MEDIUM("medium", 3),
        LONG("long", 6);
        public String name;
        public int length;
        Length(String name, int length){
            this.name = name;
            this.length = length;
        }
    }

    public enum Curve{
        STRAIGHT("straight"),
        CURVE_RIGHT("curve_right"),
        CURVE_LEFT("curve_left");
        public String name;
        Curve(String name){
            this.name = name;
        }
    }

    public enum ItemTrackPlacer {
        LONG_STRAIGHT(new ItemTrack(Tracks.LONG_STRAIGHT));

        public ItemTrack track;
        ItemTrackPlacer(ItemTrack track){
            this.track = track;
        }
    }

    public static void preInitItems(){
        for(ItemTrackPlacer track : ItemTrackPlacer.values()){
            track.track.register();
        }
    }

}

