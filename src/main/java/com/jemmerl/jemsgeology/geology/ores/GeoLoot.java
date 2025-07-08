package com.jemmerl.jemsgeology.geology.ores;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class GeoLoot {

    // TODO there is a *ton* of improvements to be made here. Predicates, more flexibility,
    //  and importantly how to handle mods wanting to change them?


    // Commonly used for stone drops
    public static final GeoLoot BASIC_ROCKS = new GeoLoot(BinomialRange.of(5, 0.65f), false);

    // Commonly used for ore drops
    public static final GeoLoot EMPTY = new GeoLoot(ConstantRange.of(0), false);
    public static final GeoLoot SINGLE_DROP_NO_FORTUNE = new GeoLoot();

    private final IRandomRange itemRange;
    private final boolean affectedByFortune;
    private final FortuneFormula fortuneFormula;
    private final boolean hasPresetDrop;
    private final Supplier<Item> presetDrop;

    public GeoLoot() {
        this(ConstantRange.of(1), false, FortuneFormula.ORE_DROPS, false, ()->Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune) {
        this(itemRange, affectedByFortune, FortuneFormula.ORE_DROPS, false, ()->Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune, FortuneFormula bonusFormula) {
        this(itemRange, affectedByFortune, bonusFormula, false, ()->Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, Supplier<Item> presetDrop) {
        this(itemRange, false, FortuneFormula.ORE_DROPS, true, presetDrop);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune, FortuneFormula fortuneFormula, boolean hasPresetDrop, Supplier<Item> presetDrop) {
        this.itemRange = itemRange;
        this.affectedByFortune = affectedByFortune;
        this.fortuneFormula = fortuneFormula;
        this.hasPresetDrop = hasPresetDrop;
        this.presetDrop = presetDrop;
    }

    public boolean hasPresetDrop() {
        return hasPresetDrop;
    }

    public Supplier<Item> getPresetDrop() {
        return presetDrop;
    }
    public Item getPresetDropItem() {
        return presetDrop.get();
    }

    public boolean isAffectedByFortune() {
        return affectedByFortune;
    }

    public FortuneFormula getFortuneFormula() {
        return fortuneFormula;
    }

    public IRandomRange getItemRange() {
        return itemRange;
    }

    // ApplyBonus
    public enum FortuneFormula {
        BINOMIAL,
        ORE_DROPS,
        UNIFORM;
    }

}
