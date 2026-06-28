package fantasyclehj.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FCCreativeTab {
    // 直接用字符串作为 ID，不要用 getNextID()
    public static final CreativeTabs FTY = new CreativeTabs("fantasy") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(FCItems.obsidian_stick);
        }

        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {
            return "Fantasy";  // 游戏里显示的名字
        }
    };
}