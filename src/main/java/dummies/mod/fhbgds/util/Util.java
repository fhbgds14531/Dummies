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

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;

import com.google.common.collect.Iterables;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.util.UUIDTypeAdapter;

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
					Property textureProperty = Iterables.getFirst(player.getGameProfile().getProperties().get("textures"), null);
					MinecraftTexturesPayload result = null;
					try {
						if(textureProperty != null){
							String json = new String(Base64.decodeBase64(textureProperty.getValue()), Charsets.UTF_8);
							result = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create().fromJson(json, MinecraftTexturesPayload.class);
						}
					} catch (JsonParseException ex) {ex.printStackTrace();}
					if( result != null){
						Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = result.getTextures();
						MinecraftProfileTexture skin = map.get(Type.SKIN);
						SkinManager sm = Minecraft.getMinecraft().func_152342_ad();
						return sm.func_152792_a(skin, Type.SKIN);
					}
				}
			}
		}
		return new ResourceLocation("skins/" + id.toString());
	}
	
	
}
