package net.lomeli.ec.entity;

import java.util.Calendar;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.lomeli.ec.ElementalCreepers;

public class EntityBirthdayCreeper extends EntityBaseCreeper {
    private boolean spawnCake;

    public EntityBirthdayCreeper(World world) {
        super(world);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if (!(month == Calendar.NOVEMBER && day == 12) || !(month == Calendar.MAY && day == 10))
            spawnCake = true;
        else
            spawnCake = false;
    }

    @Override
    public void explosion(int power, boolean flag) {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);
        if (spawnCake || this.rand.nextInt(5) <= 2) {
            if (Blocks.cake.canPlaceBlockAt(worldObj, x, y, z))
                worldObj.setBlock(x, y, z, Blocks.cake);
            if (Blocks.torch.canPlaceBlockAt(worldObj, x + 1, y, z))
                worldObj.setBlock(x + 1, y, z, Blocks.torch);
            if (Blocks.torch.canPlaceBlockAt(worldObj, x - 1, y, z))
                worldObj.setBlock(x - 1, y, z, Blocks.torch);
            if (Blocks.torch.canPlaceBlockAt(worldObj, x, y, z + 1))
                worldObj.setBlock(x, y, z + 1, Blocks.torch);
            if (Blocks.torch.canPlaceBlockAt(worldObj, x, y, z - 1))
                worldObj.setBlock(x, y, z - 1, Blocks.torch);
        }
        if (worldObj.isRemote) {
            for (int i = 0; i < 16; ++i)
                ElementalCreepers.proxy.spawnPortalParticle(worldObj, posX, posY, posZ, worldObj.rand.nextFloat(), worldObj.rand.nextFloat(), worldObj.rand.nextFloat());
        }
    }
}
