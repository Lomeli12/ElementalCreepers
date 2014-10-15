package net.lomeli.ec.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.ec.entity.ai.EntityAIFriendlyCreeperSwell;
import net.lomeli.ec.entity.explosion.ExplosionFriendly;

public class EntityFriendlyCreeper extends EntityTameable {
    protected int fuseTime = 30;
    protected int explosionRadius = 3;
    protected int lastActiveTime;
    protected int timeSinceIgnited;
    protected int coolDownTime;
    private float field_70926_e;
    private float field_70924_f;

    public EntityFriendlyCreeper(World world) {
        super(world);
        this.setSize(0.75F, 2.0F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIFriendlyCreeperSwell(this));
        this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.setTamed(false);
    }

    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.isIgnited())
                this.setCreeperState(1);

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
                this.playSound("creeper.primed", 1.0F, 0.5F);

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
                this.timeSinceIgnited = 0;

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;
                if (!this.worldObj.isRemote) {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                    this.doFriendlyExplosion((float) this.explosionRadius * (this.getPowered() ? 2 : 1), flag);
                }

                this.timeSinceIgnited = 0;
                this.lastActiveTime = 0;
                this.setCreeperState(-1);
            }
        }
        if (isSitting()) this.rotationPitch = 45.0F;
        super.onUpdate();

        this.field_70924_f = this.field_70926_e;

        if (this.func_70922_bv())
            this.field_70926_e += (1.0F - this.field_70926_e) * 0.4F;
        else
            this.field_70926_e += (0.0F - this.field_70926_e) * 0.4F;

        if (this.func_70922_bv())
            this.numTicksToChaseTarget = 10;
    }

    public void doFriendlyExplosion(float explodePower, boolean flag) {
        if (!this.isTamed()) {
            worldObj.createExplosion(this, this.posX, this.posY, this.posZ, explodePower, flag);
            this.setDead();
        } else
            createFriendlyExplosion(this.posX, this.posY, this.posZ, explodePower);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);

        if (this.isTamed())
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    public void setAttackTarget(EntityLivingBase p_70624_1_) {
        super.setAttackTarget(p_70624_1_);

        if (p_70624_1_ == null)
            this.setAngry(false);
        else if (!this.isTamed())
            this.setAngry(true);
    }

    public boolean isAngry() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 2) != 0;
    }

    public void setAngry(boolean p_70916_1_) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70916_1_)
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 | 2)));
        else
            this.dataWatcher.updateObject(16, Byte.valueOf((byte) (b0 & -3)));
    }

    public int getMaxSpawnedInChunk() {
        return 8;
    }

    protected void updateAITick() {
        this.dataWatcher.updateObject(18, Float.valueOf(this.getHealth()));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, Byte.valueOf((byte) -1));
        this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
        this.dataWatcher.addObject(18, new Float(this.getHealth()));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        EntityFriendlyCreeper entity = new EntityFriendlyCreeper(this.worldObj);
        String s = this.func_152113_b();
        if (s != null && s.trim().length() > 0) {
            entity.func_152115_b(s);
            entity.setTamed(true);
        }

        return entity;
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    @Override
    protected String getHurtSound() {
        return "mob.creeper.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.creeper.death";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }

    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setBoolean("Angry", this.isAngry());
        if (this.dataWatcher.getWatchableObjectByte(21) == 1)
            nbt.setBoolean("powered", true);

        nbt.setShort("Fuse", (short) this.fuseTime);
        nbt.setByte("ExplosionRadius", (byte) this.explosionRadius);
        nbt.setBoolean("ignited", this.isIgnited());
    }

    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setAngry(nbt.getBoolean("Angry"));
        this.dataWatcher.updateObject(21, Byte.valueOf((byte) (nbt.getBoolean("powered") ? 1 : 0)));

        if (nbt.hasKey("Fuse"))
            this.fuseTime = nbt.getShort("Fuse");

        if (nbt.hasKey("ExplosionRadius"))
            this.explosionRadius = nbt.getByte("ExplosionRadius");

        if (nbt.hasKey("ignited") && nbt.getBoolean("ignited"))
            this.setIgnited();
    }

    public boolean getPowered() {
        return this.dataWatcher.getWatchableObjectByte(21) == 1;
    }

    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float par1) {
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * par1) / (float) (this.fuseTime - 2);
    }

    public int getCreeperState() {
        return this.dataWatcher.getWatchableObjectByte(20);
    }

    public void setCreeperState(int par1) {
        this.dataWatcher.updateObject(20, Byte.valueOf((byte) par1));
    }

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
        super.onStruckByLightning(par1EntityLightningBolt);
        this.dataWatcher.updateObject(21, Byte.valueOf((byte) 1));
    }

    public void onDeath(DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);

        if (par1DamageSource.getEntity() instanceof EntitySkeleton) {
            Item[] records = new Item[]{Items.record_11, Items.record_13, Items.record_blocks, Items.record_cat, Items.record_chirp, Items.record_far, Items.record_mall, Items.record_mellohi,
                    Items.record_stal, Items.record_strad, Items.record_wait, Items.record_ward};
            this.dropItem(records[rand.nextInt(records.length)], 1);
        }
    }

    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
        if (this.isEntityInvulnerable())
            return false;
        else {
            Entity entity = par1DamageSource.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
                par2 = (par2 + 1) / 2;

            return super.attackEntityFrom(par1DamageSource, par2);
        }
    }

    public ExplosionFriendly createFriendlyExplosion(double x, double y, double z, float strength) {
        ExplosionFriendly explosion = new ExplosionFriendly(worldObj, this, x, y, z, strength);
        explosion.isFlaming = false;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
        return explosion;
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        int i = this.isTamed() ? 4 : 2;
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
    }

    public void setTamed(boolean p_70903_1_) {
        super.setTamed(p_70903_1_);

        if (p_70903_1_)
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        else
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }

    public void setOwnerByID(String p_152115_1_) {
        this.dataWatcher.updateObject(17, p_152115_1_);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        if (stack != null && stack.getItem() != null) {
            if (stack.getItem() instanceof ItemBlock)
                return Block.getBlockFromItem(stack.getItem()) instanceof BlockFlower;
        }
        return false;
    }

    @Override
    public boolean canMateWith(EntityAnimal par1EntityAnimal) {
        if (par1EntityAnimal == this)
            return false;
        else if (!this.isTamed())
            return false;
        else if (!(par1EntityAnimal instanceof EntityFriendlyCreeper))
            return false;
        else {
            EntityFriendlyCreeper entityCreeper = (EntityFriendlyCreeper) par1EntityAnimal;
            return !entityCreeper.isTamed() ? false : (entityCreeper.isSitting() ? false : this.isInLove() && entityCreeper.isInLove());
        }
    }

    public boolean func_70922_bv() {
        return this.dataWatcher.getWatchableObjectByte(19) == 1;
    }

    public boolean isOwner(EntityLivingBase entityLivingBase) {
        return entityLivingBase == this.getOwner();
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (this.isTamed()) {
            if (stack != null && stack.getItem() != null) {
                if (stack.getItem() instanceof ItemFood) {
                    ItemFood itemfood = (ItemFood) stack.getItem();
                    if (this.getHealth() < 20) {
                        if (!player.capabilities.isCreativeMode)
                            --stack.stackSize;

                        this.heal(itemfood.func_150906_h(stack));

                        if (stack.stackSize <= 0)
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

                        return true;
                    }
                } else if (stack.getItem() instanceof ItemArmor) {
                    int slot = ((ItemArmor) stack.getItem()).armorType + 1;
                    if (slot >= 1 || slot <= 4) {
                        if (this.getEquipmentInSlot(slot) != null) {
                            this.setCurrentItemOrArmor(slot, stack);
                            return true;
                        }
                    }
                }
            }


            if (isOwner(player) && !worldObj.isRemote && !isBreedingItem(stack)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity(null);
                this.setTarget(null);
                this.setAttackTarget(null);
            }
        } else {
            if (stack != null && stack.getItem() != null) {
                if (stack.getItem() == Items.flint_and_steel) {
                    this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
                    player.swingItem();
                    if (!worldObj.isRemote) {
                        this.setIgnited();
                        stack.damageItem(1, player);
                    }
                    return true;
                } else if (stack.getItem() == Items.gunpowder && !this.isAngry()) {
                    if (!player.capabilities.isCreativeMode)
                        --stack.stackSize;
                    if (stack.stackSize <= 0)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    if (!worldObj.isRemote) {
                        if (this.rand.nextInt(3) == 0) {
                            this.setTamed(true);
                            this.setPathToEntity(null);
                            this.setAttackTarget(null);
                            this.aiSit.setSitting(true);
                            this.setHealth(20.0F);
                            this.setOwnerByID(player.getUniqueID().toString());
                            this.playTameEffect(true);
                            this.worldObj.setEntityState(this, (byte) 7);
                        } else {
                            this.playTameEffect(false);
                            this.worldObj.setEntityState(this, (byte) 6);
                        }
                    }
                }
            }
        }
        return super.interact(player);
    }

    public boolean isIgnited() {
        return this.dataWatcher.getWatchableObjectByte(22) != 0;
    }

    public void setIgnited() {
        this.dataWatcher.updateObject(22, (byte) 1);
    }

    @SideOnly(Side.CLIENT)
    public String tamedTexture() {
        return this.isTamed() ? "textures/entities/friendlycreeper1.png" : "textures/entities/friendlycreeper0.png";
    }
}
