package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityDarkCreeper extends EntityBaseCreeper {

    public EntityDarkCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.darkCreeperRadius;
    }

    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
            float f = this.getBrightness(1.0F);

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
                    && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && !this.getPowered())
                this.setFire(8);
        }
        super.onLivingUpdate();
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    Block id = worldObj.getBlock((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (id != null && id.getLightValue() > 0.5F) {
                        id.dropBlockAsItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z), 0);
                        worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                        id.onBlockDestroyedByExplosion(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, new Explosion(worldObj, this, 0.0D, 0.0D, 0.0D, 0.0F));
                    }
                }
    }

}
