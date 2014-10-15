package dummies.mod.fhbgds.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import dummies.mod.fhbgds.Dummies;
import dummies.mod.fhbgds.client.render.CustomSkin;
import dummies.mod.fhbgds.util.Util;

public class EntityDummy extends EntityCreature{
	
	public String shooter;
	public UUID shooterID;
	public ResourceLocation skinLoc = CustomSkin.locationStevePng;

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
	
	public void setEntity(Entity e){
		if(e instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) e;
			this.shooter = player.getDisplayName();
			this.setCustomNameTag(this.shooter);
			this.shooterID = player.getUniqueID();
			this.skinLoc = Util.getPlayerSkinLoc(this.shooterID);
		}
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
	}

	@Override
	public boolean canDespawn(){
		return false;
	}
	
	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(this.ticksExisted > Dummies.cloneLifespan) this.kill();
	}
}
