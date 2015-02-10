package net.lomeli.ec.entity;

import java.util.Calendar;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

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
        BlockPos blockPos = new BlockPos(x, y, z);
        if (spawnCake || this.rand.nextInt(5) <= 2) {
            IBlockState state = Blocks.torch.onBlockPlaced(worldObj, blockPos, EnumFacing.UP, 0f, 0f, 0f, 0, null);
            if (Blocks.cake.canPlaceBlockAt(worldObj, blockPos)) {
                state = Blocks.cake.onBlockPlaced(worldObj, blockPos, EnumFacing.UP, 0f, 0f, 0f, 0, null);
                worldObj.setBlockState(blockPos, state, 3);
            }
            BlockPos b1 = blockPos.add(1, 0, 0);
            if (Blocks.torch.canPlaceBlockAt(worldObj, b1))
                worldObj.setBlockState(b1, state, 3);
            b1 = blockPos.add(-1, 0, 0);
            if (Blocks.torch.canPlaceBlockAt(worldObj, b1))
                worldObj.setBlockState(b1, state, 3);
            b1 = blockPos.add(0, 0, 1);
            if (Blocks.torch.canPlaceBlockAt(worldObj, b1))
                worldObj.setBlockState(b1, state, 3);
            b1 = blockPos.add(0, 0, -1);
            if (Blocks.torch.canPlaceBlockAt(worldObj, b1))
                worldObj.setBlockState(b1, state, 3);
        }
    }
}
