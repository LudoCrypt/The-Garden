package net.ludocrypt.the_garden.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IvoryNoteBlock extends NoteBlock {

	public IvoryNoteBlock(AbstractBlock.Settings settings) {
		super(settings);
	}

	@Override
	public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
		double note = state.get(NOTE);
		double pitch = Math.pow(2.0D, (note - 24.0D) / 24.0D);
		world.playSound(null, pos, state.get(INSTRUMENT).getSound(), SoundCategory.RECORDS, 3.0F, (float) pitch);
		world.addParticle(ParticleTypes.NOTE, pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, note / 24.0D, 0.0D, 0.0D);
		return true;
	}

}
