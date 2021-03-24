package net.ludocrypt.the_garden.util.wood;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.TerraformBoatItem;
import com.terraformersmc.terraform.leaves.ComposterRecipes;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import com.terraformersmc.terraform.wood.block.StrippableLogBlock;
import com.terraformersmc.terraform.wood.block.TerraformButtonBlock;
import com.terraformersmc.terraform.wood.block.TerraformDoorBlock;
import com.terraformersmc.terraform.wood.block.TerraformPressurePlateBlock;
import com.terraformersmc.terraform.wood.block.TerraformStairsBlock;
import com.terraformersmc.terraform.wood.block.TerraformTrapdoorBlock;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.ludocrypt.the_garden.TheGarden;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

/*
 * Shamelessly stolen from Terrestria
 */
public class WoodBlocks {
	public Block log;
	public Block wood;
	public Block leaves;
	public Block planks;
	public SlabBlock slab;
	public TerraformStairsBlock stairs;
	public FenceBlock fence;
	public FenceGateBlock fenceGate;
	public TerraformDoorBlock door;
	public TerraformButtonBlock button;
	public TerraformPressurePlateBlock pressurePlate;
	public TerraformSignBlock sign;
	public TerraformWallSignBlock wallSign;
	public TerraformTrapdoorBlock trapdoor;
	public Block strippedLog;
	public Block strippedWood;
	public TerraformBoatItem boat;
	public Supplier<EntityType<TerraformBoatEntity>> boatType;

	public String name;
	public WoodColors colors;
	public FlammableBlockRegistry registry;
	public boolean hasLeaves;

	public WoodBlocks(Block log, Block wood, Block leaves, Block planks, SlabBlock slab, TerraformStairsBlock stairs, FenceBlock fence, FenceGateBlock fenceGate, TerraformDoorBlock door, TerraformButtonBlock button, TerraformPressurePlateBlock pressurePlate, TerraformSignBlock sign, TerraformWallSignBlock wallSign, TerraformTrapdoorBlock trapdoor, Block strippedLog, Block strippedWood, TerraformBoatItem boat, Supplier<EntityType<TerraformBoatEntity>> boatType, String name, WoodColors colors, FlammableBlockRegistry registry, boolean hasLeaves) {
		this.log = log;
		this.wood = wood;
		this.leaves = leaves;
		this.planks = planks;
		this.slab = slab;
		this.stairs = stairs;
		this.fence = fence;
		this.fenceGate = fenceGate;
		this.door = door;
		this.button = button;
		this.pressurePlate = pressurePlate;
		this.sign = sign;
		this.wallSign = wallSign;
		this.trapdoor = trapdoor;
		this.strippedLog = strippedLog;
		this.strippedWood = strippedWood;
		this.boat = boat;
		this.boatType = boatType;
		this.name = name;
		this.colors = colors;
		this.registry = registry;
		this.hasLeaves = hasLeaves;
	}

	public static WoodBlocks generate(String name, WoodColors colors, boolean hasLeaves, Supplier<EntityType<TerraformBoatEntity>> boatType) {
		return generate(name, colors, FlammableBlockRegistry.getDefaultInstance(), hasLeaves, boatType);
	}

