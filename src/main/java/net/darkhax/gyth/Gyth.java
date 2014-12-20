package net.darkhax.gyth;

import java.util.Arrays;

import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.handler.CraftingHandler;
import net.darkhax.gyth.common.items.ItemBlockModularTank;
import net.darkhax.gyth.common.items.ItemTankUpgrade;
import net.darkhax.gyth.common.tabs.CreativeTabGyth;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.plugins.PluginManager;
import net.darkhax.gyth.utils.Constants;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, dependencies = "required-after:Waila")
public class Gyth {

    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static ProxyCommon proxy;

    @Mod.Instance(Constants.MODID)
    public static Gyth instance;

    public static Block modularTank;
    public static Item tankUpgrade;
    public static ItemBlock itemModularTank;
    public static CreativeTabs tabGyth = new CreativeTabGyth();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        setModMeta(event.getModMetadata());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        new PluginManager();
        modularTank = new BlockModularTank();
        GameRegistry.registerBlock(modularTank, ItemBlockModularTank.class, "modularTank");
        GameRegistry.registerTileEntity(TileEntityModularTank.class, "modularTank");
        tankUpgrade = new ItemTankUpgrade().setCreativeTab(tabGyth).setUnlocalizedName("modularTankUpgrade");
        GameRegistry.registerItem(tankUpgrade, "tankUpgrade");
        new CraftingHandler();
        proxy.registerBlockRenderers();
    }

    @EventHandler
    public void messageRecieved(FMLInterModComms.IMCEvent event) {

    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {

        if (event.itemStack.getItem() != null)
            event.toolTip.add(Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
    }

    void setModMeta(ModMetadata meta) {

        meta.authorList = Arrays.asList("Darkhax");
        meta.credits = "Coded by Darkhax";
        meta.description = "This mod adds a modular tank system.";
        meta.autogenerated = false;
    }
}