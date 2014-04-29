package net.lomeli.ec.entity.addon;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
                            // ((IEnergyHandler) tile).extractEnergy(null,
                            // ((IEnergyHandler) tile).getEnergyStored(null) /
                            // 2, false);
                        }
                    }
                }
    }

}
