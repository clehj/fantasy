# Fantasy Mod 开发框架

## 📖 概述

这是一个基于 **Minecraft 1.12.2 Forge** 的 Mod 开发框架，核心特点是**自动化注册**。你只需要在字段上添加 `@AutoRegister` 注解，框架会自动完成方块、物品、ItemBlock 的注册和模型绑定。

---

## 🚀 快速开始

### 1. 添加新方块

在 `FCBlocks.java` 中添加一个静态字段，使用 `@AutoRegister` 注解：

```java
@AutoRegister(name = "my_block", tab = "building_blocks")
public static final Block myBlock = BlockBuilder.create(
        new MyBlock(Material.ROCK),
        "my_block",
        CreativeTabs.BUILDING_BLOCKS,
        5.0F,
        10.0F,
        "pickaxe",
        2,
        0
);
```

#### 方块类示例

```java
package fantasyclehj.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class MyBlock extends Block {
    public MyBlock(Material materialIn) {
        super(materialIn);
        // 在这里设置方块特有的属性
        // 注意：不要在这里设置 CreativeTab，由注解统一管理
    }
}
```

#### 不需要工具和光照的方块（如植物）

```java
@AutoRegister(name = "my_flower", tab = "decorations")
public static final Block myFlower = BlockBuilder.create(
    new MyFlower(),
    "my_flower",
    CreativeTabs.DECORATIONS,
    0F,
    0F
);
```

---

### 2. 添加新物品

在 `FCItems.java` 中添加一个静态字段，使用 `@AutoRegister` 注解：

```java
@AutoRegister(name = "my_item", tab = "fantasy")
public static final Item myItem = new Item().setTranslationKey("my_item");
```

#### 自定义物品类示例

```java
@AutoRegister(name = "my_custom_item", tab = "fantasy")
public static final Item myCustomItem = new MyCustomItem();
```

自定义物品类中只需要设置 `setTranslationKey` 和 `setRegistryName`：

```java
package fantasyclehj.item;

import net.minecraft.item.Item;

public class MyCustomItem extends Item {
    public MyCustomItem() {
        this.setTranslationKey("my_custom_item");
        this.setRegistryName("my_custom_item");
        // 不要在这里设置 setCreativeTab，由注解统一管理
    }
}
```

#### 工具类示例

```java
@AutoRegister(name = "my_sword", tab = "combat")
public static final Item mySword = 
    new ItemSword(ToolMaterial.DIAMOND)
        .setTranslationKey("my_sword");
```

---

### 3. 添加新命令

在 `FCCommands.java` 中直接注册命令：

```java
package fantasyclehj.init;

import fantasyclehj.command.MyCommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class FCCommands {
    public static void register(FMLServerStartingEvent event) {
        event.registerServerCommand(new MyCommand("mycmd", "/mycmd"));
    }
}
```

命令类继承 `fantasyclehj.registry.template.Command`：

```java
package fantasyclehj.command;

import fantasyclehj.registry.template.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class MyCommand extends Command {
    public MyCommand(String name, String usage) {
        super(name, usage);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // 命令逻辑
    }
}
```

---

### 4. 可用的标签页 ID

在 `@AutoRegister(tab = "...")` 中使用以下 ID：

| ID | 对应标签页 |
|----|-----------|
| `"building_blocks"` | 建筑方块 |
| `"decorations"` | 装饰方块 |
| `"redstone"` | 红石 |
| `"transportation"` | 交通运输 |
| `"misc"` | 杂项 |
| `"search"` | 搜索 |
| `"food"` | 食物 |
| `"tools"` | 工具 |
| `"combat"` | 战斗 |
| `"brewing"` | 酿造 |
| `"materials"` | 材料 |
| `"fantasy"` | 自定义 "幻想大厅" |

---

### 5. 自定义创造模式标签页

在 `FCCreativeTab.java` 中修改：

```java
package fantasyclehj.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FCCreativeTab {
    public static final CreativeTabs FTY = new CreativeTabs("fantasy") {
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(FCItems.obsidian_stick);
        }

        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {
            return "Fantasy";
        }
    };
}
```

在 `CreativeTabHelper.java` 中添加映射：

```java
static {
    // ... 其他映射
    TAB_MAP.put("fantasy", FCCreativeTab.FTY);
    // 自定义标签页
    TAB_MAP.put("my_tab", MyCreativeTab.MY_TAB);
}
```

---

### 6. 语言文件（本地化）

在 `src/main/resources/assets/fantasyclehj/lang/` 目录下创建语言文件。

#### zh_CN.lang（中文）

```properties
# 物品
item.obsidian_stick.name=黑曜石棒
item.oh_diamond_sword.name=黑曜石钻石剑
item.oh_diamond_pickaxe.name=黑曜石钻石镐

# 方块
tile.crying_obsidian.name=哭泣的黑曜石
tile.blue_rose.name=忧郁玫瑰

# 创造标签页
itemGroup.fantasy=幻想大厅
```

#### en_US.lang（英文）

```properties
# Items
item.obsidian_stick.name=Obsidian Stick
item.oh_diamond_sword.name=Obsidian Diamond Sword
item.oh_diamond_pickaxe.name=Obsidian Diamond Pickaxe

# Blocks
tile.crying_obsidian.name=Crying Obsidian
tile.blue_rose.name=Blue Rose

# Creative Tab
itemGroup.fantasy=Fantasy Hall
```

