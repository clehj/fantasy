package fantasyclehj.init;

import fantasyclehj.potion.PotionMelancholy;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class FCPotions {

    // 药水效果
    public static Potion MELANCHOLY;

    // 药水类型（普通、延长、强化）
    public static PotionType MELANCHOLY_POTION;
    public static PotionType LONG_MELANCHOLY_POTION;
    public static PotionType STRONG_MELANCHOLY_POTION;

    public static void register() {
        // 实际注册由 @SubscribeEvent 处理
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> event) {
        MELANCHOLY = new PotionMelancholy();
        event.getRegistry().register(MELANCHOLY);
        System.out.println("[FCPotions] 注册药水效果: 忧郁");
    }

    @SubscribeEvent
    public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
        // 普通忧郁药水 (3:00)
        MELANCHOLY_POTION = new PotionType(new PotionEffect(MELANCHOLY, 3600, 0))
                .setRegistryName(new ResourceLocation("fantasyclehj", "melancholy"));
        event.getRegistry().register(MELANCHOLY_POTION);

        // 延长忧郁药水 (8:00)
        LONG_MELANCHOLY_POTION = new PotionType(new PotionEffect(MELANCHOLY, 9600, 0))
                .setRegistryName(new ResourceLocation("fantasyclehj", "long_melancholy"));
        event.getRegistry().register(LONG_MELANCHOLY_POTION);

        // 强化忧郁药水 II (1:30)
        STRONG_MELANCHOLY_POTION = new PotionType(new PotionEffect(MELANCHOLY, 1800, 1))
                .setRegistryName(new ResourceLocation("fantasyclehj", "strong_melancholy"));
        event.getRegistry().register(STRONG_MELANCHOLY_POTION);

        System.out.println("[FCPotions] 注册药水类型: 忧郁药水");
    }

    // 在游戏加载完成后注册酿造配方
    public static void registerBrewingRecipes() {
        // 平凡药水 + 忧郁玫瑰 → 忧郁药水
        PotionHelper.addMix(PotionTypes.MUNDANE, Item.getItemFromBlock(FCBlocks.blue_rose), MELANCHOLY_POTION);

        // 忧郁药水 + 红石 → 延长忧郁药水
        PotionHelper.addMix(MELANCHOLY_POTION, Items.REDSTONE, LONG_MELANCHOLY_POTION);

        // 忧郁药水 + 萤石粉 → 强化忧郁药水 II
        PotionHelper.addMix(MELANCHOLY_POTION, Items.GLOWSTONE_DUST, STRONG_MELANCHOLY_POTION);

        System.out.println("[FCPotions] 注册酿造配方: 忧郁药水");
    }
}