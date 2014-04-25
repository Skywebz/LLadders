package se.luppii.ladders;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import se.luppii.ladders.updater.UpdateManager;
import se.luppii.ladders.block.BlockLadderDispenser;
import se.luppii.ladders.block.BlockRopeLadder;
import se.luppii.ladders.block.BlockSturdyLadder;
import se.luppii.ladders.block.ItemBlockLadder;
import se.luppii.ladders.gui.GuiHandler;
import se.luppii.ladders.lib.Config;
import se.luppii.ladders.lib.References;
import se.luppii.ladders.proxy.CommonProxy;
import se.luppii.ladders.tile.TileEntityLadderDispenser;
import se.luppii.ladders.tile.TileEntityRopeLadder;
import se.luppii.ladders.tile.TileEntitySturdyLadder;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class LLadders {
	
	@Instance(References.MOD_ID)
	public static LLadders instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY_LOCATION, serverSide = References.COMMON_PROXY_LOCATION)
	public static CommonProxy proxy;
	
	public static Block blockLadderDispenser;
	public static Block blockRopeLadder;
	public static Block blockSturdyLadder;
	public static boolean checkForUpdates;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		// Load config
		Config conf = new Config();
		conf.loadConfig(e);
		
		blockLadderDispenser = new BlockLadderDispenser(conf.blockLadderDispenser.getInt());
		blockRopeLadder = new BlockRopeLadder(conf.blockRopeLadder.getInt(), conf.ropeLadderLeftClick.getBoolean(true));
		blockSturdyLadder = new BlockSturdyLadder(conf.blockSturdyLadder.getInt(), conf.sturdyLadderLeftClick.getBoolean(true));
		
		checkForUpdates = conf.checkForUpdates.getBoolean(true);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		
		 GameRegistry.registerTileEntity(TileEntityRopeLadder.class, "LRopeLadder");
		 GameRegistry.registerBlock(blockRopeLadder, ItemBlockLadder.class, blockRopeLadder.getUnlocalizedName());
		 
		 GameRegistry.registerTileEntity(TileEntitySturdyLadder.class, "LSturdyLadder");
		 GameRegistry.registerBlock(blockSturdyLadder, ItemBlockLadder.class, blockSturdyLadder.getUnlocalizedName());
		 
		 GameRegistry.registerTileEntity(TileEntityLadderDispenser.class, "LadderDispenser");
		 GameRegistry.registerBlock(blockLadderDispenser, blockLadderDispenser.getUnlocalizedName());
		 
		 addRecipes();
		 
		 NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
		 proxy.registerRenderers();
		 
		 TickRegistry.registerScheduledTickHandler(new se.luppii.ladders.updater.UpdateManager(), Side.CLIENT);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	private static void addRecipes() {
		
		// Rope Ladder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockRopeLadder, 6, 0), true, new Object[] {
			"V V", "PPP", "V V", 'P', "plankWood", 'V', Block.vine }));
		
		// Sturdy Ladder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockSturdyLadder, 16, 0), true, new Object[] {
			"I I", "IPI", "I I", 'P', "plankWood", 'I', Item.ingotIron }));
		
		// Ladder Dispenser
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockLadderDispenser, 1, 0), true, new Object[] {
			"III", "IRI", "ILI", 'I', Item.ingotIron, 'R', Item.redstone, 'L', blockRopeLadder }));
	}
}