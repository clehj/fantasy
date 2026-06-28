package fantasyclehj.init;

import fantasyclehj.registry.annotation.AutoRegister;
import fantasyclehj.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class FCItems {

    // 工具材料（先声明，后面在静态块中设置修复材料）
    public static final Item.ToolMaterial TOOL_MATERIAL_OBSIDIAN =
            EnumHelper.addToolMaterial("ObsidianStick",
                    5,
                    2000,
                    9.0F,
                    5F,
                    30);

    @AutoRegister(name = "obsidian_stick", tab = "fantasy")
    public static final Item obsidian_stick = new Item().setTranslationKey("obsidian_stick");

    @AutoRegister(name = "oh_diamond_sword", tab = "fantasy")
    public static final Item obsidianHandle_dsd =
            new FCDiamondSword("oh_diamond_sword", TOOL_MATERIAL_OBSIDIAN);

    @AutoRegister(name = "oh_diamond_axe", tab = "fantasy")
    public static final Item obsidianHandle_dae =
            new FCDiamondAxe("oh_diamond_axe", TOOL_MATERIAL_OBSIDIAN);

    @AutoRegister(name = "oh_diamond_pickaxe", tab = "fantasy")
    public static final Item obsidianHandle_dpe =
            new FCDiamondPickaxe("oh_diamond_pickaxe", TOOL_MATERIAL_OBSIDIAN);

    @AutoRegister(name = "oh_diamond_shovel", tab = "fantasy")
    public static final Item obsidianHandle_dsl =
            new FCDiamondShovel("oh_diamond_shovel", TOOL_MATERIAL_OBSIDIAN);

    @AutoRegister(name = "oh_diamond_hoe", tab = "fantasy")
    public static final Item obsidianHandle_dhe =
            new FCDiamondHoe("oh_diamond_hoe", TOOL_MATERIAL_OBSIDIAN);

    static {
        // 设置修复材料（必须在 obsidian_stick 初始化之后）
        TOOL_MATERIAL_OBSIDIAN.setRepairItem(new ItemStack(obsidian_stick));
    }
}