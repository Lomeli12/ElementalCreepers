package net.lomeli.ec.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.lib.ModLib;

public class ECGuiConfig extends GuiConfig {
    public ECGuiConfig(GuiScreen parent) {
        super(parent, getConfigElements(), ModLib.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ElementalCreepers.config.getConfig().toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.add(new ConfigElement(ElementalCreepers.config.getConfig().getCategory(Configuration.CATEGORY_GENERAL)));
        list.add(new ConfigElement(ElementalCreepers.config.getConfig().getCategory("explosion-configuration")));
        list.add(new ConfigElement(ElementalCreepers.config.getConfig().getCategory("spawn-rates")));
        return list;
    }
}
