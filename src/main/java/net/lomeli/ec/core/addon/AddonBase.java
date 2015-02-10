package net.lomeli.ec.core.addon;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Loader;

public abstract class AddonBase {
    public static List<AddonBase> addonList = new ArrayList<AddonBase>();

    protected String modID;

    public AddonBase(String id) {
        this.modID = id;
    }

    public boolean isBaseInstalled() {
        return Loader.isModLoaded(this.modID);
    }

    public abstract void loadAddon();

    public static void registerAddons() {
        addonList.add(new AddonSpecialMobs());
        //addonList.add(new AddonIC2());
        //addonList.add(new AddonRF());
    }

    public static void activateAddons() {
        for (AddonBase addon : addonList) {
            if (addon.isBaseInstalled())
                addon.loadAddon();
        }
    }
}
