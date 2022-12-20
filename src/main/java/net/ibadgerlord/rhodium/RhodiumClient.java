package net.ibadgerlord.rhodium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.ibadgerlord.rhodium.screen.AlchemyTableScreen;
import net.ibadgerlord.rhodium.util.init.RhodiumScreenHandlerRegistry;

public class RhodiumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(RhodiumScreenHandlerRegistry.ALCHEMY_TABLE_SCREEN_HANDLER, AlchemyTableScreen::new);

    }

}
