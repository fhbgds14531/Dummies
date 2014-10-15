package dummies.mod.fhbgds.client.render;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomSkin {
    public static final ResourceLocation locationStevePng = new ResourceLocation("textures/entity/steve.png");
    public static ThreadDownloadImageData dl;
    public static ResourceLocation skinLoc;

    public CustomSkin(String name){
        if (!name.isEmpty()){
        	if(CustomSkin.getLocationSkin(name) == null){
        		dl = CustomSkin.getDownloadImageSkin(CustomSkin.getLocationSkin(name), name);
        	}
        }
    }

    public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation loc, String name)
    {
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Object object = texturemanager.getTexture(loc);

        if (object == null)
        {
            object = new ThreadDownloadImageData(
            	(File)null, 
            	String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] {StringUtils.stripControlCodes(name)}), 
            	locationStevePng, 
            	new ImageBufferDownload()
            );
            texturemanager.loadTexture(loc, (ITextureObject)object);
        }

        return (ThreadDownloadImageData)object;
    }

    public static ResourceLocation getLocationSkin(String name){
        return new ResourceLocation("minecraft", "skins/" + StringUtils.stripControlCodes(name));
    }

}