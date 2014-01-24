package net.lomeli.ec.entity;

import net.minecraft.world.World;

public class EntityFakeIllusionCreeper extends EntityBaseCreeper implements IIllusion{

    public EntityFakeIllusionCreeper(World par1World) {
        super(par1World);
        this.setSize(1.0F, 2.0F);
    }

}
