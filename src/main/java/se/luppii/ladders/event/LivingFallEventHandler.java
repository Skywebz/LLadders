package se.luppii.ladders.event;

import se.luppii.ladders.LLadders;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class LivingFallEventHandler {
	
	@ForgeSubscribe
	public void livingFall(LivingFallEvent event) {
		
		if (event.entityLiving instanceof EntityPlayer) {
			
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			int p_x = MathHelper.floor_double(player.posX);
			int p_y = MathHelper.floor_double(player.posY);
			int p_z = MathHelper.floor_double(player.posZ);
			
			if (player.onGround) {
				
				World world = player.worldObj;
				//Check all four positions for the ladder, and check for both types of ladders
				if (world.getBlockId(p_x - 1, p_y + 1, p_z) == LLadders.blockRopeLadder.blockID ||
						world.getBlockId(p_x - 1, p_y + 1, p_z) == LLadders.blockSturdyLadder.blockID ||
						world.getBlockId(p_x + 1, p_y + 1, p_z) == LLadders.blockRopeLadder.blockID ||
						world.getBlockId(p_x + 1, p_y + 1, p_z) == LLadders.blockSturdyLadder.blockID ||
						world.getBlockId(p_x, p_y + 1, p_z - 1) == LLadders.blockRopeLadder.blockID ||
						world.getBlockId(p_x, p_y + 1, p_z - 1) == LLadders.blockSturdyLadder.blockID ||
						world.getBlockId(p_x, p_y + 1, p_z + 1) == LLadders.blockRopeLadder.blockID ||
						world.getBlockId(p_x, p_y + 1, p_z + 1) == LLadders.blockSturdyLadder.blockID) {
					
					event.distance = 0F;
				}
			}
		}
	}
}
