package com.jemmerl.jemsgeology.init.geology.georegistries;

import com.jemmerl.jemsgeology.init.geology.geotypes.GeoType;
import com.jemmerl.jemsgeology.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class HardStoneGeoRegistry extends SoftStoneGeoRegistry {

    private final RegistryObject<Block> rawWall;

    private final RegistryObject<Block> polishedStone;
    private final RegistryObject<Block> polishedSlab;
    private final RegistryObject<Block> polishedStairs;
    private final RegistryObject<Block> polishedWall;

    private final RegistryObject<Block> cobblestone;
    private final RegistryObject<Block> cobbleSlab;
    private final RegistryObject<Block> cobbleStairs;
    private final RegistryObject<Block> cobbleWall;

    private final RegistryObject<Block> mossyCobblestone;
    private final RegistryObject<Block> mossyCobbleSlab;
    private final RegistryObject<Block> mossyCobbleStairs;
    private final RegistryObject<Block> mossyCobbleWall;

    private final RegistryObject<Block> bricks;
    private final RegistryObject<Block> brickSlab;
    private final RegistryObject<Block> brickStairs;
    private final RegistryObject<Block> brickWall;

    private final RegistryObject<Block> mossyBricks;
    private final RegistryObject<Block> mossyBrickSlab;
    private final RegistryObject<Block> mossyBrickStairs;
    private final RegistryObject<Block> mossyBrickWall;

    private final RegistryObject<Block> chiseled;
    private final RegistryObject<Block> cracked;
    private final RegistryObject<Block> pillar;
    private final RegistryObject<Block> button;
    private final RegistryObject<Block> pressureplate;

    public HardStoneGeoRegistry(GeoType geoType) {
        super(geoType);

        cobblestone = ModBlocks.registerCobblestoneBlock(geoType);
        cobbleSlab = ModBlocks.registerCobbleSlab(geoType);
        cobbleStairs = ModBlocks.registerCobbleStairs(geoType,
                () -> cobblestone.get().getDefaultState());
        cobbleWall = ModBlocks.registerCobbleWall(geoType);

        mossyCobblestone = ModBlocks.registerMossyCobblestoneBlock(geoType);
        mossyCobbleSlab = ModBlocks.registerMossyCobbleSlab(geoType);
        mossyCobbleStairs = ModBlocks.registerMossyCobbleStairs(geoType,
                () -> mossyCobblestone.get().getDefaultState());
        mossyCobbleWall = ModBlocks.registerMossyCobbleWall(geoType);
        
        rawWall = ModBlocks.registerRawStoneWall(geoType);

        polishedStone = ModBlocks.registerPolishedStoneBlock(geoType);
        polishedSlab = ModBlocks.registerPolishedSlab(geoType);
        polishedStairs = ModBlocks.registerPolishedStairs(geoType,
                () -> polishedStone.get().getDefaultState());
        polishedWall = ModBlocks.registerPolishedStoneWall(geoType);

        bricks = ModBlocks.registerBricksBlock(geoType);
        brickSlab = ModBlocks.registerBrickSlab(geoType);
        brickStairs = ModBlocks.registerBrickStairs(geoType,
                () -> bricks.get().getDefaultState());
        brickWall = ModBlocks.registerBrickWall(geoType);

        mossyBricks = ModBlocks.registerMossyBricksBlock(geoType);
        mossyBrickSlab = ModBlocks.registerMossyBrickSlab(geoType);
        mossyBrickStairs = ModBlocks.registerMossyBrickStairs(geoType,
                () -> mossyBricks.get().getDefaultState());
        mossyBrickWall = ModBlocks.registerMossyBrickWall(geoType);

        chiseled = ModBlocks.registerChiseledBlock(geoType);
        cracked = ModBlocks.registerCrackedBlock(geoType);
        pillar = ModBlocks.registerPillarBlock(geoType);
        button = ModBlocks.registerButtonBlock(geoType);
        pressureplate = ModBlocks.registerPressurePlateBlock(geoType);
    }

    public Block getRawWall() { return this.rawWall.get(); }
    public Block getCobblestone() { return cobblestone.get(); }
    public Block getCobbleSlab() { return this.cobbleSlab.get(); }
    public Block getCobbleStairs() { return this.cobbleStairs.get(); }
    public Block getCobbleWall() { return this.cobbleWall.get(); }
    public Block getMossyCobblestone() { return mossyCobblestone.get(); }
    public Block getMossyCobbleSlab() { return this.mossyCobbleSlab.get(); }
    public Block getMossyCobbleStairs() { return this.mossyCobbleStairs.get(); }
    public Block getMossyCobbleWall() { return this.mossyCobbleWall.get(); }
    public Block getPolishedStone() { return this.polishedStone.get(); }
    public Block getPolishedSlab() { return this.polishedSlab.get(); }
    public Block getPolishedStairs() { return this.polishedStairs.get(); }
    public Block getPolishedWall() { return this.polishedWall.get(); }
    public Block getBricks() { return this.bricks.get(); }
    public Block getBrickSlab() { return this.brickSlab.get(); }
    public Block getBrickStairs() { return this.brickStairs.get(); }
    public Block getBrickWall() { return this.brickWall.get(); }
    public Block getMossyBricks() { return this.mossyBricks.get(); }
    public Block getMossyBrickSlab() { return this.mossyBrickSlab.get(); }
    public Block getMossyBrickStairs() { return this.mossyBrickStairs.get(); }
    public Block getMossyBrickWall() { return this.mossyBrickWall.get(); }

    public Block getChiseled() { return this.chiseled.get(); }
    public Block getCracked() { return this.cracked.get(); }
    public Block getPillar() { return this.pillar.get(); }
    public Block getButton() { return this.button.get(); }
    public Block getPressurePlate() { return this.pressureplate.get(); }

    public List<Block> getDecorBlocks() {
        List<Block> allDecorBlocks = new ArrayList<>();
        allDecorBlocks.add(getRawSlab());
        allDecorBlocks.add(getRawStairs());
        allDecorBlocks.add(rawWall.get());
        allDecorBlocks.add(cobbleSlab.get());
        allDecorBlocks.add(cobbleStairs.get());
        allDecorBlocks.add(cobbleWall.get());
        allDecorBlocks.add(mossyCobblestone.get());
        allDecorBlocks.add(mossyCobbleSlab.get());
        allDecorBlocks.add(mossyCobbleStairs.get());
        allDecorBlocks.add(mossyCobbleWall.get());
        allDecorBlocks.add(polishedStone.get());
        allDecorBlocks.add(polishedSlab.get());
        allDecorBlocks.add(polishedStairs.get());
        allDecorBlocks.add(polishedWall.get());
        allDecorBlocks.add(bricks.get());
        allDecorBlocks.add(brickSlab.get());
        allDecorBlocks.add(brickStairs.get());
        allDecorBlocks.add(brickWall.get());
        allDecorBlocks.add(mossyBricks.get());
        allDecorBlocks.add(mossyBrickSlab.get());
        allDecorBlocks.add(mossyBrickStairs.get());
        allDecorBlocks.add(mossyBrickWall.get());
        allDecorBlocks.add(chiseled.get());
        allDecorBlocks.add(cracked.get());
        allDecorBlocks.add(pillar.get());
        allDecorBlocks.add(button.get());
        allDecorBlocks.add(pressureplate.get());

        return allDecorBlocks;
    }
}
