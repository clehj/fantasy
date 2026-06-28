package fantasyclehj.potion;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMelancholy extends Potion {

    private static final ResourceLocation ICON = new ResourceLocation("fantasyclehj", "textures/gui/melancholy_icon.png");

    public PotionMelancholy() {
        super(false, 0x006994); // 深紫罗兰
        this.setPotionName("potion.melancholy");
        this.setRegistryName("fantasyclehj", "melancholy");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        // 每 40 tick (2秒) 触发一次效果
        int interval = 30 >> amplifier;
        return interval == 0 || duration % interval == 0;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // 只有在水里才回血
        if (entity.isInWater()) {
            float healAmount = 1.0F + amplifier; // I级回1颗心，II级回2颗心
            entity.heal(healAmount);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(ICON);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(ICON);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}