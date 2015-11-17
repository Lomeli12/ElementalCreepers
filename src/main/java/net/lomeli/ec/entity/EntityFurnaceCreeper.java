package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntityFurnaceCreeper extends EntityBaseCreeper {

    public EntityFurnaceCreeper(World world) {
        super(world);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.furnaceCreeperRadius * power) : ModVars.furnaceCreeperRadius;
        List entityList = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(radius, radius, radius));
        if (entityList != null && !entityList.isEmpty()) {
            for (Object obj : entityList) {
                if (obj != null && obj instanceof EntityPlayer)
                    generateTrap((EntityPlayer) obj);
            }
        }
    }

    public void generateTrap(EntityPlayer player) {
        IBlockState wall = Blocks.stonebrick.getDefaultState();
        IBlockState gate = Blocks.iron_bars.getDefaultState();
        IBlockState lava = Blocks.lava.getDefaultState();
        if (!worldObj.isRemote) {
            for (int x = -1; x < 2; x++)
                for (int y = -1; y < 3; y++)
                    for (int z = -1; z < 2; z++) {
                        BlockPos pos = new BlockPos(player.posX + x, Math.floor(player.getEntityBoundingBox().minY + y), player.posZ + z);
                        if (!worldObj.isAirBlock(pos))
                            continue;
                        if (x == -1 && z == 0 && y == 1)
                            worldObj.setBlockState(pos, gate);
                        else if (x == 0 && z == 0 && y == 0)
                            worldObj.setBlockState(pos, lava);
                        else if (!(x == 0 && z == 0 && y == 1))
                            worldObj.setBlockState(pos, wall);
                    }
        }
    }
}
