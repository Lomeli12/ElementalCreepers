package net.lomeli.ec.entity.addon;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import net.minecraftforge.common.util.FakePlayer;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.lib.ECVars;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.IC2Items;
import ic2.api.tile.IEnergyStorage;
import ic2.api.tile.IWrenchable;

public class EntityEUCreeper extends EntityBaseCreeper {

    public EntityEUCreeper(World world) {
        super(world);
        this.explosionRadius = ECVars.euCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        FakePlayer fakePlayer = ElementalCreepers.proxy.getEcPlayer((WorldServer) this.worldObj);
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    TileEntity tile = worldObj.getTileEntity((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (tile != null) {
                        String electricMachine = "ic2.core.block.machine.tileentity.TileEntityElectricMachine";
                        try {
                            if (Class.forName(electricMachine).isInstance(tile)) {
                                int energy = Class.forName(electricMachine).getDeclaredField("energy").getInt(tile);
                                Class.forName(electricMachine).getDeclaredField("energy").setInt(tile, (energy / 2));
                            }
                            if (tile instanceof IEnergyStorage) {
                                if (tile instanceof IEnergySource && tile instanceof IEnergySink) {
                                    ((IEnergySource) tile).drawEnergy(15000);
                                    if (((IEnergyStorage) tile).getStored() < 0)
                                        ((IEnergyStorage) tile).setStored(0);
                                }
                            } else if (Class.forName("ic2.core.block.wiring.TileEntityCable").isInstance(tile)) {
                                if (worldObj.rand.nextInt(100) < 35) {
                                    ItemStack cable = new ItemStack(IC2Items.getItem("copperCableItem").getItem(), 1, worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z));
                                    if (cable != null) {
                                        worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, cable));
                                    }
                                }
                            } else if (tile instanceof IWrenchable) {
                                if (worldObj.rand.nextInt(100) < 35) {
                                    ItemStack stack = ((IWrenchable) tile).getWrenchDrop(fakePlayer);
                                    if (stack != null) {
                                        worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, stack));
                                    }
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                }
    }

}
