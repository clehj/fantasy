package fantasyclehj.Init;


import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import fantasyclehj.Items.*;

import javax.tools.Tool;

import static fantasyclehj.Init.Auxiliary.ACCreateItem.*;

public class FCItems {
    // 使用辅助方法创建物品

    Tool Material;

    public static final Item obsidian_stick = createItem("obsidian_stick", FCCreativeTab.FTY);

    public static final Item.ToolMaterial TOOL_MATERIAL_OBSIDIAN =
            EnumHelper.addToolMaterial("ObsidianStick",
                    5,
                    2000,
                    9.0F,
                    5F,
                    30).setRepairItem(new ItemStack(FCItems.obsidian_stick));

    public static final Item obsidianHandle_dsd = new FCDiamondSword("oh_diamond_sword",TOOL_MATERIAL_OBSIDIAN).setCreativeTab(FCCreativeTab.FTY);

    public static final Item obsidianHandle_dae = new FCDiamondAxe("oh_diamond_axe", Item.ToolMaterial.DIAMOND).setCreativeTab(FCCreativeTab.FTY);

    public static final Item obsidianHandle_dpe = new FCDiamondPickaxe("oh_diamond_pickaxe", Item.ToolMaterial.DIAMOND).setCreativeTab(FCCreativeTab.FTY);

    public static final Item obsidianHandle_dsl = new FCDiamondShovel("oh_diamond_shovel", Item.ToolMaterial.DIAMOND).setCreativeTab(FCCreativeTab.FTY);

    public static final Item obsidianHandle_dhe = new FCDiamondHoe("oh_diamond_hoe", Item.ToolMaterial.DIAMOND).setCreativeTab(FCCreativeTab.FTY);




}