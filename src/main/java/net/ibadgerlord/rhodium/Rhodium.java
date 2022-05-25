package net.ibadgerlord.rhodium;

import net.fabricmc.api.ModInitializer;
import net.ibadgerlord.rhodium.util.init.*;

public class Rhodium implements ModInitializer {

    public static final String MOD_ID = "rhodium";

    @Override
    public void onInitialize() {
        RhodiumItemRegistry.registerRhodiumItems();
        RhodiumBlockRegistry.registerRhodiumBlocks();

        RhodiumBlockEntityRegistry.registerRhodiumBlockEntities();
        RhodiumScreenHandlerRegistry.registerRhodiumScreenHandlers();
        RhodiumRecipeRegistry.registerRhodiumRecipes();

        RhodiumParticleRegistry.registerRhodiumParticles();
    }

}
