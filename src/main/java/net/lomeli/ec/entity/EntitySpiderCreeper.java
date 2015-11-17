package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntitySpiderCreeper extends EntityBaseCreeper {

    public EntitySpiderCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        float radius = getPowered() ? (ModVars.spiderCreeperRadius * power) : ModVars.spiderCreeperRadius;
        for (float x = -radius; x <= radius; x++)
            for (float y = -radius; y <= radius; y++)
                for (float z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) (posX + x), (int) (posY + y), (int) (posZ + z));
                    if (rand.nextInt(100) < 2 && worldObj.isAirBlock(pos))
                        worldObj.setBlockState(pos, Blocks.web.getDefaultState());
                }

        @SuppressWarnings("unchecked")
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(radius, radius, radius));

        byte difficulty = 0;
        if (this.worldObj.getDifficulty().getDifficultyId() == 1)
            difficulty = 7;
        else if (this.worldObj.getDifficulty().getDifficultyId() == 2)
            difficulty = 10;
        else if (this.worldObj.getDifficulty().getDifficultyId() == 3)
            difficulty = 15;

        if (difficulty > 0) {
            for (Entity entity : list) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entityLiving = (EntityLivingBase) entity;
                    if (entityLiving.getDistanceToEntity(this) < 30)
                        entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, difficulty * 40, 0));
                }
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, new Byte((byte) 0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote)
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(20);

        if (par1)
            b0 = (byte) (b0 | 1);
        else
            b0 &= -2;

        this.dataWatcher.updateObject(20, Byte.valueOf(b0));
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
        return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
    }
}
