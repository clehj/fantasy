package fantasyclehj.init;

import fantasyclehj.block.*;
import fantasyclehj.registry.helper.BlockBuilder;
import fantasyclehj.registry.annotation.AutoRegister;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class FCBlocks {

    @AutoRegister(name = "crying_obsidian", tab = "building_blocks")
    public static final Block crying_obsidian = BlockBuilder.create(
            new CryingObsidianBlock(Material.ROCK),
            "crying_obsidian",
            CreativeTabs.BUILDING_BLOCKS,
            50.0F, 2000.0F, "pickaxe", 3, 10
    );

    @AutoRegister(name = "blue_rose", tab = "decorations")
    public static final Block blue_rose = BlockBuilder.create(
            new BlueRose(),
            "blue_rose",
            CreativeTabs.DECORATIONS,
            0F, 0F
    );

    @AutoRegister(name = "portal", tab = "misc", createItemBlock = false)
    public static final Block portal = BlockBuilder.create(
            new PortalBlock(Material.PORTAL),
            "portal",
            CreativeTabs.MISC,
            -1F, 6000000F, null, 0, 0
    );

    @AutoRegister(name = "item_spawner", tab = "fantasy")
    public static final Block item_spawner = BlockBuilder.create(
            new ItemSpawnerBlock(),
            "item_spawner",
            FCCreativeTab.FTY,
            20.0F,
            6000000.0F,
            "pickaxe",
            3,
            0
    );

    @AutoRegister(name = "exp_spawner", tab = "fantasy")
    public static final Block exp_spawner = BlockBuilder.create(
            new ExpSpawnerBlock(),
            "exp_spawner",
            FCCreativeTab.FTY,
            20.0F,
            6000000.0F,
            "pickaxe",
            3,
            0
    );
}