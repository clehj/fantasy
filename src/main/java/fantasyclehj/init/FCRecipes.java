package fantasyclehj.init;

import fantasyclehj.MainFC;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = MainFC.MODID)
public class FCRecipes {

    public static void init() {

        GameRegistry.addSmelting(FCBlocks.crying_obsidian,
                new ItemStack(Blocks.OBSIDIAN),
                1.0f);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
        IForgeRegistry<IRecipe> r = evt.getRegistry();

    }


}
