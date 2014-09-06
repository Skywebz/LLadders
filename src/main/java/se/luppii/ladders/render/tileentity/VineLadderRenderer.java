package se.luppii.ladders.render.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import se.luppii.ladders.lib.References;
import se.luppii.ladders.render.model.ModelVineLadder;

public class VineLadderRenderer extends TileEntitySpecialRenderer {

	public static final ResourceLocation vineLadderTexture = new ResourceLocation(References.MOD_ID.toLowerCase(),
			"textures/models/vineladder.png");

	private ModelVineLadder modelVineLadder = new ModelVineLadder();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {

		int metadata = 0;
		int rotation = 0;
		metadata = tileentity.getBlockMetadata();
		if (tileentity.getWorldObj() != null) {
			rotation = metadata + 2 & 3;
			rotation *= 90;
		}
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d0 + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
		bindTexture(vineLadderTexture);
		modelVineLadder.renderLadder();
		GL11.glPopMatrix();
	}
}
