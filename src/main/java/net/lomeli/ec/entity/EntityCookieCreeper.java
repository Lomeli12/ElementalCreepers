package net.lomeli.ec.entity;

import net.lomeli.ec.lib.ECVars;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCookieCreeper extends EntityBaseCreeper {

    public EntityCookieCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        for (int i = 0; i < ECVars.cookieCreeperAmount; i++) {
            EntityItem cookie = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.cookie));
            cookie.motionY = 0.5F;
            worldObj.spawnEntityInWorld(cookie);
        }
    }

}
