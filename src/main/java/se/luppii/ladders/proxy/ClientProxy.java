package se.luppii.ladders.proxy;

import se.luppii.ladders.block.BlockRopeLadder;
import se.luppii.ladders.block.BlockSturdyLadder;
import se.luppii.ladders.render.block.BlockRopeLadderRenderer;
import se.luppii.ladders.render.block.BlockSturdyLadderRenderer;
import se.luppii.ladders.render.tileentity.RopeLadderRenderer;
import se.luppii.ladders.render.tileentity.SturdyLadderRenderer;
import se.luppii.ladders.render.tileentity.VineLadderRenderer;
import se.luppii.ladders.tile.TileEntityRopeLadder;
import se.luppii.ladders.tile.TileEntitySturdyLadder;
import se.luppii.ladders.tile.TileEntityVineLadder;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	private static int ropeLadderRenderID;

	private static ISimpleBlockRenderingHandler ropeLadderRenderer;

	private static int sturdyLadderRenderID;

	private static ISimpleBlockRenderingHandler sturdyLadderRenderer;

	private static int vineLadderRenderID;

	private static ISimpleBlockRenderingHandler vineLadderRenderer;

	@Override
	public void registerRenderers() {

		// Register RopeLadder renderer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeLadder.class, new RopeLadderRenderer());
		ropeLadderRenderID = RenderingRegistry.getNextAvailableRenderId();
		BlockRopeLadder.setRenderID(ropeLadderRenderID);
		ropeLadderRenderer = new BlockRopeLadderRenderer(ropeLadderRenderID);
		RenderingRegistry.registerBlockHandler(ropeLadderRenderer);
		// Register SturdyLadder renderer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySturdyLadder.class, new SturdyLadderRenderer());
		sturdyLadderRenderID = RenderingRegistry.getNextAvailableRenderId();
		BlockSturdyLadder.setRenderID(sturdyLadderRenderID);
		sturdyLadderRenderer = new BlockSturdyLadderRenderer(sturdyLadderRenderID);
		RenderingRegistry.registerBlockHandler(sturdyLadderRenderer);
		// Register VineLadder renderer
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVineLadder.class, new VineLadderRenderer());
		vineLadderRenderID = RenderingRegistry.getNextAvailableRenderId();
		BlockSturdyLadder.setRenderID(vineLadderRenderID);
		vineLadderRenderer = new BlockSturdyLadderRenderer(vineLadderRenderID);
		RenderingRegistry.registerBlockHandler(vineLadderRenderer);
	}
}
