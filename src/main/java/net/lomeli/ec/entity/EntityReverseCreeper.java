package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
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
            Block[][][] blocks = new Block[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
            int[][][] metas = new int[((int) radius) * 2 + 2][((int) radius) * 2 + 2][((int) radius) * 2 + 2];
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
                        Block id = worldObj.getBlock(ex, ey, ez);
                        if (id == Blocks.bedrock)
                            continue;
                        blocks[ax][ay][az] = null;

                        if (id != null && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius && ey > -1) {
                            blocks[ax][ay][az] = id;
                            metas[ax][ay][az] = worldObj.getBlockMetadata(ex, ey, ez);
                            TEs[ax][ay][az] = worldObj.getTileEntity(ex, ey, ez);
                        }
                    }
                }
            }
            for (int x = (int) -radius - 1; x <= radius; x++) {
                for (int y = (int) -radius - 1; y <= radius; y++) {
                    for (int z = (int) -radius - 1; z <= radius; z++) {
                        Block id = blocks[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
                        int meta = metas[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
                        TileEntity TE = TEs[x + (int) radius + 1][(2 * (int) radius) - (y + (int) radius)][z + (int) radius + 1];
                        if (id != null) {
                            if ((int) posY + y <= 0)
                                continue;
                            worldObj.setBlock((int) posX + x, (int) posY + y, (int) posZ + z, id, meta, 3);
                            if (TE != null)
                                worldObj.setTileEntity((int) posX + x, (int) posY + y, (int) posZ + z, TE);
                        }
                    }
                }
            }
        }
    }

}
