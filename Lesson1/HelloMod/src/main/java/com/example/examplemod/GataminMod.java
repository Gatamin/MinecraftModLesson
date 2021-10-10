package com.example.examplemod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

// @Mod Modというアノテーションです。このクラスがModの本体である事を宣言する役割があります。
@Mod(GataminMod.MOD_ID)
public class GataminMod {
    public static final String MOD_ID = "gataminmod";

    // Modを読み込む時に最初にコールされるとこ
    // コンストラクタといいます
    public GataminMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Blocks.register(eventBus);
        Items.register(eventBus);
    }

    //全Blockの情報を扱うクラスです
    public static class Blocks {
        //BLOCKS という大枠で全ブロックの情報を持ちます。
        private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);

        //今回、新しく追加するブロックの諸々の情報を定義し、かつ上記の全ブロックの情報に登録してあげます。
        public static final RegistryObject<Block> GATAMIN_BLOCK = BLOCKS.register("gatamin_block", new Supplier<Block>() {
            @Override
            public Block get() {
                return new Block(AbstractBlock.Properties
                        .of(Material.METAL, MaterialColor.METAL) //燃えるかどうか、地図上での表示色など
                        .requiresCorrectToolForDrops() //ブロックとして取るためにはシルクタッチみたいなのを必要とする場合
                        .strength(2.0F, 6.0F) //硬さと爆破耐性
                        .sound(SoundType.METAL) //ブロックの設置や上に乗ったとき、壊したときの音
                        .harvestTool(ToolType.AXE) //回収に必要な道具指定。AXEで斧、SHOVELでしゃべる、HOEでクワ
                        .harvestLevel(-1) //回収に必要な道具の素材、木や金が0, 1が石, 2が鉄, 3がダイヤ
                );
            }
        });

        //Modを読み込むタイミングにこのregisterが呼び出されるので、全ブロックの情報を返します。
        public static void register(IEventBus eventBus) {
            BLOCKS.register(eventBus);
        }
    }

    //全アイテムの情報を扱うクラスです
    public static class Items {
        //Items という大枠で手に持てる形の全アイテム情報を持ちます。
        private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

        //今回新しく追加するブロックのアイテム状態のものでブロックとして全アイテムの情報に登録します。
        public static final RegistryObject<Item> GATAMIN_BLOCK = ITEMS.register("gatamin_block", new Supplier<Item>() {
            @Override
            public Item get() {
                return new BlockItem(Blocks.GATAMIN_BLOCK.get(), new Item.Properties().tab(ItemGroup.TAB_BUILDING_BLOCKS));
            }
        });

        //ほぼ同じ形でインゴットという形で、全アイテムの情報に登録します。
        public static final RegistryObject<Item> GATAMIN_INGOT = ITEMS.register("gatamin_ingot", new Supplier<Item>() {
            @Override
            public Item get() {
                return new Item(new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
            }
        });

        //Modを読むこむタイミングにこのregisterが呼び出されるので、全アイテムの情報を返します。
        public static void register(IEventBus eventBus) {
            ITEMS.register(eventBus);
        }
    }

}
