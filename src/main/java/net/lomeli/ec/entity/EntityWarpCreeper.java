package net.lomeli.ec.entity;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import net.lomeli.lomlib.lib.Vector3;
import net.lomeli.lomlib.util.EntityUtil;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.lib.ModVars;

public class EntityWarpCreeper extends EntityBaseCreeper {
    public EntityWarpCreeper(World world) {
        super(world);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.warpCreeperRadius * power) : ModVars.warpCreeperRadius;
        List<EntityLivingBase> entityList = removeNonLiving(worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(radius, radius, radius)));
        List<Vector3> entityPos = getEntityPos(entityList);
        for (int j = 0; j < entityList.size(); j++) {
            EntityLivingBase entity = entityList.get(j);
            ElementalCreepers.logger.logInfo("Warpping " + entity.getName());
            Vector3 vec3 = new Vector3(entity);
            int i = 0;
            ElementalCreepers.logger.logInfo(vec3.toString());
            while (vec3.equals(entity.posX, entity.posY, entity.posZ)) {
                i = rand.nextInt(entityPos.size());
                vec3 = entityPos.get(i);
            }
            ElementalCreepers.logger.logInfo(vec3.toString());
            EntityUtil.teleportTo(worldObj, entity, vec3.getX(), vec3.getY(), vec3.getZ());
            entityPos.remove(i);
            ElementalCreepers.logger.logInfo(entityPos.size());
        }
    }

    private List<Vector3> getEntityPos(List<EntityLivingBase> list) {
        List<Vector3> posList = Lists.newArrayList();
        for (EntityLivingBase entity : list)
            posList.add(new Vector3(entity));
        return posList;
    }

    private List<EntityLivingBase> removeNonLiving(List list) {
        Iterator it = list.listIterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (!(obj instanceof EntityLivingBase))
                it.remove();
        }
        return list;
    }
}
