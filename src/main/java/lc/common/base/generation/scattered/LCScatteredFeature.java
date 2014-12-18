package lc.common.base.generation.scattered;

import java.util.Random;

import lc.common.util.math.Vector3;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

/**
 * LanteaCraft scattered feature stub class. Used for the creation of scattered
 * structures using addComponentParts.
 *
 * @author AfterLifeLochie
 */
public abstract class LCScatteredFeature extends StructureComponent {

	/** The size of the bounding box for this feature in the X axis */
	protected int scatteredFeatureSizeX;
	/** The size of the bounding box for this feature in the Y axis */
	protected int scatteredFeatureSizeY;
	/** The size of the bounding box for this feature in the Z axis */
	protected int scatteredFeatureSizeZ;
	/** The h-position store */
	protected int hPos = -1;

	/** Default constructor. */
	public LCScatteredFeature() {
	}

	/**
	 * Create a new structure component
	 *
	 * @param rng
	 *            The random number generator
	 * @param x
	 *            The x-coordinate
	 * @param y
	 *            The y-coordinate
	 * @param z
	 *            The z-coordinate
	 * @param sx
	 *            The size on the x-axis
	 * @param sy
	 *            The size on the y-axis
	 * @param sz
	 *            The size on the z-axis
	 */
	protected LCScatteredFeature(Random rng, int x, int y, int z, int sx, int sy, int sz) {
		super(0);
		scatteredFeatureSizeX = sx;
		scatteredFeatureSizeY = sy;
		scatteredFeatureSizeZ = sz;
		coordBaseMode = rng.nextInt(4);

		switch (coordBaseMode) {
		case 0:
		case 2:
			boundingBox = new StructureBoundingBox(x, y, z, x + sx - 1, y + sy - 1, z + sz - 1);
			break;
		default:
			boundingBox = new StructureBoundingBox(x, y, z, x + sz - 1, y + sy - 1, z + sx - 1);
		}
	}

	@Override
	protected void func_143012_a(NBTTagCompound tag) {
		tag.setInteger("Width", scatteredFeatureSizeX);
		tag.setInteger("Height", scatteredFeatureSizeY);
		tag.setInteger("Depth", scatteredFeatureSizeZ);
		tag.setInteger("CoordMode", coordBaseMode);
		tag.setInteger("HPos", hPos);
	}

	@Override
	protected void func_143011_b(NBTTagCompound tag) {
		scatteredFeatureSizeX = tag.getInteger("Width");
		scatteredFeatureSizeY = tag.getInteger("Height");
		scatteredFeatureSizeZ = tag.getInteger("Depth");
		coordBaseMode = tag.getInteger("CoordMode");
		hPos = tag.getInteger("HPos");
	}

	/**
	 * Determine if placement is allowed. Calculates the bottom layer of the
	 * bounding box so that it sits on the surface correctly.
	 *
	 * @param world
	 *            The world
	 * @param sbb
	 *            The bounding box
	 * @param offset
	 *            The height offset
	 * @return If the placement is legal
	 */
	protected boolean canPlaceAt(World world, StructureBoundingBox sbb, int offset) {
		if (hPos >= 0)
			return true;
		else {
			int j = 0, k = 0;
			for (int z = boundingBox.minZ; z <= boundingBox.maxZ; ++z)
				for (int x = boundingBox.minX; x <= boundingBox.maxX; ++x)
					if (sbb.isVecInside(x, 64, z)) {
						j += Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());
						++k;
					}
			if (k == 0)
				return false;
			else {
				hPos = j / k;
				boundingBox.offset(0, hPos - boundingBox.minY + offset, 0);
				return true;
			}
		}
	}

	/**
	 * Build the component into the target world using the random number
	 * generator provided.
	 */
	@Override
	public abstract boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox);

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0) {
		fill(w, bb, v0, v1, b0, b0);
	}

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0, int m0) {
		fill(w, bb, v0, v1, b0, m0, b0, m0);
	}

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0, Block b1) {
		fill(w, bb, v0, v1, b0, b1, true);
	}

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0, int m0, Block b1, int m1) {
		fill(w, bb, v0, v1, b0, m0, b1, m1, true);
	}

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0, Block b1, boolean rep) {
		fillWithBlocks(w, bb, v0.floorX(), v0.floorY(), v0.floorZ(), v1.floorX(), v1.floorY(), v1.floorZ(), b0, b1, rep);
	}

	protected void fill(World w, StructureBoundingBox bb, Vector3 v0, Vector3 v1, Block b0, int m0, Block b1, int m1,
			boolean rep) {
		fillWithMetadataBlocks(w, bb, v0.floorX(), v0.floorY(), v0.floorZ(), v1.floorX(), v1.floorY(), v1.floorZ(), b0,
				m0, b1, m1, rep);
	}

}