	public static WoodBlocks generate(String name, WoodColors colors, FlammableBlockRegistry registry, boolean hasLeaves, Supplier<EntityType<TerraformBoatEntity>> boatType) {

		Block log;
		Block wood;
		Block leaves;
		Block planks;
		SlabBlock slab;
		TerraformStairsBlock stairs;
		FenceBlock fence;
		FenceGateBlock fenceGate;
		TerraformDoorBlock door;
		TerraformButtonBlock button;
		TerraformPressurePlateBlock pressurePlate;
		TerraformSignBlock sign;
		TerraformWallSignBlock wallSign;
		TerraformTrapdoorBlock trapdoor;
		Block strippedLog;
		Block strippedWood;
		TerraformBoatItem boat;

		planks = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		slab = new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		stairs = new TerraformStairsBlock(planks, FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		fence = new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		fenceGate = new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		door = new TerraformDoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		button = new TerraformButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_BUTTON).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		pressurePlate = new TerraformPressurePlateBlock(FabricBlockSettings.copyOf(Blocks.OAK_PRESSURE_PLATE).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		trapdoor = new TerraformTrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_TRAPDOOR).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));

		Identifier signTexture = TheGarden.id("entity/signs/" + name);

		sign = new TerraformSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_SIGN).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		wallSign = new TerraformWallSignBlock(signTexture, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));

		if (hasLeaves) {
			leaves = new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).breakByTool(FabricToolTags.HOES).materialColor(colors.leaves).allowsSpawning(WoodBlocks::canSpawnOnLeaves).suffocates(WoodBlocks::never).blockVision(WoodBlocks::never));
		} else {
			leaves = null;
		}

		strippedLog = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		strippedWood = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).materialColor(colors.planks).breakByTool(FabricToolTags.AXES));
		log = new StrippableLogBlock(() -> strippedLog, colors.planks, FabricBlockSettings.copyOf(Blocks.OAK_LOG).materialColor(colors.bark).breakByTool(FabricToolTags.AXES));
		wood = new StrippableLogBlock(() -> strippedWood, colors.bark, FabricBlockSettings.copyOf(Blocks.OAK_LOG).materialColor(colors.bark).breakByTool(FabricToolTags.AXES));

		boat = new TerraformBoatItem(boatType, new Item.Settings().maxCount(1).group(ItemGroup.TRANSPORTATION));

		return new WoodBlocks(log, wood, leaves, planks, slab, stairs, fence, fenceGate, door, button, pressurePlate, sign, wallSign, trapdoor, strippedLog, strippedWood, boat, boatType, name, colors, registry, hasLeaves);

	}

	public WoodBlocks register() {
		registerManufactured();

		if (hasLeaves || this.leaves != null) {
			register(name + "_leaves", new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).breakByTool(FabricToolTags.HOES).materialColor(colors.leaves).allowsSpawning(WoodBlocks::canSpawnOnLeaves).suffocates(WoodBlocks::never).blockVision(WoodBlocks::never)));
		}

		register("stripped_" + name + "_log", strippedLog);
		register("stripped_" + name + "_wood", strippedWood);
		register(name + "_log", log);
		register(name + "_wood", wood);

		this.addTreeFireInfo();

		return this;
	}

	public WoodBlocks registerManufactured() {
		register(name + "_planks", planks);
		register(name + "_slab", slab);
		register(name + "_stairs", stairs);
		register(name + "_fence", fence);
		register(name + "_fence_gate", fenceGate);
		register(name + "_door", door);
		register(name + "_button", button);
		register(name + "_pressure_plate", pressurePlate);
		register(name + "_trapdoor", trapdoor);

		register(name + "_sign", sign);
		register(name + "_wall_sign", wallSign);

		this.addManufacturedFireInfo();

		FuelRegistry.INSTANCE.add(this.fence, 300);
		FuelRegistry.INSTANCE.add(this.fenceGate, 300);

		return this;
	}

	public WoodBlocks registerItems() {

		registerBlockItem(name + "_log", this.log, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem(name + "_wood", this.wood, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem("stripped_" + name + "_log", this.strippedLog, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem("stripped_" + name + "_wood", this.strippedWood, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		if (this.leaves != null || this.hasLeaves) {
			registerBlockItem(name + "_leaves", this.leaves, new FabricItemSettings().group(ItemGroup.DECORATIONS));
		}
		registerBlockItem(name + "_planks", this.planks, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem(name + "_slab", this.slab, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem(name + "_stairs", this.stairs, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS));
		registerBlockItem(name + "_fence", this.fence, new FabricItemSettings().group(ItemGroup.DECORATIONS));
		registerBlockItem(name + "_fence_gate", this.fenceGate, new FabricItemSettings().group(ItemGroup.REDSTONE));
		registerBlockItem(name + "_door", this.door, new FabricItemSettings().group(ItemGroup.REDSTONE));
		registerBlockItem(name + "_button", this.button, new FabricItemSettings().group(ItemGroup.REDSTONE));
		registerBlockItem(name + "_pressure_plate", this.pressurePlate, new FabricItemSettings().group(ItemGroup.REDSTONE));
		registerBlockItem(name + "_trapdoor", this.trapdoor, new FabricItemSettings().group(ItemGroup.REDSTONE));
		registerSignItem(name + "_sign", this.sign, this.wallSign, new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS));

		registerItem(name + "_boat", boat);

		return this;
	}

	public void addTreeFireInfo() {
		registry.add(log, 5, 5);
		registry.add(strippedLog, 5, 5);

		if (wood != log) {
			registry.add(wood, 5, 5);
		}

		if (strippedWood != strippedLog) {
			registry.add(strippedWood, 5, 5);
		}

		if (leaves != null) {
			registry.add(leaves, 30, 60);
		}
	}

	public void addManufacturedFireInfo() {
		registry.add(planks, 5, 20);
		registry.add(slab, 5, 20);
		registry.add(stairs, 5, 20);
		registry.add(fence, 5, 20);
		registry.add(fenceGate, 5, 20);
	}

	private static <T extends Block> T register(String name, T block) {
		return Registry.register(Registry.BLOCK, TheGarden.id(name), block);
	}

	private static BlockItem registerBlockItem(String name, Block block, Item.Settings settings) {
		BlockItem item = new BlockItem(block, settings);
		item.appendBlocks(Item.BLOCK_ITEMS, item);

		ComposterRecipes.registerCompostableBlock(block);

		return Registry.register(Registry.ITEM, TheGarden.id(name), item);
	}

	private static SignItem registerSignItem(String name, Block standing, Block wall, Item.Settings settings) {
		return Registry.register(Registry.ITEM, TheGarden.id(name), new SignItem(settings, standing, wall));
	}

	private static <I extends Item> I registerItem(String name, I item) {
		return Registry.register(Registry.ITEM, TheGarden.id(name), item);
	}

	public static boolean never(BlockState state, BlockView world, BlockPos pos) {
		return false;
	}

	public static Boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
		return type == EntityType.OCELOT || type == EntityType.PARROT;
	}

}
