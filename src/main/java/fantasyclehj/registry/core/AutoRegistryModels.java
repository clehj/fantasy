package fantasyclehj.registry.core;

import fantasyclehj.MainFC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MainFC.MODID)
public class AutoRegistryModels {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelReg(ModelRegistryEvent event) {
        System.out.println("[AutoRegistryModels] 开始注册模型...");
        registerAllModels();
    }

    private static void registerAllModels() {
        // 方法1：通过 FCBlocks 注册方块模型
        registerBlockModels();

        // 方法2：通过 FCItems 注册物品模型
        registerItemModels();
    }

    private static void registerBlockModels() {
        try {
            Class<?> blocksClass = Class.forName("fantasyclehj.init.FCBlocks");
            System.out.println("[AutoRegistryModels] 扫描 FCBlocks...");
            for (Field field : blocksClass.getDeclaredFields()) {
                if (Block.class.isAssignableFrom(field.getType())) {
                    Block block = (Block) field.get(null);
                    if (block == null) continue;

                    Item item = Item.getItemFromBlock(block);
                    if (item != null && item.getRegistryName() != null) {
                        registerModel(item);
                        System.out.println("[AutoRegistryModels] 注册方块模型: " + item.getRegistryName());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[AutoRegistryModels] 注册 FCBlocks 模型失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerItemModels() {
        try {
            Class<?> itemsClass = Class.forName("fantasyclehj.init.FCItems");
            System.out.println("[AutoRegistryModels] 扫描 FCItems...");
            for (Field field : itemsClass.getDeclaredFields()) {
                if (Item.class.isAssignableFrom(field.getType())) {
                    Item item = (Item) field.get(null);
                    if (item == null) continue;

                    // 检查是否有注册名
                    if (item.getRegistryName() != null) {
                        registerModel(item);
                        System.out.println("[AutoRegistryModels] 注册物品模型: " + item.getRegistryName());
                    } else {
                        System.out.println("[AutoRegistryModels] 跳过未注册物品: " + field.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[AutoRegistryModels] 注册 FCItems 模型失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void registerModel(Item item) {
        try {
            if (item.getRegistryName() == null) {
                System.err.println("[AutoRegistryModels] 无法注册模型: RegistryName 为 null");
                return;
            }
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory")
            );
        } catch (Exception e) {
            System.err.println("[AutoRegistryModels] 注册模型失败: " + item.getRegistryName());
            e.printStackTrace();
        }
    }
}