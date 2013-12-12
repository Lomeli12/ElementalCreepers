package net.lomeli.ec.core;

import net.lomeli.ec.entity.EntityFireCreeper;
import net.lomeli.ec.entity.EntityMagmaCreeper;
import net.lomeli.ec.entity.EntityWaterCreeper;

import net.lomeli.lomlib.util.ModLoaded;

import morph.api.Ability;

public class MorphAddon {
    public static void loadAddon() {
        if (ModLoaded.isModInstalled("Morph"))
            registerAbilities();
    }

    private static void registerAbilities() {
        Ability.mapAbilities(EntityFireCreeper.class, Ability.getNewAbilityFireImmunity(), Ability.getNewAbilityHostile());
        Ability.mapAbilities(EntityMagmaCreeper.class, Ability.getNewAbilityFireImmunity(), Ability.getNewAbilityHostile());
        Ability.mapAbilities(EntityWaterCreeper.class, Ability.getNewAbilitySwim(true), Ability.getNewAbilityHostile());
    }
}
