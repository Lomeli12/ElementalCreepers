package net.lomeli.ec.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityIllusionCreeper extends EntityBaseCreeper implements IIllusion{
    private boolean split, illusion;

    public EntityIllusionCreeper(World par1World) {
        super(par1World);
        split = false;
        illusion = false;
        this.explosionSound = true;
    }

    public EntityIllusionCreeper(World par1World, boolean illusion) {
        this(par1World);
        this.illusion = illusion;
        this.explosionSound = !illusion;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!worldObj.isRemote && !illusion) {
            EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 8F);
            if (!split && player != null && !player.capabilities.isCreativeMode) {
                createFakeCreepersAndLaunchSelf();
                split = true;
            }
        }
    }

    @Override
    public void explosion(int power, boolean flag) {
        if (!illusion) {
            int exPower = this.explosionRadius * power;
            
            this.worldObj.createExplosion(this, posX, posY, posZ, exPower, flag);
        }
    }

    private void createFakeCreepersAndLaunchSelf() {
        for (int i = 0; i < 4; i++) {
            EntityIllusionCreeper entity = new EntityIllusionCreeper(worldObj, true);
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
        par1NBTTagCompound.setBoolean("isIllusion", illusion);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        split = par1NBTTagCompound.getBoolean("split");
        illusion = par1NBTTagCompound.getBoolean("isIllusion");
    }

    @Override
    public boolean isIllusion() {
        return this.illusion;
    }
}
