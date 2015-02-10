package net.lomeli.ec.entity;

import java.util.Collections;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityDarkCreeper extends EntityBaseCreeper {

    public EntityDarkCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
            float f = this.getBrightness(1.0F);
            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
                    && this.worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))) && !this.getPowered())
                this.setFire(8);
        }
        super.onLivingUpdate();
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ECVars.darkCreeperRadius * power) : ECVars.darkCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                    IBlockState state = worldObj.getBlockState(pos);
                    if (state != null && state.getBlock() != null) {
                        Block block = state.getBlock();
                        if (block != null && block.getLightValue() > 0.5F) {
                            block.dropBlockAsItem(worldObj, pos, state, 0);
                            worldObj.setBlockToAir(pos);
                            block.onBlockDestroyedByExplosion(worldObj, pos, new Explosion(worldObj, this, 0d, 0d, 0d, 0f, Collections.emptyList()));
                        }
                    }
                }
    }

}
