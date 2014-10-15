package dummies.mod.fhbgds.proxy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderArrow;
import cpw.mods.fml.client.registry.RenderingRegistry;
import dummies.mod.fhbgds.client.render.RenderDummy;
import dummies.mod.fhbgds.entity.EntityDummy;
import dummies.mod.fhbgds.entity.EntitySummoner;

public class ProxyClient extends ProxyCommon{
	
	public static RenderDummy renderDummy = new RenderDummy(new ModelBiped(), 0.5f);

	@Override
	public void registerRenderers(){
		RenderingRegistry.registerEntityRenderingHandler(EntitySummoner.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, renderDummy);
	}
	
}
