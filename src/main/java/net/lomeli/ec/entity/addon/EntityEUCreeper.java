package net.lomeli.ec.entity.addon;

import ic2.api.energy.tile.IEnergySink;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.lib.ECVars;

public class EntityEUCreeper extends EntityBaseCreeper {

    public EntityEUCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.euCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null) {
                        if (tile instanceof IEnergySink)
                            ((IEnergySink) tile).injectEnergyUnits(null, -1000);
                        else if (tile instanceof TileEntityElectricMachine)
                            ((TileEntityElectricMachine) tile).energy = ((((TileEntityElectricMachine) tile).energy) / 2);
                    }
                }
    }

}
