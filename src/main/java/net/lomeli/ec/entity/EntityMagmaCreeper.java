package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityMagmaCreeper extends EntityBaseCreeper {

    public EntityMagmaCreeper(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
        this.explosionRadius = ECVars.magmaCreeperRadius;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote) {
            if ((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY) || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F)) {
                if (worldObj.isAirBlock((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ))
                        && Blocks.fire.canPlaceBlockAt(worldObj, (int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ)))
                    worldObj.setBlock((int) (Math.round(prevPosX) + 0.5), (int) (Math.round(prevPosY) + 0.5), (int) (Math.round(prevPosZ) + 0.5), Blocks.fire);
            }
        }
        if (this.isWet())
            this.attackEntityFrom(DamageSource.drown, 1f);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    if (Blocks.lava.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z) && !Blocks.lava.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Blocks.flowing_lava);
                    }
                }
    }

}
