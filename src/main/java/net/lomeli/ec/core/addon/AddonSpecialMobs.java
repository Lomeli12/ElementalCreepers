package net.lomeli.ec.core.addon;

import net.lomeli.ec.core.EntityRegistering;

public class AddonSpecialMobs extends AddonBase {
    public AddonSpecialMobs() {
        super("SpecialMobs");
    }

    @Override
    public void loadAddon() {
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityArmorCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityDarkCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityDeathCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityDirtCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityDoomCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityDrowningCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityEnderCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityFireCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityJumpingCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityLightningCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntityMiniCreeper.class);
        EntityRegistering.creeperClassList.add(toast.specialMobs.entity.creeper.EntitySplittingCreeper.class);
    }
}
