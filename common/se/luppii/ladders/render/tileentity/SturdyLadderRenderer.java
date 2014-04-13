package se.luppii.ladders.render.tileentity;

import org.lwjgl.opengl.GL11;

import se.luppii.ladders.lib.References;
import se.luppii.ladders.render.model.ModelSturdyLadder;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SturdyLadderRenderer extends TileEntitySpecialRenderer {
	
	public static final ResourceLocation sturdyLadderTexture = new ResourceLocation(References.MOD_ID.toLowerCase(), "textures/models/ironsturdyladder.png");
	private ModelSturdyLadder modelSturdyLadder = new ModelSturdyLadder();
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,	double d2, float f) {
		
		int metadata = 0;
		int rotation = 0;
		
		metadata = tileentity.getBlockMetadata();
		if (tileentity.getWorldObj() != null) {
			
			rotation = metadata + 2 & 3;
			rotation *= 90;
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float)d0 + 0.5F, (float)d1 + 1.5F, (float)d2 + 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
		
		bindTexture(sturdyLadderTexture);
		
		modelSturdyLadder.renderLadder();
		
		GL11.glPopMatrix();
	}
}
