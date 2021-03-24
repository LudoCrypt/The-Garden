package net.ludocrypt.the_garden.util.wood;

import net.minecraft.block.MaterialColor;

/*
 * Shamelessly stolen from Terrestria
 */
public class WoodColors {
	public static final WoodColors DEAD_TREE;

	static {
		DEAD_TREE = new WoodColors();
		DEAD_TREE.bark = MaterialColor.GRAY;
		DEAD_TREE.planks = MaterialColor.GRAY;
		DEAD_TREE.leaves = MaterialColor.GRAY;

	}

	public MaterialColor bark;
	public MaterialColor planks;
	public MaterialColor leaves = MaterialColor.FOLIAGE;
}
