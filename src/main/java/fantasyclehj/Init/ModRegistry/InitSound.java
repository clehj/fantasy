package fantasyclehj.Init.ModRegistry;

import fantasyclehj.Blocks.BlockGunpowder;
import fantasyclehj.MainFC;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MainFC.MODID)
public class InitSound {

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        // 注册火药嘶嘶声
        ResourceLocation fizzleLocation = new ResourceLocation(MainFC.MODID, "gunpowder.fizzle");
        SoundEvent soundFizzle = new SoundEvent(fizzleLocation);
        soundFizzle.setRegistryName(fizzleLocation);
        event.getRegistry().register(soundFizzle);

        // 赋值给 BlockGunpowder 的静态变量
        BlockGunpowder.soundFizzle = soundFizzle;
    }
}