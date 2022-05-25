package net.ibadgerlord.rhodium.util.init;

import net.ibadgerlord.rhodium.Rhodium;
import net.ibadgerlord.rhodium.recipe.AlchemyTableRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RhodiumRecipeRegistry {

    public static void registerRhodiumRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Rhodium.MOD_ID, AlchemyTableRecipe.Serializer.ID),
                AlchemyTableRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(Rhodium.MOD_ID, AlchemyTableRecipe.Type.ID),
                AlchemyTableRecipe.Type.INSTANCE);

    }

}
