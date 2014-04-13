package se.luppii.ladders.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class ModelRopeLadder extends ModelBase {
	
	ModelRenderer LeftRope;
	ModelRenderer RightRope;
	ModelRenderer Step1;
	ModelRenderer Step2;
	ModelRenderer Step3;
	ModelRenderer Step4;
	
	public ModelRopeLadder() {
		
		textureWidth = 64;
		textureHeight = 32;
		
		LeftRope = new ModelRenderer(this, 0, 0);
		LeftRope.addBox(0F, 0F, 0F, 1, 16, 1);
		LeftRope.setRotationPoint(-6F, 8F, 6.5F);
		LeftRope.setTextureSize(64, 32);
		LeftRope.mirror = true;
		setRotation(LeftRope, 0F, 0F, 0F);
		
		RightRope = new ModelRenderer(this, 0, 0);
		RightRope.addBox(0F, 0F, 0F, 1, 16, 1);
		RightRope.setRotationPoint(5F, 8F, 6.5F);
		RightRope.setTextureSize(64, 32);
		RightRope.mirror = true;
		setRotation(RightRope, 0F, 0F, 0F);
		
		Step1 = new ModelRenderer(this, 4, 0);
		Step1.addBox(0F, 0F, 0F, 14, 1, 2);
		Step1.setRotationPoint(-7F, 11F, 6F);
		Step1.setTextureSize(64, 32);
		Step1.mirror = true;
		setRotation(Step1, 0F, 0F, 0F);
		
		Step2 = new ModelRenderer(this, 4, 3);
		Step2.addBox(0F, 0F, 0F, 14, 1, 2);
		Step2.setRotationPoint(-7F, 15F, 6F);
		Step2.setTextureSize(64, 32);
		Step2.mirror = true;
		setRotation(Step2, 0F, 0F, 0F);
		
		Step3 = new ModelRenderer(this, 4, 6);
		Step3.addBox(0F, 0F, 0F, 14, 1, 2);
		Step3.setRotationPoint(-7F, 19F, 6F);
		Step3.setTextureSize(64, 32);
		Step3.mirror = true;
		setRotation(Step3, 0F, 0F, 0F);
		
		Step4 = new ModelRenderer(this, 4, 9);
		Step4.addBox(0F, 0F, 0F, 14, 1, 2);
		Step4.setRotationPoint(-7F, 23F, 6F);
		Step4.setTextureSize(64, 32);
		Step4.mirror = true;
		setRotation(Step4, 0F, 0F, 0F);
	}
	
	public void renderLadder() {

		LeftRope.render(0.0625F);
		RightRope.render(0.0625F);
		Step1.render(0.0625F);
		Step2.render(0.0625F);
		Step3.render(0.0625F);
		Step4.render(0.0625F);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z) {
		
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {
		
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
	}
}
