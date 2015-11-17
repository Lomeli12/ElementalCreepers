package net.lomeli.ec.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntityCookieCreeper extends EntityBaseCreeper {

    public EntityCookieCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        for (int i = 0; i < ModVars.cookieCreeperAmount; i++) {
            EntityItem cookie = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Items.cookie));
            cookie.motionY = 0.5F;
            worldObj.spawnEntityInWorld(cookie);
        }
    }
}
