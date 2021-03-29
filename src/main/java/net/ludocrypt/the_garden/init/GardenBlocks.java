package net.ludocrypt.the_garden.init;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.terraformersmc.terraform.wood.block.TerraformStairsBlock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.blocks.InsulationBlock;
import net.ludocrypt.the_garden.blocks.MulchBlock;
import net.ludocrypt.the_garden.blocks.MulchLayerBlock;
import net.ludocrypt.the_garden.blocks.MulchPortalBlock;
import net.ludocrypt.the_garden.blocks.OSBBlock;
import net.ludocrypt.the_garden.blocks.OSBSlabBlock;
import net.ludocrypt.the_garden.blocks.OSBStairBlock;
import net.ludocrypt.the_garden.blocks.OSBWallBlock;
import net.ludocrypt.the_garden.blocks.TileBlock;
import net.ludocrypt.the_garden.blocks.entity.MulchPortalBlockEntity;
import net.ludocrypt.the_garden.util.DyeUtil;
import net.ludocrypt.the_garden.util.wood.WoodBlocks;
import net.ludocrypt.the_garden.util.wood.WoodColors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.RedstoneOreBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

// TODO: Check
public class GardenBlocks {

	private static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
	private static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();
	private static final Map<Identifier, BlockEntityType<?>> BLOCK_ENTITIES = new LinkedHashMap<>();
	private static final Map<Identifier, WoodBlocks> WOOD_BLOCKS = new LinkedHashMap<>();
	private static final Map<Identifier, DyeUtil> DYED_BLOCKS = new LinkedHashMap<>();

