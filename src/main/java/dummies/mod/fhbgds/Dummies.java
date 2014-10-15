package dummies.mod.fhbgds;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryNamespaced;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import dummies.mod.fhbgds.entity.EntityDummy;
import dummies.mod.fhbgds.entity.EntitySummoner;
import dummies.mod.fhbgds.items.ItemCloner;
import dummies.mod.fhbgds.lib.Reference;
import dummies.mod.fhbgds.proxy.ProxyCommon;

@Mod(modid=Reference.MODID, name=Reference.MODNAME, version=Reference.VERSION)
public class Dummies {

	public static final String UNLOC_PREFIX = Reference.MODID + ":";
	
	@Instance
	public static Dummies instance;
	
	@SidedProxy(clientSide=Reference.MODID + ".mod.fhbgds.proxy.ProxyClient", serverSide=Reference.MODID + ".mod.fhbgds.ProxyCommon")
	public static ProxyCommon proxy;
	
	RegistryNamespaced reg = GameData.getItemRegistry();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		clonerID = config.get(Configuration.CATEGORY_GENERAL, "clonerID", 8020).getInt();
		maxClonesPerItem = config.get(Configuration.CATEGORY_GENERAL, "Maximum clones per cloner item", 64).getInt();
		cloneLifespan = config.get(Configuration.CATEGORY_GENERAL, "Clone lifespan (ticks)", 400).getInt();
		config.save();
		
		cloner = new ItemCloner().setUnlocalizedName(UNLOC_PREFIX + "cloner").setTextureName(UNLOC_PREFIX + "summoner");
		
		reg.addObject(clonerID, UNLOC_PREFIX + "cloner", cloner);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e){
		proxy.registerRenderers();
		
		GameRegistry.addShapelessRecipe(new ItemStack(cloner, 1), Items.bow, Items.diamond, Items.bed);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		EntityRegistry.registerModEntity(EntitySummoner.class, UNLOC_PREFIX + "summoner", 0, instance, 0, 1, true);
		EntityRegistry.registerModEntity(EntityDummy.class, UNLOC_PREFIX + "dummy", 1, instance, 256, 100, true);
	}
	
	public static int clonerID;
	public static int maxClonesPerItem;
	public static int cloneLifespan;
	
	public static Item cloner;
}
