package net.ibadgerlord.rhodium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.ibadgerlord.rhodium.screen.AlchemyTableScreen;
import net.ibadgerlord.rhodium.util.init.RhodiumParticleRegistry;
import net.ibadgerlord.rhodium.util.init.RhodiumScreenHandlerRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class RhodiumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ScreenRegistry.register(RhodiumScreenHandlerRegistry.ALCHEMY_TABLE_SCREEN_HANDLER, AlchemyTableScreen::new);

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(Rhodium.MOD_ID, "particle/green_flame"));
        }));

        ParticleFactoryRegistry.getInstance().register(RhodiumParticleRegistry.SMALL_SOUL_FIRE_FLAME, FlameParticle.SmallFactory::new);

        ParticleFactoryRegistry.getInstance().register(RhodiumParticleRegistry.GREEN_FLAME, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(RhodiumParticleRegistry.SMALL_GREEN_FLAME, FlameParticle.SmallFactory::new);

    }

}
