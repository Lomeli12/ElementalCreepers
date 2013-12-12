package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityIceCreeper extends EntityBaseCreeper {

    public EntityIceCreeper(World par1World) {
        super(par1World);
        this.isImmuneToFire = true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posZ);
        for (i = 0; i < 4; i++) {

            j = MathHelper.floor_double(this.posX + (double) ((float) (i % 2 * 2 - 1) * 0.25F));
            int k = MathHelper.floor_double(this.posY);
            int l = MathHelper.floor_double(this.posZ + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25F));

            if (this.worldObj.getBlockId(j, k, l) == 0 && this.worldObj.getBiomeGenForCoords(j, l).getFloatTemperature() < 0.8F
                    && Block.snow.canPlaceBlockAt(this.worldObj, j, k, l))
                this.worldObj.setBlock(j, k, l, Block.snow.blockID);
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    int id = worldObj.getBlockId((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (id == Block.waterStill.blockID || id == Block.waterMoving.blockID)
                        worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.ice.blockID);

                    if (Block.snow.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z)
                            && !Block.snow.canPlaceBlockAt(worldObj, (int) posX + x, (int) posY + y - 1, (int) posZ + z)) {
                        if (rand.nextBoolean())
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, Block.snow.blockID);
                    }
                }
    }

}
