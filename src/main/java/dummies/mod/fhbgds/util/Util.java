package dummies.mod.fhbgds.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

public class Util {

	private Util(){}
	
	@SuppressWarnings("unchecked")
	public static ResourceLocation getPlayerSkinLoc(UUID id){
		List<Entity> l = MinecraftServer.getServer().getEntityWorld().getLoadedEntityList();
		for(Entity e : l){
			if(e instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer) e;
				UUID id1 = player.getUniqueID();
				if(id1.equals(id)){
					Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Minecraft.getMinecraft().func_152347_ac().getTextures(Minecraft.getMinecraft().func_152347_ac().fillProfileProperties(player.getGameProfile(), false), false);
					if(map.isEmpty()) break;
					MinecraftProfileTexture skin = map.get(Type.SKIN);
					SkinManager sm = Minecraft.getMinecraft().func_152342_ad();
					return sm.func_152792_a(skin, Type.SKIN);
				}
			}
		}
		return new ResourceLocation("skins/" + id.toString());
	}
}
