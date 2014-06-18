package net.lomeli.ec.entity.addon;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.FakePlayer;

import net.lomeli.ec.entity.EntityBaseCreeper;
import net.lomeli.ec.lib.ECVars;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.wiring.TileEntityElectricBlock;

public class EntityEUCreeper extends EntityBaseCreeper {
    private String uuid;
    private FakePlayer fakePlayer;

    public EntityEUCreeper(World par1World) {
        super(par1World);
        this.uuid = UUID.randomUUID().toString();
        this.fakePlayer = new FakePlayer(MinecraftServer.getServer().worldServerForDimension(this.dimension), new GameProfile(uuid, "[FakeEC"));
        this.explosionRadius = ECVars.euCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    TileEntity tile = worldObj.getTileEntity((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (tile != null) {
                        if (tile instanceof TileEntityElectricBlock) {
                            ((TileEntityElectricBlock) tile).drawEnergy(15000 * ((TileEntityElectricBlock) tile).getMaxSafeInput());
                            if (((TileEntityElectricBlock) tile).getStored() < 0)
                                ((TileEntityElectricBlock) tile).setStored(0);
                        }else if (tile instanceof TileEntityElectricMachine)
                            ((TileEntityElectricMachine) tile).energy = ((((TileEntityElectricMachine) tile).energy) / 2);
                        else if (tile instanceof TileEntityBaseGenerator) {
                            ItemStack stack = ((TileEntityBaseGenerator) tile).getWrenchDrop(fakePlayer);
                            if (stack != null) {
                                worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                                worldObj.spawnEntityInWorld(new EntityItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, stack));
                            }
                        }
                    }
                }
    }

}
