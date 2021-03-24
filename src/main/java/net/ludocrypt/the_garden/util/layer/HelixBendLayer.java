package net.ludocrypt.the_garden.util.layer;

import java.util.function.Predicate;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.Layer;

import net.minecraft.util.math.Direction;

public class HelixBendLayer implements Layer {

	private final double height;
	private final double radius;
	protected final Direction.Axis axis;

	public HelixBendLayer(double height, double radius, Direction.Axis axis) {
		this.height = height;
		this.radius = radius;
		this.axis = axis;
	}

	public static HelixBendLayer of(double height, double radius, Direction.Axis axis) {
		return new HelixBendLayer(height, radius, axis);
	}

	@Override
	public Position modifyMax(Shape shape) {
		Position pos = shape.max();

		switch (axis) {
		case X:
			pos.setY(pos.getY() + radius);
			pos.setZ(pos.getZ() + radius);
			break;
		case Z:
			pos.setX(pos.getX() + radius);
			pos.setY(pos.getY() + radius);
			break;
		default:
			pos.setX(pos.getX() + radius);
			pos.setZ(pos.getZ() + radius);
			break;
		}

		return pos;
	}

	@Override
	public Position modifyMin(Shape shape) {
		Position pos = shape.min();

		switch (axis) {
		case X:
			pos.setY(pos.getY() - radius);
			pos.setZ(pos.getZ() - radius);
			break;
		case Z:
			pos.setX(pos.getX() - radius);
			pos.setY(pos.getY() - radius);
			break;
		default:
			pos.setX(pos.getX() - radius);
			pos.setZ(pos.getZ() - radius);
			break;
		}

		return pos;
	}

	@Override
	public Predicate<Position> modifyEquation(Shape shape) {
		return (pos) -> {

			double turn = getTurn(pos);

			switch (axis) {
			case X:
				pos.setY(pos.getY() + (Math.cos(Math.toRadians(turn)) * radius));
				pos.setZ(pos.getZ() + (Math.sin(Math.toRadians(turn)) * radius));
				break;
			case Z:
				pos.setX(pos.getX() + (Math.cos(Math.toRadians(turn)) * radius));
				pos.setY(pos.getY() + (Math.sin(Math.toRadians(turn)) * radius));
				break;
			default:
				pos.setX(pos.getX() + (Math.cos(Math.toRadians(turn)) * radius));
				pos.setZ(pos.getZ() + (Math.sin(Math.toRadians(turn)) * radius));
				break;
			}

			return shape.equation().test(pos);
		};
	}

	protected double getTurn(Position pos) {
		double y = pos.getY();
		switch (axis) {
		case X:
			y = pos.getX();
			break;
		case Z:
			y = pos.getZ();
			break;
		default:
			break;
		}
		double turn = ((y % height) / height) * 360;
		return turn;
	}

}
