package dummies.mod.fhbgds.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import dummies.mod.fhbgds.entity.EntityDummy;

public class RenderDummy extends RenderLiving{

		public RenderDummy(ModelBase model, float f) {
			super(model, f);
		}

		@Override
		protected ResourceLocation getEntityTexture(Entity entity) {
			return ((EntityDummy)entity).getLocationSkin();
		}

	}