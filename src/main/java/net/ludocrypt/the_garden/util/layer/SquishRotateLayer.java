package net.ludocrypt.the_garden.util.layer;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.terraformersmc.terraform.shapes.api.Position;
import com.terraformersmc.terraform.shapes.api.Quaternion;
import com.terraformersmc.terraform.shapes.api.Shape;
import com.terraformersmc.terraform.shapes.api.layer.Layer;
import com.terraformersmc.terraform.shapes.impl.layer.transform.RotateLayer;

import net.minecraft.util.math.Direction;

public class SquishRotateLayer implements Layer {

	protected final double arc;
	protected final double height;
	protected final Direction.Axis axis;

	protected Position minPos = Position.of(0, 0, 0);
	protected Position maxPos = Position.of(0, 0, 0);

	public SquishRotateLayer(double arc, double height, Direction.Axis axis) {
		this.arc = arc;
		this.height = height;
		this.axis = axis;
	}

	public static SquishRotateLayer of(double arc, double height, Direction.Axis axis) {
		return new SquishRotateLayer(arc, height, axis);
	}

	@Override
	public Position modifyMin(Shape shape) {
		List<Position> translatedVertices = Position.vertices(shape.max(), shape.min()).stream().map((pos) -> getRotateLayer(pos).modifyMin(shape)).collect(Collectors.toList());
		return Position.of(translatedVertices.stream().map(Position::getX).min(Double::compareTo).get(), translatedVertices.stream().map(Position::getY).min(Double::compareTo).get(), translatedVertices.stream().map(Position::getZ).min(Double::compareTo).get());
	}

	@Override
	public Position modifyMax(Shape shape) {
		List<Position> translatedVertices = Position.vertices(shape.max(), shape.min()).stream().map((pos) -> getRotateLayer(pos).modifyMax(shape)).collect(Collectors.toList());
		return Position.of(translatedVertices.stream().map(Position::getX).min(Double::compareTo).get(), translatedVertices.stream().map(Position::getY).min(Double::compareTo).get(), translatedVertices.stream().map(Position::getZ).min(Double::compareTo).get());
	}

	@Override
	public Predicate<Position> modifyEquation(Shape shape) {
		return (pos) -> getRotateLayer(pos).modifyEquation(shape).test(pos);
	}

	protected RotateLayer getRotateLayer(Position pos) {
		return new RotateLayer(getRotation(pos));
	}

	protected Quaternion getRotation(Position pos) {
		double rotation = (arc / height) * pos.getY();
		return Quaternion.of(axis.equals(Direction.Axis.X) ? rotation : 0, axis.equals(Direction.Axis.Y) ? rotation : 0, axis.equals(Direction.Axis.Z) ? rotation : 0, true);
	}

}
