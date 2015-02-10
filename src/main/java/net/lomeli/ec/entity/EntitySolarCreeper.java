package net.lomeli.ec.entity;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntitySolarCreeper extends EntityBaseCreeper {

    public EntitySolarCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        this.explosionRadius = 3;
        if (!this.worldObj.isRemote) {
            if (this.worldObj.isDaytime()) {

                if (this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 3), MathHelper.floor_double(this.posZ)))) {
                    int i1 = this.worldObj.getLightFor(EnumSkyBlock.SKY, new BlockPos((int) this.posX, (int) this.posY + 3, (int) this.posZ)) - this.worldObj.getSkylightSubtracted();
                    float j = this.worldObj.getCelestialAngleRadians(1.0F);

                    if (j < (float) Math.PI)
                        j += (0.0F - j) * 0.2F;
                    else
                        j += (((float) Math.PI * 2F) - j) * 0.2F;

                    i1 = Math.round(i1 * MathHelper.cos(j));

                    if (i1 < 0)
                        i1 = 0;
                    if (i1 > 15)
                        i1 = 15;
                    this.explosionRadius = 3 + (i1 / 3);
                }
            } else
                this.explosionRadius--;
            if (this.worldObj.isRaining() || this.worldObj.isThundering())
                this.explosionRadius--;
        }

        int radius = this.explosionRadius * power;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, flag);

        if (this.getPowered() ? radius == 16 : radius == 8) {
            for (int x = -radius; x <= radius; x++)
                for (int y = -radius; y <= radius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                        if (Blocks.dirt.canPlaceBlockAt(worldObj, pos) && !Blocks.dirt.canPlaceBlockAt(worldObj, new BlockPos((int) posX + x, (int) posY + y - 1, (int) posZ + z))) {
                            if (rand.nextBoolean())
                                worldObj.setBlockState(pos, Blocks.fire.getDefaultState());
                        }
                    }
        }
    }

    /*
    @Override
    protected boolean isValidLightLevel() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int l = this.worldObj.getLight(pos);

            if (this.worldObj.isThundering()) {
                int i1 = this.worldObj.getSkylightSubtracted();
                //this.worldObj.setSkylightSubtracted(10);
                l = this.worldObj.getLight(pos);
                //this.worldObj.setSkylightSubtracted(i1);
            }
            int f = this.rand.nextInt(8);
            if (l > f) {
                if (this.worldObj.isDaytime() && this.worldObj.canSeeSky(pos) && this.rand.nextInt(1000) <= 5)
                    return true;
            }
            return l <= f;
        }
    }*/
}
