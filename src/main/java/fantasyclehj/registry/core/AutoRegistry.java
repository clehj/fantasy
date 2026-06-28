package fantasyclehj.registry.core;

import fantasyclehj.MainFC;
import fantasyclehj.registry.helper.CreativeTabHelper;
import fantasyclehj.registry.annotation.AutoRegister;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MainFC.MODID)
public class AutoRegistry {

    // 存储所有需要注册的字段
    private static final List<Field> BLOCK_FIELDS = new ArrayList<>();
    private static final List<Field> ITEM_FIELDS = new ArrayList<>();

    // 存储需要创建 ItemBlock 的方块信息
    private static final Map<Block, String> ITEMBLOCK_TO_CREATE = new HashMap<>();
    private static final Map<Block, String> ITEMBLOCK_TABS = new HashMap<>();

    /**
     * 扫描并收集所有带有 @AutoRegister 注解的字段
     * 应该在预初始化阶段调用
     */
    public static void scanAndCollect(Class<?>... classes) {
        System.out.println("========= AutoRegistry.scanAndCollect 被调用！========");
        System.out.println("[AutoRegistry] 开始扫描类...");
        for (Class<?> clazz : classes) {
            System.out.println("[AutoRegistry] 扫描: " + clazz.getName());
            for (Field field : clazz.getDeclaredFields()) {
                // 只处理 public static final 字段
                if (!Modifier.isPublic(field.getModifiers()) ||
                        !Modifier.isStatic(field.getModifiers()) ||
                        !Modifier.isFinal(field.getModifiers())) {
                    continue;
                }

                AutoRegister annotation = field.getAnnotation(AutoRegister.class);
                if (annotation == null) continue;

                System.out.println("[AutoRegistry] 找到注解: " + field.getName());

                // 检查字段类型
                if (Block.class.isAssignableFrom(field.getType())) {
                    BLOCK_FIELDS.add(field);
                    System.out.println("[AutoRegistry] 添加方块: " + field.getName());
                } else if (Item.class.isAssignableFrom(field.getType())) {
                    ITEM_FIELDS.add(field);
                    System.out.println("[AutoRegistry] 添加物品: " + field.getName());
                }
            }
        }
        System.out.println("[AutoRegistry] 扫描完成，方块: " + BLOCK_FIELDS.size() + ", 物品: " + ITEM_FIELDS.size());
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        System.out.println("[AutoRegistry] 开始注册方块...");
        IForgeRegistry<Block> registry = event.getRegistry();

        for (Field field : BLOCK_FIELDS) {
            try {
                Block block = (Block) field.get(null);
                if (block == null) {
                    System.err.println("[AutoRegistry] 方块为空: " + field.getName());
                    continue;
                }

                AutoRegister annotation = field.getAnnotation(AutoRegister.class);
                String name = annotation.name().isEmpty() ? field.getName() : annotation.name();
                String tabId = annotation.tab();

                System.out.println("[AutoRegistry] 注册方块: " + name);

                if (block.getRegistryName() == null) {
                    block.setRegistryName(new ResourceLocation(MainFC.MODID, name));
                }

                String transKey = block.getTranslationKey();
                if (transKey == null || transKey.isEmpty() || transKey.startsWith("tile.null")) {
                    block.setTranslationKey(name);
                }

                // ★★★ 强制设置 CreativeTab ★★★
                CreativeTabs tab = CreativeTabHelper.getTab(tabId);
                block.setCreativeTab(tab);

                registry.register(block);
                System.out.println("[AutoRegistry] 方块注册成功: " + name + ", 标签页: " + tabId);

                if (annotation.createItemBlock()) {
                    ITEMBLOCK_TO_CREATE.put(block, name);
                    ITEMBLOCK_TABS.put(block, tabId);
                    System.out.println("[AutoRegistry] 标记需要创建 ItemBlock: " + name);
                }

            } catch (IllegalAccessException e) {
                System.err.println("[AutoRegistry] 注册方块失败: " + field.getName());
                e.printStackTrace();
            }
        }
        System.out.println("[AutoRegistry] 方块注册完成");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        System.out.println("[AutoRegistry] 开始注册物品...");
        IForgeRegistry<Item> registry = event.getRegistry();

        // ★★★ 只保留这一段注册代码！删除重复的部分 ★★★
        for (Field field : ITEM_FIELDS) {
            try {
                Item item = (Item) field.get(null);
                if (item == null) {
                    System.err.println("[AutoRegistry] 物品为空: " + field.getName());
                    continue;
                }

                AutoRegister annotation = field.getAnnotation(AutoRegister.class);
                String name = annotation.name().isEmpty() ? field.getName() : annotation.name();
                String tabId = annotation.tab();

                System.out.println("[AutoRegistry] 注册物品: " + name);

                // 设置注册名
                if (item.getRegistryName() == null) {
                    item.setRegistryName(new ResourceLocation(MainFC.MODID, name));
                }

                // 设置翻译键（修复 item.null.name 问题）
                String transKey = item.getTranslationKey();
                if (transKey == null || transKey.isEmpty() || "item.null".equals(transKey) || "item.null.name".equals(transKey)) {
                    item.setTranslationKey(name);
                    System.out.println("[AutoRegistry] 设置翻译键: " + name);
                }

                // ★★★ 强制使用注解的 CreativeTab ★★★
                CreativeTabs tab = CreativeTabHelper.getTab(tabId);
                if (tab != null) {
                    item.setCreativeTab(tab);
                    System.out.println("[AutoRegistry] 设置标签页: " + tabId + " -> " + tab.getTabLabel());
                }

                registry.register(item);
                System.out.println("[AutoRegistry] 物品注册成功: " + name);

            } catch (IllegalAccessException e) {
                System.err.println("[AutoRegistry] 注册物品失败: " + field.getName());
                e.printStackTrace();
            }
        }

        // 自动创建并注册 ItemBlock
        System.out.println("[AutoRegistry] 开始注册 ItemBlock...");
        for (Map.Entry<Block, String> entry : ITEMBLOCK_TO_CREATE.entrySet()) {
            Block block = entry.getKey();
            String name = entry.getValue();
            String tabId = ITEMBLOCK_TABS.get(block);

            System.out.println("[AutoRegistry] 注册 ItemBlock: " + name);

            ItemBlock itemBlock = new ItemBlock(block);
            itemBlock.setRegistryName(new ResourceLocation(MainFC.MODID, name));
            itemBlock.setTranslationKey(name);

            CreativeTabs tab = CreativeTabHelper.getTab(tabId);
            if (tab != null) {
                itemBlock.setCreativeTab(tab);
            }

            registry.register(itemBlock);
            System.out.println("[AutoRegistry] ItemBlock 注册成功: " + name);
        }

        ITEMBLOCK_TO_CREATE.clear();
        ITEMBLOCK_TABS.clear();
        System.out.println("[AutoRegistry] 物品注册完成");
    }
}