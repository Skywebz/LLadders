package se.luppii.ladders.block;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import se.luppii.ladders.lib.References;
import se.luppii.ladders.tile.TileEntitySturdyLadder;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSturdyLadder extends Block implements ITileEntityProvider {
	
	private Icon blockIcon;
	private boolean enableLeftClick;
	public static int renderID;
	
	public BlockSturdyLadder(int par1,  boolean par2Boolean) {
		
		super(par1, Material.circuits);
		this.setHardness(0.4F);
		this.setStepSound(soundLadderFootstep);
		this.setUnlocalizedName("lladders.block.sturdyladder");
		this.setCreativeTab(CreativeTabs.tabDecorations);
		enableLeftClick = par2Boolean;
	}
	
	public boolean isBlockNormalCube(World world, int i, int j, int k) {
		return false;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {		
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		//Fix a collisionbox that is tiny bit bigger than the block the ladder is placed on. This to enable climbing from both sides

		int direction = par1World.getBlockMetadata(par2, par3, par4) & 3;
		float factor = 1.0F;
		float f = factor / 16.0F;

		switch (direction) {
		case 0:
			//SOUTH
			AxisAlignedBB box = super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			box = box.expand(0, 0, -f);
			return box;
		case 1:
			//WEST
			box = super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			box = box.expand(-f, 0, 0);
			return box;
		case 2:
			//NORTH
			box = super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			box = box.expand(0, 0, -f);
			return box;
		case 3:
			//EAST
			box = super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			box = box.expand(-f, 0, 0);
			return box;
		}

		return AxisAlignedBB.getBoundingBox(par2 + f, par3, par4 + f, par2 + factor - f, par3 + factor - f, par4 + factor - f);
	}
	
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {	
		this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {	
		this.updateLadderBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
    public void updateLadderBounds(int par1) {
		
		int direction = par1 & 3;
		float f = 0.185F;
		
		if (direction == 0) {
			
			this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		}
		if (direction == 1) {
			
			this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		}
		if (direction == 2) {
			
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		}
		if (direction == 3) {
			
			this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

    	//Check to see if it is a player as we are only interested in manipulating them
    	if (entity instanceof EntityPlayer) {

    		//Cast to EntityPlayer to make it more strict
    		EntityPlayer player = (EntityPlayer) entity;
    		
    		int direction = world.getBlockMetadata(x, y, z) & 3;

    		boolean player_close = false;

    		//Look at placement position and calculate if the player is close enough to ladder to "climb"
    		switch (direction) {

    		//SOUTH
    		case 0:
    			if (player.posZ - z >= 0.8D)
    				player_close = true;
    			break;
    		//WEST
    		case 1:
    			if (player.posX - x <= 0.2D)
    				player_close = true;
    			break;
    		//NORTH
    		case 2:
    			if (player.posZ - z <= 0.2D)
    				player_close = true;
    			break;
    		//EAST
    		case 3:
    			if (player.posX - x >= 0.8D)
    				player_close = true;
    			break;
    		default:
    			player_close = false;
    		}

    		if (player.posY - 1.0D >= y && player_close) {

    			player.moveForward = 0.0F;

    			//If player is moving down, move slowly down.
    			if (player.motionY < -0.15D)
    				player.motionY = -0.15D;

    			//Check if we want to climb up
    			if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight)) {

    				if (player.motionY < 0.2D)
    					player.motionY = 0.2D;
    			}		
    		}

    		//Check if we are sneaking and want to climb up, or just want to sneak "stand still" on the ladder
    		if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak) && (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft) || GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight))) {

    			player.motionY = 0.2D;
    		}
    		else if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) {	

    			player.setVelocity(0.0D, 0.08D, 0.0D); //Found this by experimenting. An upward velocity of 0.08 negates gravity fall
    		}
    	}
    }
	
    @Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {	
		return false;
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return false;
	}
	
	public static void setRenderID(int id) {		
		renderID = id;
	}
	
	public int getRenderType() {		
		return renderID;
	}
	
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		
		return par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP) ||
				par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST ) ||
				par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST ) ||
				par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH) ||
				par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH) ||
				par1World.getBlockId(par2, par3 - 1, par4) == this.blockID ||
				par1World.getBlockId(par2, par3 + 1, par4) == this.blockID;
	}
	
	
	@Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		
		int j1 = par9 & 3;
		
		if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID) {
			return par1World.getBlockMetadata(par2, par3 + 1, par4) & 3;
		}
		if (par1World.getBlockId(par2, par3 - 1, par4) == this.blockID) {
			return par1World.getBlockMetadata(par2, par3 - 1, par4) & 3;
		}
		
		if (par5 == 2) {
			j1 = 0;
		}
		if (par5 == 3) {
			j1 = 2;
		}
		if (par5 == 4) {
			j1 = 3;
		}
		if (par5 == 5) {
			j1 = 1;
		}
		
		return j1;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

		if (par5EntityLivingBase == null) {
			return;
		}
		
		int direction = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		// Use the same direction as ladders above or below.
		if (par1World.getBlockId(par2, par3 + 1, par4) == this.blockID) {
			direction = par1World.getBlockMetadata(par2, par3 + 1, par4) & 3;
		}
		if (par1World.getBlockId(par2, par3 - 1, par4) == this.blockID) {
			direction = par1World.getBlockMetadata(par2, par3 - 1, par4) & 3;
		}
		
		par1World.setBlockMetadataWithNotify(par2, par3, par4, direction, 2);
	}
	
	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		
		if (enableLeftClick) {
			
			if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().isItemEqual(new ItemStack(this))) {
				
				int meta = par1World.getBlockMetadata(par2, par3, par4) & 3;
				if (canSetLadder(par1World, par2, par3 + 1, par4, meta)) {
					
					setLadder(par1World, par2, par3 + 1, par4, meta, par5EntityPlayer);
				}
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		
		int metadata = par1World.getBlockMetadata(par2, par3, par4) & 3;
		boolean flag = false;
		
		if (par1World.isBlockSolidOnSide(par2, par3 - 1, par4, UP) || par1World.getBlockId(par2, par3 - 1, par4) == this.blockID) {
			
			flag = true;
		}
		
		// Placing ladders on the opposite of another, effectively making "flying" ladders - isn't allowed.
		if (metadata != 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) {
			
			flag = true;
		}
		if (metadata != 3 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)) {
			
			flag = true;
		}
		if (metadata != 0 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) {
			
			flag = true;
		}
		if (metadata != 1 && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) {
			
			flag = true;
		}
		
		if (!flag) {
			
			this.dropBlockAsItem(par1World, par2, par3, par4, metadata, 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}
	
	public int quantityDropped(Random par1Random) {
		return 1;
	}
	
	@Override
	public boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {		
		blockIcon = par1IconRegister.registerIcon("lladders:" + getUnlocalizedName());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {		
		return blockIcon;
	}
	
	public TileEntity getBlockEntity(int par1) {
		return new TileEntitySturdyLadder();
	}
	
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySturdyLadder();
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		
		try {
			
			TileEntitySturdyLadder te = TileEntitySturdyLadder.class.newInstance();
			return te;
		}
		catch (IllegalAccessException ex) {
			
			FMLLog.severe(References.MOD_NAME, "Unable to create TileEntitySturdyLadder instance.");
			return null;
		}
		catch (InstantiationException ex) {
			
			FMLLog.severe(References.MOD_NAME, "Unable to create TileEntitySturdyLadder instance.");
			return null;
		}
	}
	
	private boolean canSetLadder(World world, int x, int y, int z, int meta) {
		
		if (world.getBlockId(x, y, z) == this.blockID) {
			
			return canSetLadder(world, x, y + 1, z, meta);
		}
		else if (!world.isAirBlock(x, y, z)) {
			
			return false;
		}
		return true;
	}
	
	private boolean setLadder(World world, int x, int y, int z, int meta, EntityPlayer player) {
		
		int id = world.getBlockId(x, y, z);
		if (world.isAirBlock(x, y, z)) {
			
			ItemStack item = player.getCurrentEquippedItem();
			if (item.stackSize > 1) {
				
				player.setCurrentItemOrArmor(0, new ItemStack(item.getItem(), item.stackSize - 1, item.getItemDamage()));
			}
			else {
				
				player.setCurrentItemOrArmor(0, null);
			}
			world.setBlock(x, y, z, this.blockID, meta, 2);
			return true;
		}
		if (id == this.blockID) {
			
			return setLadder(world, x, y + 1, z, meta, player);
		}
		return false;
	}
}
