package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityWaterCreeper extends EntityBaseCreeper {

    public EntityWaterCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ECVars.waterCreeperRadius * power) : ECVars.waterCreeperRadius;
        if (ECVars.domeExplosion)
            this.domeExplosion(radius, Blocks.water);
        else
            this.wildExplosion(radius, Blocks.water);
    }
}
