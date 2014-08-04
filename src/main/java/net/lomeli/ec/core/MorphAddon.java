package net.lomeli.ec.core;

import cpw.mods.fml.common.Loader;

import net.lomeli.ec.entity.*;

import morph.api.Ability;

public class MorphAddon {
    private static Ability hostile, fireImunity, climb, waterAllergy, sunBurn;

    public static void loadAddon() {
        if (Loader.isModLoaded("Morph"))
            registerAbilities();
    }

    private static void registerAbilities() {
        hostile = Ability.createNewAbilityByType("hostile", null);
        fireImunity = Ability.createNewAbilityByType("fireImmunity", null);
        climb = Ability.createNewAbilityByType("climb", null);
        waterAllergy = Ability.createNewAbilityByType("waterAllergy", null);
        sunBurn = Ability.createNewAbilityByType("sunburn", null);
        Ability.mapAbilities(EntityFireCreeper.class, fireImunity, hostile);
        Ability.mapAbilities(EntityMagmaCreeper.class, fireImunity, hostile, waterAllergy);
        Ability.mapAbilities(EntityWaterCreeper.class, Ability.createNewAbilityByType("swim", new String[]{"true"}), hostile);
        Ability.mapAbilities(EntityDarkCreeper.class, sunBurn, hostile);
        Ability.mapAbilities(EntitySpiderCreeper.class, climb, hostile);
        Ability.mapAbilities(EntityWindCreeper.class, Ability.createNewAbilityByType("float", new String[]{"-0.1141748F", "true"}), hostile);
        Ability.mapAbilities(EntityEnderCreeper.class, waterAllergy, hostile);
    }
}
