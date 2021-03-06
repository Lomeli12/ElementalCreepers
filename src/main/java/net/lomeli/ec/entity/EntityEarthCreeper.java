package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntityEarthCreeper extends EntityBaseCreeper {

    public EntityEarthCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.earthCreeperRadius * power) : ModVars.earthCreeperRadius;
        if (ModVars.domeExplosion)
            this.domeExplosion(radius, Blocks.dirt);
        else
            this.wildExplosion(radius, Blocks.dirt);
    }
}
