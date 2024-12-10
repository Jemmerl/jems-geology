package com.jemmerl.jemsgeology.geology.ores;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.IRandomRange;

public class OreLoot {

    private final IRandomRange oreRange;
    private final boolean affectedByFortune;
    private final FortuneFormula fortuneFormula;
    private final boolean hasPresetOre;
    private final Item presetOreItem;

    public OreLoot() {
        this(ConstantRange.of(1), false, FortuneFormula.ORE_DROPS, false, Items.AIR);
    }

    public OreLoot(IRandomRange oreRange, boolean affectedByFortune) {
        this(oreRange, affectedByFortune, FortuneFormula.ORE_DROPS, false, Items.AIR);
    }

    public OreLoot(IRandomRange oreRange, boolean affectedByFortune, FortuneFormula bonusFormula) {
        this(oreRange, affectedByFortune, bonusFormula, false, Items.AIR);
    }

    public OreLoot(IRandomRange oreRange, boolean affectedByFortune, FortuneFormula fortuneFormula, boolean hasPresetOre, Item presetOreItem) {
        this.oreRange = oreRange;
        this.affectedByFortune = affectedByFortune;
        this.fortuneFormula = fortuneFormula;
        this.hasPresetOre = hasPresetOre;
        this.presetOreItem = presetOreItem;
    }

    public boolean hasPresetOre() {
        return hasPresetOre;
    }

    public Item getPresetOreItem() {
        return presetOreItem;
    }

    public boolean isAffectedByFortune() {
        return affectedByFortune;
    }

    public FortuneFormula getFortuneFormula() {
        return fortuneFormula;
    }

    public IRandomRange getOreRange() {
        return oreRange;
    }

    // ApplyBonus
    public enum FortuneFormula {
        BINOMIAL,
        ORE_DROPS,
        UNIFORM;
    }

}
