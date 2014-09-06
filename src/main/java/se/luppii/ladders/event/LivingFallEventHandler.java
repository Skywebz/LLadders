package se.luppii.ladders.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import se.luppii.ladders.block.BlockLadderDispenser;
import se.luppii.ladders.block.BlockRopeLadder;
import se.luppii.ladders.block.BlockSturdyLadder;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LivingFallEventHandler {

	@SubscribeEvent
	public void livingFall(LivingFallEvent event) {

		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			int p_x = MathHelper.floor_double(player.posX);
			int p_y = MathHelper.floor_double(player.posY);
			int p_z = MathHelper.floor_double(player.posZ);
			if (player.onGround) {
				World world = player.worldObj;
				// Check all four positions for the ladder, and check for both types of ladders
				if (world.getBlock(p_x - 1, p_y + 1, p_z) instanceof BlockRopeLadder
						|| world.getBlock(p_x - 1, p_y + 1, p_z) instanceof BlockSturdyLadder
						|| world.getBlock(p_x - 1, p_y + 1, p_z) instanceof BlockLadderDispenser
						|| world.getBlock(p_x + 1, p_y + 1, p_z) instanceof BlockRopeLadder
						|| world.getBlock(p_x + 1, p_y + 1, p_z) instanceof BlockSturdyLadder
						|| world.getBlock(p_x + 1, p_y + 1, p_z) instanceof BlockLadderDispenser
						|| world.getBlock(p_x, p_y + 1, p_z - 1) instanceof BlockRopeLadder
						|| world.getBlock(p_x, p_y + 1, p_z - 1) instanceof BlockSturdyLadder
						|| world.getBlock(p_x, p_y + 1, p_z - 1) instanceof BlockLadderDispenser
						|| world.getBlock(p_x, p_y + 1, p_z + 1) instanceof BlockRopeLadder
						|| world.getBlock(p_x, p_y + 1, p_z + 1) instanceof BlockSturdyLadder
						|| world.getBlock(p_x, p_y + 1, p_z + 1) instanceof BlockLadderDispenser) {
					event.distance = 0F;
				}
			}
		}
	}
}
