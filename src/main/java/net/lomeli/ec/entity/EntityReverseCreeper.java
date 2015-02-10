package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntityReverseCreeper extends EntityBaseCreeper {

    public EntityReverseCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        if (flag) {
            double radius = getPowered() ? (int) (ECVars.reverseCreeperRadius * 1.5F) : ECVars.reverseCreeperRadius;
            IBlockState[][][] states = new IBlockState[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
            TileEntity[][][] TEs = new TileEntity[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
            for (int x = (int) -radius - 1; x <= radius; x++) {
                for (int y = (int) -radius - 1; y <= radius; y++) {
                    for (int z = (int) -radius - 1; z <= radius; z++) {
                        int ax = x + (int) radius + 1;
                        int ay = y + (int) radius + 1;
                        int az = z + (int) radius + 1;
                        int ex = (int) posX + x;
                        int ey = (int) posY + y;
                        int ez = (int) posZ + z;
                        BlockPos pos = new BlockPos(ex, ey, ez);
                        IBlockState state = worldObj.getBlockState(pos);
                        if (state != null && state.getBlock() != null) {
                            Block id = state.getBlock();
                            if (id == Blocks.bedrock)
                                continue;
                            states[ax][ay][az] = null;

                            if (id != null && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius && ey > -1) {
                                states[ax][ay][az] = state;
                                TEs[ax][ay][az] = worldObj.getTileEntity(pos);
                            }
                        }
                    }
                }
            }
            for (int x = (int) -radius - 1; x <= radius; x++) {
                for (int y = (int) -radius - 1; y <= radius; y++) {
                    for (int z = (int) -radius - 1; z <= radius; z++) {
                        BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                        IBlockState state = states[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
                        TileEntity TE = TEs[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
                        if (state != null && state.getBlock() != null) {
                            if ((int) posY + y <= 0)
                                continue;
                            worldObj.setBlockState(pos, state, 3);
                            if (TE != null)
                                worldObj.setTileEntity(pos, TE);
                        }
                    }
                }
            }
        }
    }

}
