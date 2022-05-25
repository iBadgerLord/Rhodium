package net.ibadgerlord.rhodium.util.init;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.screen.AlchemyTableScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class RhodiumScreenHandlerRegistry {

    public static ScreenHandlerType<AlchemyTableScreenHandler> ALCHEMY_TABLE_SCREEN_HANDLER;

    public static void registerRhodiumScreenHandlers() {
        ALCHEMY_TABLE_SCREEN_HANDLER =
                ScreenHandlerRegistry.registerSimple(new Identifier(Rhodium.MOD_ID, "alchemy_table"),
                        AlchemyTableScreenHandler::new);
    }

}
