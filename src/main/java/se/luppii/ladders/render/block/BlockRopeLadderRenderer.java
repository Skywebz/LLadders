package se.luppii.ladders.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import se.luppii.ladders.LLadders;
import se.luppii.ladders.tile.TileEntityRopeLadder;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRopeLadderRenderer implements ISimpleBlockRenderingHandler {
	
	private int renderID;
	
	public BlockRopeLadderRenderer(int id) {
		renderID = id;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		
		if (block.blockID == LLadders.blockRopeLadder.blockID) {
			
			TileEntity te = new TileEntityRopeLadder();
			te.blockType = block;
			te.blockMetadata = metadata;
			TileEntityRenderer.instance.renderTileEntityAt(te, 0.0D, 0.0D, 0.0D, 0.0F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		
		return false;
	}

	@Override
	public int getRenderId() {
		
		return renderID;
	}
}
