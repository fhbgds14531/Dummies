package dummies.mod.fhbgds.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntitySummoner extends EntityArrow {
	
	public EntitySummoner(World world, EntityLivingBase entity, float f) {
		super(world, entity, f);
		this.setKnockbackStrength(0);
		this.setDamage(0);
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.ticksExisted > 0){
			this.spawnEntity();
		}
	}
	
	public void spawnEntity(){
		if(!this.worldObj.isRemote){
			EntityDummy dummy = new EntityDummy(this.worldObj, (EntityPlayer)this.shootingEntity);
			dummy.setPositionAndRotation(this.posX, this.posY, this.posZ, 0, 0);
			dummy.setVelocity(this.motionX, this.motionY, this.motionZ);
			this.worldObj.spawnEntityInWorld(dummy);
		}
		this.setDead();
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer player){
		if(player != this.shootingEntity){
			this.spawnEntity();
		}
	}
}
