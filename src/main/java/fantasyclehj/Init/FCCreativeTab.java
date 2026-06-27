package fantasyclehj.Init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FCCreativeTab {
    public static final CreativeTabs FTY = new CreativeTabs(CreativeTabs.getNextID(), "fantasy")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {return new ItemStack(FCItems.obsidian_stick);}
    };
}
