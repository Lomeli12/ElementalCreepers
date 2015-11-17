package net.lomeli.ec.client;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;

public class CreeperEntry {
    public static List<CreeperEntry> entryList;

    static {
        entryList = Lists.newArrayList();
        entryList.add(new CreeperEntry(EntityCreeper.class, "book.entry.minecraft.creeper"));
    }

    private Class<? extends Entity> creeperClass;
    private Entity entity;
    private String unlocalizedText;
    public CreeperEntry(Class<? extends Entity> creeperClass, String unlocalizedText) {
        this.creeperClass = creeperClass;
        this.unlocalizedText = unlocalizedText;
    }

    public Class getEntityClass() {
        return this.creeperClass;
    }

    public Entity getEntity(World world) {
        if (entity == null) {
            try {
                if (creeperClass != null)
                    entity = creeperClass.getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    public String getUnlocalizedText() {
        return this.unlocalizedText;
    }

    public static List<CreeperEntry> generatePages(NBTTagCompound tagCompound) {
        List<CreeperEntry> list = Lists.newArrayList();
        if (tagCompound != null && tagCompound.hasKey("creepers", 9)) {
            NBTTagList tagList = tagCompound.getTagList("creepers", 8);
            for (int i = 0; i < tagList.tagCount(); i++) {
                String clazzString = ((NBTTagString) tagList.get(i)).getString();
                listSearch:
                for (CreeperEntry entry : entryList) {
                    if (entry.getEntityClass().getCanonicalName().equals(clazzString)) {
                        list.add(entry);
                        break listSearch;
                    }
                }
            }
        }
        return list;
    }
}
