package net.ludocrypt.the_garden.blocks;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.ludocrypt.the_garden.TheGarden;
import net.ludocrypt.the_garden.init.GardenBlocks;
import net.ludocrypt.the_garden.init.GardenParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class MulchBlock extends Block {

	public MulchBlock(Settings settings) {
		super(settings);

	}

	public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
		entity.handleFallDamage(distance, 0.8F);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, Entity entity) {
		spawnSteppingParticles(world, pos, entity);
	}

	public static void spawnSteppingParticles(World world, BlockPos pos, Entity entity) {
		Random random = world.getRandom();
		if (world.isAir(pos.up())) {
			if ((entity.getVelocity().getX() >= 0.2 || entity.getVelocity().getX() <= -0.2 || entity.getVelocity().getZ() >= 0.2 || entity.getVelocity().getZ() <= -0.2) && random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
				world.addParticle(GardenParticles.MULCH_PARTICLES.get(random.nextInt(GardenParticles.MULCH_PARTICLES.size())), entity.getX(), pos.getY() + random.nextDouble() + 0.5, entity.getZ(), -entity.getVelocity().getX(), random.nextDouble() * 3, -entity.getVelocity().getZ());
			}
		}
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(8) == 0) {
			if (world.getBlockState(pos.down()).getMaterial().equals(Material.SOIL)) {
				if (isWaterNearby(world, pos, 4)) {
					Iterator<BlockPos> i = BlockPos.iterate(pos.add(-5, -1, -5), pos.add(5, 2, 5)).iterator();
					while (i.hasNext()) {
						BlockPos blockPos = i.next();
						BlockState blockState = world.getBlockState(blockPos);
						if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
							if (blockState.getBlock() instanceof Fertilizable) {
								Fertilizable fertile = (Fertilizable) blockState.getBlock();
								if (fertile.canGrow(world, random, blockPos, blockState)) {
									fertile.grow(world, random, blockPos, blockState);
									world.spawnParticles(ParticleTypes.COMPOSTER, blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), random.nextInt(5) + 3, 0.1, 0.1, 0.1, random.nextDouble() / 100);
								}
							}
						}
					}
				}
			}
		}
	}

	private static boolean isWaterNearby(WorldView world, BlockPos pos, int search) {
		Iterator<BlockPos> i = BlockPos.iterate(pos.add(-search, 0, -search), pos.add(search, 1, search)).iterator();

		BlockPos blockPos;
		do {
			if (!i.hasNext()) {
				return false;
			}

			blockPos = i.next();
		} while (!world.getFluidState(blockPos).isIn(FluidTags.WATER));

		return true;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return use(state, world, pos, player, hand, hit);
	}

	public static ActionResult use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).getItem() instanceof ShovelItem && world.isAir(pos.up())) {
			if (state.isOf(GardenBlocks.MULCH_BLOCK)) {
				world.setBlockState(pos, GardenBlocks.MULCH_LAYER_BLOCK.getDefaultState().with(MulchLayerBlock.LAYERS, 7), 2);
			} else {
				if (state.get(MulchLayerBlock.LAYERS) > 1) {
					world.setBlockState(pos, state.with(MulchLayerBlock.LAYERS, state.get(MulchLayerBlock.LAYERS) - 1), 2);
				} else {
					world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				}
			}
			if (!world.isClient) {
				player.getStackInHand(hand).damage(1, player, ((p) -> {
					p.sendToolBreakStatus(hand);
				}));

				world.playSound(null, pos, SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 1.0F, MathHelper.nextFloat(world.getRandom(), 0.5F, 2F));

				LootContext.Builder builder = (new LootContext.Builder((ServerWorld) world)).parameter(LootContextParameters.ORIGIN, hit.getPos()).parameter(LootContextParameters.THIS_ENTITY, player);
				LootTable lootTable = world.getServer().getLootManager().getTable(TheGarden.id("gameplay/worm"));
				List<ItemStack> list = lootTable.generateLoot(builder.build(LootContextTypes.GIFT));
				Iterator<ItemStack> var7 = list.iterator();

				while (var7.hasNext()) {
					ItemStack itemStack = (ItemStack) var7.next();
					ItemEntity itemEntity = new ItemEntity(world, hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), itemStack);
					double d = player.getX() - pos.getX();
					double e = player.getY() - pos.getY();
					double f = player.getZ() - pos.getZ();
					itemEntity.setVelocity(d * 0.1D, e * 0.1D + Math.sqrt(Math.sqrt(d * d + e * e + f * f)) * 0.08D, f * 0.1D);
					world.spawnEntity(itemEntity);
				}
			}

			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

}
