package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.Traincraft;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public enum Tracks {

    MEDIUM_CURVE(Length.MEDIUM, Curve.CURVE, "mediumCurve"),
    SHORT_STRAIGHT(Length.LONG, Curve.STRAIGHT, "shortStraight");

    public Length length;
    public Curve curve;
    public String name;
    Tracks(Length length, Curve curve, String name){
        this.length = length;
        this.curve = curve;
        this.name = name;
    }

    public ResourceLocation getModel(){
        return new ResourceLocation(Traincraft.MODID, "models/tracks/" + name + ".obj");
    }
    public ResourceLocation getTexture(){
        if(this.curve != Curve.SLOPE){
            return new ResourceLocation(Traincraft.MODID, "textures/blocks/tracks/trackNormal.png");
        }
        return new ResourceLocation(Traincraft.MODID, "textures/blocks/tracks/" + name + ".png");
    }

    public enum Length {
        SHORT("short", 1),
        MEDIUM("medium", 3),
        LONG("long", 6),
        VERY_LONG("very_long", 9);
        public String name;
        public int length;
        Length(String name, int length){
            this.name = name;
            this.length = length;
        }
    }

    public enum Curve{
        STRAIGHT("straight"),
        CURVE("curve"),
        SWITCH("switch"),
        SLOPE("slope");
        public String name;
        Curve(String name){
            this.name = name;
        }
    }

}

