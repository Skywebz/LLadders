package se.luppii.ladders;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import se.luppii.ladders.block.BlockLadderDispenser;
import se.luppii.ladders.block.BlockRopeLadder;
import se.luppii.ladders.block.BlockSturdyLadder;
import se.luppii.ladders.block.BlockVineLadder;
import se.luppii.ladders.block.ItemBlockLadder;
import se.luppii.ladders.event.LivingFallEventHandler;
import se.luppii.ladders.gui.GuiHandler;
import se.luppii.ladders.lib.Config;
import se.luppii.ladders.lib.References;
import se.luppii.ladders.modhelper.IExtension;
import se.luppii.ladders.proxy.CommonProxy;
import se.luppii.ladders.tile.TileEntityLadderDispenser;
import se.luppii.ladders.tile.TileEntityRopeLadder;
import se.luppii.ladders.tile.TileEntitySturdyLadder;
import se.luppii.ladders.tile.TileEntityVineLadder;
import se.luppii.ladders.updater.UpdateManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION)
public class LLadders {
	
	@Instance(References.MOD_ID)
	public static LLadders instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY_LOCATION, serverSide = References.COMMON_PROXY_LOCATION)
	public static CommonProxy proxy;
	
	public static Block blockLadderDispenser;
	public static Block blockRopeLadder;
	public static Block blockSturdyLadder;
	public static Block blockVineLadder;
	public static boolean checkForUpdates;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		
		// Load config
		Config.loadConfig(e);
		
		blockLadderDispenser = new BlockLadderDispenser();
		blockRopeLadder = new BlockRopeLadder(Config.ropeLadderLeftClick.getBoolean(true));
		blockSturdyLadder = new BlockSturdyLadder(Config.sturdyLadderLeftClick.getBoolean(true));
		blockVineLadder = new BlockVineLadder(Config.vineLadderLeftClick.getBoolean(true));
		
		checkForUpdates = Config.checkForUpdates.getBoolean(true);
		
		 GameRegistry.registerTileEntity(TileEntityRopeLadder.class, "LRopeLadder");
		 GameRegistry.registerBlock(blockRopeLadder, ItemBlockLadder.class, blockRopeLadder.getUnlocalizedName());
		 
		 GameRegistry.registerTileEntity(TileEntitySturdyLadder.class, "LSturdyLadder");
		 GameRegistry.registerBlock(blockSturdyLadder, ItemBlockLadder.class, blockSturdyLadder.getUnlocalizedName());
		 
		 GameRegistry.registerTileEntity(TileEntityVineLadder.class, "LVineLadder");
		 GameRegistry.registerBlock(blockVineLadder, ItemBlockLadder.class, blockVineLadder.getUnlocalizedName());
		 
		 GameRegistry.registerTileEntity(TileEntityLadderDispenser.class, "LadderDispenser");
		 GameRegistry.registerBlock(blockLadderDispenser, blockLadderDispenser.getUnlocalizedName());
		 
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		
		 addRecipes();
		 
		 NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		 proxy.registerRenderers();
		 
		 registerEvent(new UpdateManager(), checkForUpdates);
		 
		 //Special eventhandler for falling players so climbing backsides of ladders won't cause fall damage
		 MinecraftForge.EVENT_BUS.register(new LivingFallEventHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	private static void registerEvent(Object par1Object, boolean par2) {
		
		if (par2) {
			
			FMLCommonHandler.instance().bus().register(par1Object);
		}
	}
	
	private static void addRecipes() {
		
		//Check if Ropes+ is present. And if config says to use Ropes+ recipes
		if (Loader.isModLoaded("RopesPlus") && Config.ropesPlusRecipe.getBoolean(true)) {
			try {
				Class.forName("se.luppii.ladders.modhelper.ropesplus.RopesPlus").asSubclass(IExtension.class).newInstance().load();
			} catch (Exception err) {
				FMLLog.warning("Could not load compatible class for Ropes+.");
				FMLLog.warning(err.toString());
			}
		}
		
		// Rope Laddder, check if RopesPlus is loaded. If so, check config if we should overwrite vanilla recipe
		if ((!Loader.isModLoaded("RopesPlus")) ||(Loader.isModLoaded("RopesPlus") && !Config.overwriteVanillaRecipe.getBoolean(true)) || (Loader.isModLoaded("RopesPlus") && !Config.ropesPlusRecipe.getBoolean(true)) ) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockRopeLadder, 6, 0), true, new Object[] {
				"V V", "PPP", "V V", 'P', "plankWood", 'V', Blocks.vine }));
		}
		// Sturdy Ladder
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockSturdyLadder, 16, 0), true, new Object[] {
			"I I", "IPI", "I I", 'P', "plankWood", 'I', Items.iron_ingot }));
		
		// Ladder Dispenser
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockLadderDispenser, 1, 0), true, new Object[] {
			"III", "IRI", "ILI", 'I', Items.iron_ingot, 'R', Items.redstone, 'L', blockRopeLadder }));
	}
}