	public static final Block CRACKED_OBSIDIAN = add("cracked_obsidian", new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool()), ItemGroup.BUILDING_BLOCKS);
	public static final Block MULCH_PORTAL = add("mulch_portal", new MulchPortalBlock(FabricBlockSettings.copyOf(Blocks.END_PORTAL).dropsNothing()));
	public static final BlockEntityType<MulchPortalBlockEntity> MULCH_PORTAL_BLOCK_ENTITY = add("mulch_portal", MULCH_PORTAL, MulchPortalBlockEntity::new);

	// Point One
	public static final Block TILE = add("tile", new TileBlock(FabricBlockSettings.copyOf(Blocks.STONE).breakByTool(FabricToolTags.PICKAXES).requiresTool().materialColor(DyeColor.WHITE)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT = add("playdirt", new Block(FabricBlockSettings.copyOf(Blocks.DIRT).breakByTool(FabricToolTags.SHOVELS).materialColor(DyeColor.BROWN)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_GOLD_ORE = add("playdirt_gold_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_IRON_ORE = add("playdirt_iron_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.IRON_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_COAL_ORE = add("playdirt_coal_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.COAL_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_LAPIS_ORE = add("playdirt_lapis_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.LAPIS_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_DIAMOND_ORE = add("playdirt_diamond_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.DIAMOND_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_REDSTONE_ORE = add("playdirt_redstone_ore", new RedstoneOreBlock(FabricBlockSettings.copyOf(Blocks.REDSTONE_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block PLAYDIRT_EMERALD_ORE = add("playdirt_emerald_ore", new OreBlock(FabricBlockSettings.copyOf(Blocks.EMERALD_ORE).sounds(BlockSoundGroup.GRAVEL)), ItemGroup.BUILDING_BLOCKS);
	public static final Block MULCH_BLOCK = add("mulch_block", new MulchBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).breakByTool(FabricToolTags.SHOVELS).strength(0.2F).ticksRandomly().materialColor(DyeColor.BROWN)), ItemGroup.BUILDING_BLOCKS);
	public static final Block MULCH_LAYER_BLOCK = add("mulch_layer_block", new MulchLayerBlock(FabricBlockSettings.copyOf(MULCH_BLOCK).breakByTool(FabricToolTags.SHOVELS).materialColor(DyeColor.BROWN)), ItemGroup.BUILDING_BLOCKS);
	public static final Block OSB_BOARD = add("osb_board", new OSBBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(3.5F, 6.5F).breakByTool(FabricToolTags.AXES).materialColor(DyeColor.YELLOW)), ItemGroup.BUILDING_BLOCKS);
	public static final Block OSB_STAIRS = add("osb_stairs", new OSBStairBlock(OSB_BOARD, FabricBlockSettings.copyOf(OSB_BOARD)), ItemGroup.BUILDING_BLOCKS);
	public static final Block OSB_SLAB = add("osb_slab", new OSBSlabBlock(FabricBlockSettings.copyOf(OSB_BOARD)), ItemGroup.BUILDING_BLOCKS);
	public static final Block OSB_WALL = add("osb_wall", new OSBWallBlock(FabricBlockSettings.copyOf(OSB_BOARD)), ItemGroup.DECORATIONS);
	public static final Block WHITE_INSULATION = add("white_insulation", new InsulationBlock(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL).breakByTool(FabricToolTags.SHEARS).requiresTool().materialColor(DyeColor.WHITE), 3, GardenParticles.WHITE_INSULATION), ItemGroup.BUILDING_BLOCKS);
	public static final Block BROWN_INSULATION = add("brown_insulation", new InsulationBlock(FabricBlockSettings.copyOf(Blocks.BROWN_WOOL).breakByTool(FabricToolTags.SHEARS).requiresTool().materialColor(DyeColor.BROWN), 4, GardenParticles.BROWN_INSULATION), ItemGroup.BUILDING_BLOCKS);
	public static final Block GREEN_INSULATION = add("green_insulation", new InsulationBlock(FabricBlockSettings.copyOf(Blocks.GREEN_WOOL).breakByTool(FabricToolTags.SHEARS).requiresTool().materialColor(DyeColor.GREEN), 5, GardenParticles.GREEN_INSULATION), ItemGroup.BUILDING_BLOCKS);
	public static final Block PINK_INSULATION = add("pink_insulation", new InsulationBlock(FabricBlockSettings.copyOf(Blocks.PINK_WOOL).breakByTool(FabricToolTags.SHEARS).requiresTool().materialColor(DyeColor.PINK), 6, GardenParticles.PINK_INSULATION), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK = add("cork", new Block(FabricBlockSettings.copyOf(Blocks.WARPED_HYPHAE).strength(3.0F, 8.0F).breakByTool(FabricToolTags.AXES).materialColor(DyeColor.YELLOW)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_STAIRS = add("cork_stairs", new TerraformStairsBlock(CORK, FabricBlockSettings.copyOf(CORK)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_SLAB = add("cork_slab", new SlabBlock(FabricBlockSettings.copyOf(CORK)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_WALL = add("cork_wall", new WallBlock(FabricBlockSettings.copyOf(CORK)), ItemGroup.DECORATIONS);
	public static final Block CORK_BRICKS = add("cork_bricks", new Block(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).strength(4.0F, 9.5F).breakByTool(FabricToolTags.AXES).materialColor(DyeColor.YELLOW)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_BRICK_STAIRS = add("cork_brick_stairs", new TerraformStairsBlock(CORK_BRICKS, FabricBlockSettings.copyOf(CORK_BRICKS)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_BRICK_SLAB = add("cork_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(CORK_BRICKS)), ItemGroup.BUILDING_BLOCKS);
	public static final Block CORK_BRICK_WALL = add("cork_brick_wall", new WallBlock(FabricBlockSettings.copyOf(CORK_BRICKS)), ItemGroup.DECORATIONS);
	public static final Block PEA_GRAVEL = add("pea_gravel", new FallingBlock(FabricBlockSettings.copyOf(Blocks.GRAVEL).breakByTool(FabricToolTags.SHOVELS).strength(0.8F).materialColor(DyeColor.GRAY)), ItemGroup.BUILDING_BLOCKS);
	public static final DyeUtil EDGING = add("edging", DyeUtil.ofEdging("edging", FabricBlockSettings.copyOf(Blocks.OAK_FENCE), new FabricItemSettings()));
	public static final DyeUtil EDGING_FACE = add("edging_face", DyeUtil.ofEdgingFace("edging_face", FabricBlockSettings.copyOf(Blocks.OAK_FENCE), new FabricItemSettings()));

	// Woods
	public static WoodBlocks DEAD_TREE = add("dead_tree", WoodBlocks.generate("dead_tree", WoodColors.DEAD_TREE, false, () -> GardenBoats.DEAD_TREE_BOAT));

	private static <B extends Block, T extends BlockEntity> BlockEntityType<T> add(String name, B block, Supplier<T> supplier) {
		Identifier id = TheGarden.id(name);
		BlockEntityType<T> blockEntity = BlockEntityType.Builder.create(supplier, block).build(null);
		BLOCK_ENTITIES.put(id, blockEntity);
		return blockEntity;
	}

	private static <W extends WoodBlocks> W add(String name, W set) {
		WOOD_BLOCKS.put(TheGarden.id(name), set);
		return set;
	}

	private static <D extends DyeUtil> D add(String name, D set) {
		DYED_BLOCKS.put(TheGarden.id(name), set);
		return set;
	}

	private static <B extends Block> B add(String name, B block, ItemGroup tab) {
		return add(name, block, new BlockItem(block, new Item.Settings().group(tab)));
	}

	private static <B extends Block> B add(String name, B block, BlockItem item) {
		add(name, block);
		if (item != null) {
			item.appendBlocks(Item.BLOCK_ITEMS, item);
			ITEMS.put(TheGarden.id(name), item);
		}
		return block;
	}

	private static <B extends Block> B add(String name, B block) {
		BLOCKS.put(TheGarden.id(name), block);
		return block;
	}

	public static void init() {

		for (Identifier id : ITEMS.keySet()) {
			Registry.register(Registry.ITEM, id, ITEMS.get(id));
		}
		for (Identifier id : BLOCKS.keySet()) {
			Registry.register(Registry.BLOCK, id, BLOCKS.get(id));
		}
		for (Identifier id : BLOCK_ENTITIES.keySet()) {
			Registry.register(Registry.BLOCK_ENTITY_TYPE, id, BLOCK_ENTITIES.get(id));
		}
		for (Identifier id : WOOD_BLOCKS.keySet()) {
			WOOD_BLOCKS.get(id).register().registerItems();
		}
		for (Identifier id : DYED_BLOCKS.keySet()) {
			DYED_BLOCKS.get(id).registerAll();
		}

		registerCompostableBlocks();
		registerFlammableBlocks();
	}

	private static void registerCompostableBlocks() {
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(MULCH_BLOCK.asItem(), 1);
	}

	private static void registerFlammableBlocks() {
		FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

		registry.add(MULCH_BLOCK, 5, 5);
		registry.add(MULCH_LAYER_BLOCK, 5, 5);

		registry.add(OSB_BOARD, 5, 15);

		registry.add(CORK, 5, 10);
		registry.add(CORK_BRICKS, 5, 5);
	}
}
