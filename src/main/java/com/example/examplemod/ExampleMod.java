package com.example.examplemod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.event.ServerChatEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MODID)
public final class ExampleMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "examplemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    private static AbstractVillager lastTradedVillager;

    public ExampleMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Register the commonSetup method for modloading
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }



    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.DEDICATED_SERVER)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void setLastVillager(TradeWithVillagerEvent event) {
            lastTradedVillager = event.getAbstractVillager();
            LOGGER.info("Trade gotten HAIIII");
        }

       @SubscribeEvent
       public static void exterminateVillager(ServerChatEvent event) {
            String msg = event.getRawText();

            if (msg.contains("lower")) {
                LOGGER.info("Message recieved. Bargaining");
                MerchantOffers offers = lastTradedVillager.getOffers();

                LOGGER.info("Current Prices: ");
                for (int i = 0; i < offers.size(); i++) {
                    MerchantOffer offer = offers.get(i);

                    LOGGER.info(offer.getCostA().getCount() +
                        " " + offer.getCostA().getItem());
                    LOGGER.info("Special price diff: " + offer.getSpecialPriceDiff());

                    offer.addToSpecialPriceDiff(-1);

                    LOGGER.info("Dropping by one...");
                }
            }

       }
    }
}
