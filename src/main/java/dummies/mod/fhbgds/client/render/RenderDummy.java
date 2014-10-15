package dummies.mod.fhbgds.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import dummies.mod.fhbgds.entity.EntityDummy;

public class RenderDummy extends RenderBiped{

	public boolean hasSpoken = false;
	
	public RenderDummy(ModelBiped model, float par2) {
		super(model, par2);
	}
	
	public void doRender(EntityDummy dummy, double par2, double par4, double par6, float par8, float par9){
		this.bindTexture(dummy.skinLoc);
		super.doRender(dummy, par2, par4, par6, par8, par9);
	}
	
	public void doRender(EntityLiving entity, double par2, double par4, double par6, float par8, float par9){
		this.doRender((EntityDummy)entity, par2, par4, par6, par8, par9);
	}
	
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9){
		this.doRender((EntityDummy)entity, par2, par4, par6, par8, par9);
	}
	
	public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9){
		this.doRender((EntityDummy)par1EntityLivingBase, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		if(entity instanceof EntityDummy){
			EntityDummy dummy = (EntityDummy) entity;
			return dummy.skinLoc;
		}
		return CustomSkin.locationStevePng;
	}
}
