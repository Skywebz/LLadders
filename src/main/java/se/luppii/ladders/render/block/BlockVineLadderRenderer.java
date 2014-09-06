package se.luppii.ladders.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import se.luppii.ladders.LLadders;
import se.luppii.ladders.render.model.ModelVineLadder;
import se.luppii.ladders.tile.TileEntityVineLadder;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockVineLadderRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

	private int renderID;

	private ModelVineLadder vineLadder = new ModelVineLadder();

	public BlockVineLadderRenderer(int id) {

		renderID = id;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		if (block == LLadders.blockVineLadder) {
			TileEntity te = new TileEntityVineLadder();
			te.blockType = block;
			te.blockMetadata = metadata;
			this.renderTileEntityAt(te, 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {

		return false;
	}

	@Override
	public int getRenderId() {

		return renderID;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double d0, double d1, double d2, float f) {

		vineLadder.renderLadder();
	}
}
