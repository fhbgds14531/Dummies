package dummies.mod.fhbgds.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dummies.mod.fhbgds.entity.EntitySummoner;

public class ItemCloner extends ItemBow{

	public ItemCloner(){
		this.maxStackSize = 1;
		this.setMaxDamage(64);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int diff) { ;
		int durationHeld = getMaxItemUseDuration(stack) - diff;

		float secondsHeld = durationHeld / 20.0F;
		float adjustedTimeHeld = (secondsHeld * secondsHeld + secondsHeld * 2.0F) / 3.0F;
	
		if (adjustedTimeHeld > 1.0F){
			adjustedTimeHeld = 1.0F;
		}
		
		EntitySummoner summoner = new EntitySummoner(world, player, adjustedTimeHeld * 2.0F);
		stack.damageItem(1, player);
		world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + adjustedTimeHeld * 0.5F);
		world.spawnEntityInWorld(summoner);
		stack.damageItem(0, player);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister r){
		this.itemIcon = r.registerIcon(this.getIconString());
	}
}
