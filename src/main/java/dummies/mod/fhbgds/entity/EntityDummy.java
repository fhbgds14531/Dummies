package dummies.mod.fhbgds.entity;

import java.util.UUID;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dummies.mod.fhbgds.Dummies;

public class EntityDummy extends EntityCreature{
	
	public String shooterName;
	public UUID shooterID;
	public ResourceLocation skinLoc;

	public EntityDummy(World world, EntityPlayer owner){
		this(world);
		this.shooterID = owner.getPersistentID();
		this.shooterName = owner.getGameProfile().getName();
		this.setCustomNameTag(shooterName);
	}
	
	public EntityDummy(World world) {
		super(world);
		this.setSize(1.0F, 2.0F);
		
		this.getNavigator().setCanSwim(true);
		
		this.tasks.addTask(0, new EntityAIWander(this, 3));
		this.tasks.addTask(1, new EntityAILookIdle(this));
		this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 18F));
		this.tasks.addTask(4, new EntityAISwimming(this));
		
		this.tasks.addTask(5, new EntityAIAvoidEntity(this, EntityCreeper.class, 16.0F, 0.5D, 0.5D));
		this.tasks.addTask(6, new EntityAIAvoidEntity(this, EntityZombie.class, 16.0F, 0.5D, 0.5D));
		this.tasks.addTask(7, new EntityAIAvoidEntity(this, EntitySkeleton.class, 24.0F, 0.5D, 0.5D));
		this.tasks.addTask(8, new EntityAIAvoidEntity(this, EntitySpider.class, 48.0F, 0.6D, 0.5D));
		this.tasks.addTask(9, new EntityAIAvoidEntity(this, EntityCaveSpider.class, 48.0F, 0.6D, 0.5D));

		this.getNavigator().setSpeed(3);
		
		this.setAlwaysRenderNameTag(true);
	}
	
	@Override
	public boolean canDespawn(){
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public ResourceLocation getLocationSkin() {
		String newSkin = getPlayerSkin();

		if (skinLoc == null || !newSkin.equals(shooterName)) {
			skinLoc = AbstractClientPlayer.getLocationSkin(newSkin);
			AbstractClientPlayer.getDownloadImageSkin(skinLoc, newSkin);
		}
		shooterName = newSkin;
		return skinLoc;
	}

	public String getPlayerSkin() {
		return hasCustomNameTag()? getCustomNameTag() : this.shooterName;
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(this.ticksExisted > Dummies.cloneLifespan) this.kill();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);

		tag.setString("OwnerUUID", this.shooterID.toString());
		tag.setString("OwnerName", this.hasCustomNameTag()? this.getCustomNameTag() : this.shooterName);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		
		System.out.println(tag.getString("OwnerName"));

		String id;
		if(tag.hasKey("OwnerName", Constants.NBT.TAG_STRING)){
			String ownerName = tag.getString("OwnerName");
			id = PreYggdrasilConverter.func_152719_a(ownerName);
			this.shooterName = ownerName;
		}else{
			id = tag.getString("OwnerUUID");
			this.shooterName = tag.getString("OwnerName");
		}
		try{
			shooterID = UUID.fromString(id);
		}catch(Exception e){
			System.err.println("Failed to parse UUID: " + id);
			e.printStackTrace();
		}
		System.out.println(this.shooterName);
	}
}
