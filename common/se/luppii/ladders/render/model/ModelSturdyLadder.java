package se.luppii.ladders.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSturdyLadder extends ModelBase {
	
	ModelRenderer LeftPole;
	ModelRenderer RightPole;
	ModelRenderer Step1;
	ModelRenderer Step2;
	ModelRenderer Step3;
	ModelRenderer Step4;
	
	public ModelSturdyLadder() {
		textureWidth = 64;
		textureHeight = 32;
		
		LeftPole = new ModelRenderer(this, 0, 0);
		LeftPole.addBox(0F, 0F, 0F, 2, 16, 3);
		LeftPole.setRotationPoint(-8F, 8F, 5F);
		LeftPole.setTextureSize(64, 32);
		LeftPole.mirror = true;
		setRotation(LeftPole, 0F, 0F, 0F);
		
		RightPole = new ModelRenderer(this, 0, 0);
		RightPole.addBox(0F, 0F, 0F, 2, 16, 3);
		RightPole.setRotationPoint(6F, 8F, 5F);
		RightPole.setTextureSize(64, 32);
		RightPole.mirror = true;
		setRotation(RightPole, 0F, 0F, 0F);
		
		Step1 = new ModelRenderer(this, 10, 0);
		Step1.addBox(0F, 0F, 0F, 15, 1, 2);
		Step1.setRotationPoint(-7.5F, 11F, 5.5F);
		Step1.setTextureSize(64, 32);
		Step1.mirror = true;
		setRotation(Step1, 0F, 0F, 0F);
		
		Step2 = new ModelRenderer(this, 10, 3);
		Step2.addBox(0F, 0F, 0F, 15, 1, 2);
		Step2.setRotationPoint(-7.5F, 15F, 5.5F);
		Step2.setTextureSize(64, 32);
		Step2.mirror = true;
		setRotation(Step2, 0F, 0F, 0F);
		
		Step3 = new ModelRenderer(this, 10, 6);
		Step3.addBox(0F, 0F, 0F, 15, 1, 2);
		Step3.setRotationPoint(-7.5F, 19F, 5.5F);
		Step3.setTextureSize(64, 32);
		Step3.mirror = true;
		setRotation(Step3, 0F, 0F, 0F);
		
		Step4 = new ModelRenderer(this, 10, 9);
		Step4.addBox(0F, 0F, 0F, 15, 1, 2);
		Step4.setRotationPoint(-7.5F, 23F, 5.5F);
		Step4.setTextureSize(64, 32);
		Step4.mirror = true;
		setRotation(Step4, 0F, 0F, 0F);
	}
	
	public void renderLadder() {

		LeftPole.render(0.0625F);
		RightPole.render(0.0625F);
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
