package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntityIceCreeper extends EntityBaseCreeper {

    public EntityIceCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!worldObj.isRemote) {
            for (int i = 0; i < 4; i++) {
                if ((int) Math.round(posX + 0.5F) != (int) Math.round(prevPosX + 0.5F) || (int) Math.round(posY) != (int) Math.round(prevPosY)
                        || (int) Math.round(posZ + 0.5F) != (int) Math.round(prevPosZ + 0.5F)) {
                    BlockPos pos = new BlockPos((int) Math.round(prevPosX), (int) Math.round(prevPosY), (int) Math.round(prevPosZ));
                    if (worldObj.isAirBlock(pos) && Blocks.snow_layer.canPlaceBlockAt(worldObj, pos))
                        worldObj.setBlockState(pos, Blocks.snow_layer.getDefaultState());
                }
            }
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.iceCreeperRadius * power) : ModVars.iceCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                    IBlockState state = worldObj.getBlockState(pos);
                    if (state != null && state.getBlock() != null) {
                        Block block = state.getBlock();
                        if (block == Blocks.water || block == Blocks.flowing_water)
                            worldObj.setBlockState(pos, Blocks.ice.getDefaultState());
                        else if (block == Blocks.lava || block == Blocks.flowing_lava)
                            worldObj.setBlockState(pos, Blocks.obsidian.getDefaultState());
                    }
                }
        if (ModVars.domeExplosion)
            this.domeExplosion(radius, Blocks.snow);
        else {
            for (int x = -radius; x <= radius; x++)
                for (int y = -radius; y <= radius; y++)
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                        if (Blocks.dirt.canPlaceBlockAt(worldObj, pos) && !Blocks.dirt.canPlaceBlockAt(worldObj, new BlockPos((int) posX + x, (int) posY + y - 1, (int) posZ + z))) {
                            if (rand.nextBoolean())
                                worldObj.setBlockState(pos, Blocks.snow_layer.getDefaultState());
                            else
                                worldObj.setBlockState(pos, Blocks.snow.getDefaultState());
                        }
                    }
        }
    }
}
