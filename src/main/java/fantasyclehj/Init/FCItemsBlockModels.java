package fantasyclehj.Init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

import static fantasyclehj.Init.Auxiliary.ACCreateItem.createItemBlock;

public class FCItemsBlockModels {
    public static final ItemBlock crying_obsidian_item = createItemBlock(
            FCBlocks.crying_obsidian,
            "crying_obsidian",
            CreativeTabs.BUILDING_BLOCKS
    );

    public static final ItemBlock blue_rose_item = createItemBlock(
            FCBlocks.blue_rose,
            "blue_rose",
            CreativeTabs.DECORATIONS
    );




}
