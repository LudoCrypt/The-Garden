package net.ludocrypt.the_garden.client.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;

public class GardenSkies {

	public static SkyProperties pointOneSky = new PointOneSky();
	public static SkyProperties pointTwoSky = new PointTwoSky();

	@Environment(EnvType.CLIENT)
	public static class PointOneSky extends SkyProperties {
		public PointOneSky() {
			super(Float.NaN, false, SkyProperties.SkyType.NONE, true, false);
		}

		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			return color;
		}

		@Override
		public boolean useThickFog(int camX, int camY) {
			return true;
		}

		@Override
		public float[] getFogColorOverride(float skyAngle, float tickDelta) {
			return null;
		}

	}

	@Environment(EnvType.CLIENT)
	public static class PointTwoSky extends SkyProperties {
		public PointTwoSky() {
			super(Float.NaN, false, SkyProperties.SkyType.NONE, true, false);
		}

		@Override
		public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
			return color;
		}

		@Override
		public boolean useThickFog(int camX, int camY) {
			return true;
		}

		@Override
		public float[] getFogColorOverride(float skyAngle, float tickDelta) {
			return null;
		}

	}

}
