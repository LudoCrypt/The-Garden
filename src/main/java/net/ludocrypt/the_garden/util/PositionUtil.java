package net.ludocrypt.the_garden.util;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PositionUtil {

	public static Position getCenter(Shape shape) {
		Position min = shape.min();
		Position max = shape.max();
		return Position.of(min.getX() + ((max.getX() - min.getX()) / 2), min.getY() + ((max.getY() - min.getY()) / 2), min.getZ() + ((max.getZ() - min.getZ()) / 2));
	}

	public static Vec3d toVec3d(Position pos) {
		return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

	public static double distanceToStretched(Vec3d a, Vec3d b, double stretchX, double stretchY, double stretchZ) {
		double x = a.x - b.x;
		double y = a.y - b.y;
		double z = a.z - b.z;
		return MathHelper.sqrt(((x * x) / stretchX) + ((y * y) / stretchY) + ((z * z) / stretchZ));
	}

}
