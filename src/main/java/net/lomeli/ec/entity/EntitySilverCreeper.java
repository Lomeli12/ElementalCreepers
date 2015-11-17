package net.lomeli.ec.entity;

import java.util.Random;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.core.block.BlockSilverCreeper;
import net.lomeli.ec.lib.ModVars;

public class EntitySilverCreeper extends EntityBaseCreeper {

    public EntitySilverCreeper(World world) {
        super(world);
        this.tasks.addTask(7, new EntitySilverCreeper.AIHideInStone());
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.silverCreeperRadius * power) : ModVars.silverCreeperRadius;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, radius, false);

        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                    IBlockState state = worldObj.getBlockState(pos);
                    if (state != null && state.getBlock() != null && BlockSilverfish.canContainSilverfish(state) && rand.nextInt(100) < 25)
                        this.worldObj.setBlockState(pos, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT, BlockSilverfish.EnumType.forModelBlock(state)), 3);
                }
    }

    class AIHideInStone extends EntityAIWander {
        private EnumFacing face;
        private boolean replaceBlock;

        public AIHideInStone() {
            super(EntitySilverCreeper.this, 1.0D, 10);
            this.setMutexBits(1);
        }

        public boolean shouldExecute() {
            if (EntitySilverCreeper.this.getAttackTarget() != null)
                return false;
            else if (!EntitySilverCreeper.this.getNavigator().noPath())
                return false;
            else {
                Random random = EntitySilverCreeper.this.getRNG();

                if (random.nextInt(10) == 0) {
                    this.face = EnumFacing.random(random);
                    BlockPos blockpos = (new BlockPos(EntitySilverCreeper.this.posX, EntitySilverCreeper.this.posY + 0.5D, EntitySilverCreeper.this.posZ)).offset(this.face);
                    IBlockState iblockstate = EntitySilverCreeper.this.worldObj.getBlockState(blockpos);

                    if (BlockSilverfish.canContainSilverfish(iblockstate)) {
                        this.replaceBlock = true;
                        return true;
                    }
                }

                this.replaceBlock = false;
                return super.shouldExecute();
            }
        }

        public boolean continueExecuting() {
            return this.replaceBlock ? false : super.continueExecuting();
        }

        public void startExecuting() {
            if (!this.replaceBlock)
                super.startExecuting();
            else {
                World world = EntitySilverCreeper.this.worldObj;
                BlockPos blockpos = (new BlockPos(EntitySilverCreeper.this.posX, EntitySilverCreeper.this.posY + 0.5D, EntitySilverCreeper.this.posZ)).offset(this.face);
                IBlockState iblockstate = world.getBlockState(blockpos);

                if (BlockSilverfish.canContainSilverfish(iblockstate)) {
                    world.setBlockState(blockpos, ElementalCreepers.silverCreepBlock.getDefaultState().withProperty(BlockSilverCreeper.VARIANT, BlockSilverCreeper.EnumType.forModelBlock(iblockstate)), 3);
                    EntitySilverCreeper.this.spawnExplosionParticle();
                    EntitySilverCreeper.this.setDead();
                }
            }
        }
    }
}
