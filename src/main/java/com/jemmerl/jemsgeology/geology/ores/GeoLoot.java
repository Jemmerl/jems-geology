package com.jemmerl.jemsgeology.geology.ores;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.BinomialRange;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;

public class GeoLoot {

    // Commonly used for stone drops
    public static final GeoLoot BASIC_ROCKS = new GeoLoot(BinomialRange.of(3, 0.65f), false);

    // Commonly used for ore drops
    public static final GeoLoot EMPTY = new GeoLoot(ConstantRange.of(0), false);
    public static final GeoLoot SINGLE_DROP_NO_FORTUNE = new GeoLoot();

    private final IRandomRange itemRange;
    private final boolean affectedByFortune;
    private final FortuneFormula fortuneFormula;
    private final boolean hasPresetDrop;
    private final Item presetDrop;

    public GeoLoot() {
        this(ConstantRange.of(1), false, FortuneFormula.ORE_DROPS, false, Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune) {
        this(itemRange, affectedByFortune, FortuneFormula.ORE_DROPS, false, Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune, FortuneFormula bonusFormula) {
        this(itemRange, affectedByFortune, bonusFormula, false, Items.AIR);
    }

    public GeoLoot(IRandomRange itemRange, Item presetDrop) {
        this(itemRange, false, FortuneFormula.ORE_DROPS, true, presetDrop);
    }

    public GeoLoot(IRandomRange itemRange, boolean affectedByFortune, FortuneFormula fortuneFormula, boolean hasPresetDrop, Item presetDrop) {
        this.itemRange = itemRange;
        this.affectedByFortune = affectedByFortune;
        this.fortuneFormula = fortuneFormula;
        this.hasPresetDrop = hasPresetDrop;
        this.presetDrop = presetDrop;
    }

    public boolean hasPresetDrop() {
        return hasPresetDrop;
    }

    public Item getPresetDrop() {
        return presetDrop;
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
