package fantasyclehj.Init;

import fantasyclehj.Blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

import static fantasyclehj.Init.Auxiliary.ACCreateBlock.*;


public class FCBlocks {
    public static final Block crying_obsidian = createBlock(
            new CryingObsidianBlock(Material.ROCK),
            "crying_obsidian",
            CreativeTabs.BUILDING_BLOCKS,
            50.0F,
            2000.0F,
            "pickaxe",
            3,
            10
    );

    public static final Block blue_rose = createBlock(
            new BlueRose(),
            "blue_rose",
            CreativeTabs.DECORATIONS,
            0F,
            0F,
            null,
            0,
            0
    );

    public static final Block portal = createBlock(
            new PortalBlock(Material.PORTAL), // 确保 PortalBlock 已经定义
            "portal",
            CreativeTabs.MISC,
            -1F,
            6000000F,
            null,
            0,
            0
    );

    public static final Block gunpowder = new BlockGunpowder()
            .setHardness(0.0F)
            .setTranslationKey("gunpowder_block")
            .setRegistryName("gunpowder_block");

    public static final Block wet_gunpowder = new WetGunpowderBlock()
            .setTranslationKey("wet_gunpowder_block")
            .setRegistryName("wet_gunpowder_block");
}