**⚠️ 重要**：文件编码必须是 **UTF-8**（无 BOM）。

---

### 7. 模型文件

#### 物品模型

路径：`assets/fantasyclehj/models/item/物品名.json`

普通物品：
```json
{
    "parent": "item/generated",
    "textures": {
        "layer0": "fantasyclehj:items/物品名"
    }
}
```

工具类物品：
```json
{
    "parent": "item/handheld",
    "textures": {
        "layer0": "fantasyclehj:items/剑名"
    }
}
```

#### 方块模型

路径：`assets/fantasyclehj/models/block/方块名.json`

```json
{
    "parent": "block/cube_all",
    "textures": {
        "all": "fantasyclehj:blocks/方块名"
    }
}
```

#### 方块物品模型

路径：`assets/fantasyclehj/models/item/方块名.json`

```json
{
    "parent": "fantasyclehj:block/方块名"
}
```

---

### 8. 纹理文件

纹理放在对应目录：

| 类型 | 路径 |
|------|------|
| 物品纹理 | `assets/fantasyclehj/textures/items/物品名.png` |
| 方块纹理 | `assets/fantasyclehj/textures/blocks/方块名.png` |

---

### 9. 不需要 ItemBlock 的方块

某些方块（如传送门）不需要对应的物品，设置 `createItemBlock = false`：

```java
@AutoRegister(name = "portal", tab = "misc", createItemBlock = false)
public static final Block portal = BlockBuilder.create(参数);
```

---

## 📁 项目结构

```
src/main/
└── java/fantasyclehj/
    ├── MainFC.java                         # 主类
    │
    ├── block/                              # 方块类
    │   ├── BlueRose.java
    │   ├── CryingObsidianBlock.java
    │   └── PortalBlock.java
    │
    ├── item/                               # 物品类
    │   ├── FCDiamondSword.java
    │   ├── FCDiamondPickaxe.java
    │   ├── FCDiamondAxe.java
    │   ├── FCDiamondShovel.java
    │   └── FCDiamondHoe.java
    │
    ├── command/                            # 命令类
    │   ├── CommandFcGive.java
    │   └── CommandTeleportToDimension.java
    │
    ├── dimension/                          # 维度
    │   ├── FantasyClehjDO.java
    │   └── FCDOChunkGenerator.java
    │
    ├── registry/                           # 注册系统
    │   ├── annotation/
    │   │   └── AutoRegister.java           # 注解定义
    │   ├── core/
    │   │   ├── AutoRegistry.java           # 自动注册核心
    │   │   └── AutoRegistryModels.java     # 模型自动注册
    │   ├── helper/
    │   │   ├── BlockBuilder.java           # 方块创建辅助
    │   │   ├── ItemBuilder.java            # 物品创建辅助
    │   │   └── CreativeTabHelper.java      # 标签页映射
    │   └── template/
    │       └── Command.java                # 命令抽象基类
    │
    └── init/                               # 注册入口
        ├── FCBlocks.java                   # 所有方块
        ├── FCItems.java                    # 所有物品
        ├── FCCommands.java                 # 命令注册
        ├── FCCreativeTab.java              # 自定义标签页
        ├── FCRecipes.java                  # 合成配方
        ├── FCWorldGen.java                 # 世界生成
        ├── FCBlockFlower.java              # 花朵基类
        └── DimensionRegistry.java          # 维度注册

src/main/resources/
└── assets/fantasyclehj/
    ├── lang/           # 语言文件
    ├── models/         # 模型文件
    └── textures/       # 纹理文件
```

---

## ⚠️ 注意事项

1. **不要重复设置 CreativeTab**：在物品/方块类中不要调用 `setCreativeTab()`，由注解统一管理。
2. **语言文件编码必须是 UTF-8**：否则中文会乱码。
3. **注册名必须一致**：`@AutoRegister(name = "xxx")`、`setTranslationKey("xxx")` 中的名字必须相同。
4. **模型文件必须存在**：否则物品在游戏中会显示为紫黑方块。
5. **命令直接 new**：不再需要 `ACCreateCommand`，直接在 `FCCommands` 中 `new` 命令实例。

---

## 🐛 常见问题

### Q: 物品显示 `item.null.name`

**原因**：没有设置翻译键 `setTranslationKey()`

**解决**：

```java
@AutoRegister(name = "my_item", tab = "fantasy")
public static final Item myItem = new Item().setTranslationKey("my_item");
```

### Q: 物品出现在错误的标签页

**原因**：注解中的 `tab` 参数和代码中 `setCreativeTab` 冲突

**解决**：
1. 删除物品类中的 `setCreativeTab()` 调用
2. 检查 `CreativeTabHelper` 中是否有对应的映射

### Q: 方块没有纹理

**原因**：缺少模型文件或纹理文件

**解决**：
1. 检查模型文件是否存在
2. 检查纹理文件是否存在
3. 检查模型文件中的纹理路径是否正确

### Q: 重复注册警告

**原因**：`scanAndCollect` 被调用了多次

**解决**：确保只在 `MainFC.preInit` 中调用一次 `AutoRegistry.scanAndCollect()`

---

## 📦 依赖

- Minecraft Forge 1.12.2
- JDK 8+

---

## 📝 更新日志

### v1.0.0
- 初始版本
- 自动注册系统
- 自定义维度
- 自定义命令