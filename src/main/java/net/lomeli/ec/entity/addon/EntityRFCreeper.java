package net.lomeli.ec.entity.addon;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.lib.ECVars;

import cofh.api.energy.IEnergyHandler;

public class EntityRFCreeper extends EntityBaseCreeper {

    public EntityRFCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.rfCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    TileEntity tile = worldObj.getTileEntity((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (tile != null) {
                        if (tile instanceof IEnergyHandler) {
                            if (worldObj.rand.nextInt(1000) < 25) {
                                ItemStack itemStack = new ItemStack(worldObj.getBlock((int) posX + x, (int) posY + y, (int) posZ + z), 1, worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z));
                                if (itemStack != null) {
                                    EntityItem item = new EntityItem(worldObj, posX + x, posY + y, posZ + z, itemStack);
                                    if (!worldObj.isRemote)
                                        worldObj.spawnEntityInWorld(item);
                                    worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                                }
                            } else {
                                int stored = 0;
                                for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                                    stored = ((IEnergyHandler) tile).getEnergyStored(dir);
                                    if (stored > 0)
                                        break;
                                }
                                ForgeDirection validDir = null;
                                for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                                    if (((IEnergyHandler) tile).extractEnergy(dir, stored, false) > 0) {
                                        validDir = dir;
                                        break;
                                    }
                                }
                                if (validDir != null) {
                                    for (int i = 0; i < 10; i++) {
                                        ((IEnergyHandler) tile).extractEnergy(validDir, stored, false);
                                    }
                                }
                            }
                        }
                    }
                }
    }

}
