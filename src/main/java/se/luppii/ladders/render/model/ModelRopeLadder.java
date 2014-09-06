package se.luppii.ladders.render.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelRopeLadder extends ModelBase {

	ModelRenderer LeftRope;

	ModelRenderer RightRope;

	ModelRenderer Step1;

	ModelRenderer Step2;

	ModelRenderer Step3;

	ModelRenderer Step4;

	ModelRenderer[] Knot = new ModelRenderer[16];

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
		Step1.setRotationPoint(-7F, 10F, 6F);
		Step1.setTextureSize(64, 32);
		Step1.mirror = true;
		setRotation(Step1, 0F, 0F, 0F);
		Step2 = new ModelRenderer(this, 4, 3);
		Step2.addBox(0F, 0F, 0F, 14, 1, 2);
		Step2.setRotationPoint(-7F, 14F, 6F);
		Step2.setTextureSize(64, 32);
		Step2.mirror = true;
		setRotation(Step2, 0F, 0F, 0F);
		Step3 = new ModelRenderer(this, 4, 6);
		Step3.addBox(0F, 0F, 0F, 14, 1, 2);
		Step3.setRotationPoint(-7F, 18F, 6F);
		Step3.setTextureSize(64, 32);
		Step3.mirror = true;
		setRotation(Step3, 0F, 0F, 0F);
		Step4 = new ModelRenderer(this, 4, 9);
		Step4.addBox(0F, 0F, 0F, 14, 1, 2);
		Step4.setRotationPoint(-7F, 22F, 6F);
		Step4.setTextureSize(64, 32);
		Step4.mirror = true;
		setRotation(Step4, 0F, 0F, 0F);
		// This is the models for the knots, 2 models per knot.
		float x = 0, y = 0, z = 0;
		for (int i = 0; i < this.Knot.length; i++) {
			if (i < 4) {
				Knot[i] = new ModelRenderer(this, 4, 12);
				Knot[i].addBox(0F, 0F, 0F, 1, 1, 2);
				x = -6F;
				if (i == 0)
					y = 11F;
				z = 6F;
				Knot[i].setRotationPoint(x, y, z);
				y += 4F;
			}
			else if (i < 8) {
				Knot[i] = new ModelRenderer(this, 10, 12);
				Knot[i].addBox(0F, 0F, 0F, 2, 1, 1);
				x = -6.5F;
				if (i == 4)
					y = 11F;
				z = 6.5F;
				Knot[i].setRotationPoint(x, y, z);
				y += 4F;
			}
			else if (i < 12) {
				Knot[i] = new ModelRenderer(this, 4, 12);
				Knot[i].addBox(0F, 0F, 0F, 1, 1, 2);
				x = 5F;
				if (i == 8)
					y = 11F;
				z = 6F;
				Knot[i].setRotationPoint(x, y, z);
				y += 4F;
			}
			else {
				Knot[i] = new ModelRenderer(this, 10, 12);
				Knot[i].addBox(0F, 0F, 0F, 2, 1, 1);
				x = 4.5F;
				if (i == 12)
					y = 11F;
				z = 6.5F;
				Knot[i].setRotationPoint(x, y, z);
				y += 4F;
			}
			Knot[i].setTextureSize(64, 32);
			Knot[i].mirror = true;
			setRotation(Knot[i], 0F, 0F, 0F);
		}
	}

	public void renderLadder() {

		LeftRope.render(0.0625F);
		RightRope.render(0.0625F);
		Step1.render(0.0625F);
		Step2.render(0.0625F);
		Step3.render(0.0625F);
		Step4.render(0.0625F);
		for (int i = 0; i < this.Knot.length; i++) {
			Knot[i].render(0.0625F);
		}
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
