package net.lomeli.ec.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * ModelCreeper - Lomeli12
 * Created using Tabula 4.1.1
 */
public class ModelSilverCreeper extends ModelBase {
    public ModelRenderer torso;
    public ModelRenderer backLeftFoot;
    public ModelRenderer frontLeftFoot;
    public ModelRenderer frontRightFoot;
    public ModelRenderer head;
    public ModelRenderer backRightFoot;
    public ModelRenderer fuzz;

    public ModelSilverCreeper() {
        this.textureWidth = 80;
        this.textureHeight = 48;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.frontRightFoot = new ModelRenderer(this, 0, 16);
        this.frontRightFoot.setRotationPoint(-2.0F, 16.0F, -4.0F);
        this.frontRightFoot.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.frontLeftFoot = new ModelRenderer(this, 0, 16);
        this.frontLeftFoot.setRotationPoint(2.0F, 16.0F, -4.0F);
        this.frontLeftFoot.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.backLeftFoot = new ModelRenderer(this, 0, 16);
        this.backLeftFoot.setRotationPoint(2.0F, 16.0F, 4.0F);
        this.backLeftFoot.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.backRightFoot = new ModelRenderer(this, 0, 16);
        this.backRightFoot.setRotationPoint(-2.0F, 16.0F, 4.0F);
        this.backRightFoot.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F);
        this.fuzz = new ModelRenderer(this, 40, 0);
        this.fuzz.setRotationPoint(-8.0F, -8.0F, 0.0F);
        this.fuzz.addBox(0.0F, 0.0F, 0.0F, 16, 27, 0, 0.0F);
        this.torso = new ModelRenderer(this, 16, 16);
        this.torso.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.torso.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.head.render(f5);
        this.frontRightFoot.render(f5);
        this.frontLeftFoot.render(f5);
        this.backLeftFoot.render(f5);
        this.backRightFoot.render(f5);
        this.fuzz.render(f5);
        this.torso.render(f5);
    }
    
    public void render(float f) {
        this.render(null, 0f, 0f, 0f, 0f, 0f, f);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
        this.head.rotateAngleY = par4 / (180F / (float) Math.PI);
        this.head.rotateAngleX = par5 / (180F / (float) Math.PI);
        this.frontLeftFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
        this.frontRightFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.backLeftFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2;
        this.backRightFoot.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
    }
}
