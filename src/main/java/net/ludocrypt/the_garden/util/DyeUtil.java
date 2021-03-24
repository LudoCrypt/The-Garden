package net.ludocrypt.the_garden.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.blocks.EdgingBlock;
import net.ludocrypt.the_garden.blocks.EdgingFaceBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DyeUtil {

	public final Identifier id;
	public final Block WHITE;
	public final Block ORANGE;
	public final Block MAGENTA;
	public final Block LIGHT_BLUE;
	public final Block YELLOW;
	public final Block LIME;
	public final Block PINK;
	public final Block GRAY;
	public final Block LIGHT_GRAY;
	public final Block CYAN;
	public final Block PURPLE;
	public final Block BLUE;
	public final Block BROWN;
	public final Block GREEN;
	public final Block RED;
	public final Block BLACK;
	public final Item.Settings itemSettings;

	public DyeUtil(Identifier id, Block WHITE, Block ORANGE, Block MAGENTA, Block LIGHT_BLUE, Block YELLOW, Block LIME, Block PINK, Block GRAY, Block LIGHT_GRAY, Block CYAN, Block PURPLE, Block BLUE, Block BROWN, Block GREEN, Block RED, Block BLACK, Item.Settings itemSettings) {
		this.id = id;
		this.WHITE = WHITE;
		this.ORANGE = ORANGE;
		this.MAGENTA = MAGENTA;
		this.LIGHT_BLUE = LIGHT_BLUE;
		this.YELLOW = YELLOW;
		this.LIME = LIME;
		this.PINK = PINK;
		this.GRAY = GRAY;
		this.LIGHT_GRAY = LIGHT_GRAY;
		this.CYAN = CYAN;
		this.PURPLE = PURPLE;
		this.BLUE = BLUE;
		this.BROWN = BROWN;
		this.GREEN = GREEN;
		this.RED = RED;
		this.BLACK = BLACK;
		this.itemSettings = itemSettings;
	}

	public void registerAll() {
		registerBlocks();
		registerItems();
	}

	public void registerBlocks() {
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "white_" + id.getPath()), WHITE);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "orange_" + id.getPath()), ORANGE);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "magenta_" + id.getPath()), MAGENTA);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "light_blue_" + id.getPath()), LIGHT_BLUE);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "yellow_" + id.getPath()), YELLOW);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "lime_" + id.getPath()), LIME);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "pink_" + id.getPath()), PINK);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "gray_" + id.getPath()), GRAY);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "light_gray_" + id.getPath()), LIGHT_GRAY);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "cyan_" + id.getPath()), CYAN);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "purple_" + id.getPath()), PURPLE);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "blue_" + id.getPath()), BLUE);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "brown_" + id.getPath()), BROWN);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "green_" + id.getPath()), GREEN);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "red_" + id.getPath()), RED);
		Registry.register(Registry.BLOCK, new Identifier(id.getNamespace(), "black_" + id.getPath()), BLACK);
	}

	public void registerItems() {
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "white_" + id.getPath()), new BlockItem(WHITE, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "orange_" + id.getPath()), new BlockItem(ORANGE, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "magenta_" + id.getPath()), new BlockItem(MAGENTA, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "light_blue_" + id.getPath()), new BlockItem(LIGHT_BLUE, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "yellow_" + id.getPath()), new BlockItem(YELLOW, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "lime_" + id.getPath()), new BlockItem(LIME, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "pink_" + id.getPath()), new BlockItem(PINK, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "gray_" + id.getPath()), new BlockItem(GRAY, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "light_gray_" + id.getPath()), new BlockItem(LIGHT_GRAY, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "cyan_" + id.getPath()), new BlockItem(CYAN, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "purple_" + id.getPath()), new BlockItem(PURPLE, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "blue_" + id.getPath()), new BlockItem(BLUE, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "brown_" + id.getPath()), new BlockItem(BROWN, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "green_" + id.getPath()), new BlockItem(GREEN, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "red_" + id.getPath()), new BlockItem(RED, itemSettings));
		Registry.register(Registry.ITEM, new Identifier(id.getNamespace(), "black_" + id.getPath()), new BlockItem(BLACK, itemSettings));
	}

	public static DyeUtil of(String id, Settings base, Item.Settings itemSettings) {
		return new DyeUtil(TheGarden.id(id), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.WHITE)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.ORANGE)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.MAGENTA)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_BLUE)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.YELLOW)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIME)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PINK)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GRAY)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_GRAY)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.CYAN)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PURPLE)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLUE)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BROWN)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GREEN)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.RED)), new Block(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLACK)), itemSettings);
	}

	public static DyeUtil ofEdging(String id, Settings base, Item.Settings itemSettings) {
		return new DyeUtil(TheGarden.id(id), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.WHITE)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.ORANGE)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.MAGENTA)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_BLUE)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.YELLOW)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIME)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PINK)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GRAY)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_GRAY)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.CYAN)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PURPLE)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLUE)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BROWN)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GREEN)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.RED)), new EdgingBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLACK)), itemSettings);
	}

	public static DyeUtil ofEdgingFace(String id, Settings base, Item.Settings itemSettings) {
		return new DyeUtil(TheGarden.id(id), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.WHITE)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.ORANGE)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.MAGENTA)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_BLUE)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.YELLOW)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIME)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PINK)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GRAY)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.LIGHT_GRAY)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.CYAN)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.PURPLE)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLUE)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BROWN)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.GREEN)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.RED)), new EdgingFaceBlock(FabricBlockSettings.copyOf(base).materialColor(DyeColor.BLACK)), itemSettings);
	}

}
