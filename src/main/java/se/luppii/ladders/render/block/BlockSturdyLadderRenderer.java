package se.luppii.ladders.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import se.luppii.ladders.LLadders;
import se.luppii.ladders.render.model.ModelSturdyLadder;
import se.luppii.ladders.tile.TileEntitySturdyLadder;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockSturdyLadderRenderer extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

	private int renderID;

	private ModelSturdyLadder sturdyLadder = new ModelSturdyLadder();

	public BlockSturdyLadderRenderer(int id) {

		renderID = id;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		if (block == LLadders.blockSturdyLadder) {
			TileEntity te = new TileEntitySturdyLadder();
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

		sturdyLadder.renderLadder();
	}
}
