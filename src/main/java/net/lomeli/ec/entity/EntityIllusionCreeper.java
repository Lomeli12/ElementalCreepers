package net.lomeli.ec.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityIllusionCreeper extends EntityBaseCreeper {
    private boolean split;

    public EntityIllusionCreeper(World par1World) {
        super(par1World);
        split = false;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.isRemote) {
            EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 8F);
            if (!split && player != null && !player.capabilities.isCreativeMode) {
                createFakeCreepersAndLaunchSelf();
                split = true;
            }
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        int exPower = this.explosionRadius * power;
        this.worldObj.createExplosion(this, posX, posY, posZ, (float) exPower, flag);
    }

    private void createFakeCreepersAndLaunchSelf() {
        for (int i = 0; i < 4; i++) {
            EntityFakeIllusionCreeper entity = new EntityFakeIllusionCreeper(worldObj);
            if (entity != null) {
                entity.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
                entity.motionY = 0.5F;
                worldObj.spawnEntityInWorld(entity);
            }
        }
        this.motionY = 0.5F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("split", split);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        split = par1NBTTagCompound.getBoolean("split");
    }
}
