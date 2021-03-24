package net.ludocrypt.the_garden.util.layer;

import java.util.function.Predicate;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.Layer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;

import net.minecraft.util.math.Direction;

public class BendLayer implements Layer {

	protected final double arc;
	protected final double height;
	protected final Direction.Axis axis;

	protected Position minPos = Position.of(0, 0, 0);
	protected Position maxPos = Position.of(0, 0, 0);

	public BendLayer(double arc, double height, Direction.Axis axis) {
		this.arc = arc;
		this.height = height;
		this.axis = axis;
	}

	public static BendLayer of(double arc, double height, Direction.Axis axis) {
		return new BendLayer(arc, height, axis);
	}

	@Override
	public Position modifyMin(Shape shape) {

		shape.stream().forEachOrdered((pos) -> {
			RotateLayer layer = getRotateLayer(pos);
			Position min = layer.modifyMin(shape);

			if (min.getX() <= minPos.getX()) {
				minPos.setX(min.getX());
			}
			if (min.getY() <= minPos.getY()) {
				minPos.setY(min.getY());
			}
			if (min.getZ() <= minPos.getZ()) {
				minPos.setZ(min.getZ());
			}
		});

		return minPos;
	}

	@Override
	public Position modifyMax(Shape shape) {

		shape.stream().forEachOrdered((pos) -> {
			RotateLayer layer = getRotateLayer(pos);
			Position max = layer.modifyMax(shape);

			if (max.getX() >= maxPos.getX()) {
				maxPos.setX(max.getX());
			}
			if (max.getY() >= maxPos.getY()) {
				maxPos.setY(max.getY());
			}
			if (max.getZ() >= maxPos.getZ()) {
				maxPos.setZ(max.getZ());
			}
		});

		return maxPos;
	}

	@Override
	public Predicate<Position> modifyEquation(Shape shape) {
		return (pos) -> getRotateLayer(pos).modifyEquation(shape).test(pos);
	}

	protected RotateLayer getRotateLayer(Position pos) {
		return new RotateLayer(getRotation(pos));
	}

	protected double getDist(Position pos) {
		return Math.sqrt(pos.getX() * pos.getX() + pos.getY() + pos.getY() + pos.getZ() + pos.getZ());
	}

	protected Quaternion getRotation(Position pos) {
		double rotation = arc * (getDist(pos) / height);
		return Quaternion.of(axis.equals(Direction.Axis.X) ? rotation : 0, axis.equals(Direction.Axis.Y) ? rotation : 0, axis.equals(Direction.Axis.Z) ? rotation : 0, true);
	}

}
