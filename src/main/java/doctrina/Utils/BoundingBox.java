package doctrina.Utils;

import doctrina.physic.Axis;
import doctrina.physic.MovementDir;
import org.joml.Vector3f;

public class BoundingBox {
    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;


    private final float originalMinX;
    private final float originalMinY;
    private final float originalMinZ;
    private final float originalMaxX;
    private final float originalMaxY;
    private final float originalMaxZ;



    public BoundingBox(float minX, float maxX, float minY,
                       float maxY, float minZ, float maxZ) {
        if (minX > maxX || minY > maxY || minZ > maxZ) {
            throw new IllegalArgumentException("Invalid bounding box: min > max");
        }
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;


        this.originalMinX = minX;
        this.originalMinY = minY;
        this.originalMinZ = minZ;
        this.originalMaxX = maxX;
        this.originalMaxY = maxY;
        this.originalMaxZ = maxZ;
    }

    public BoundingBox(BoundingBox other) {
        this.minX = other.minX;
        this.minY = other.minY;
        this.minZ = other.minZ;
        this.maxX = other.maxX;
        this.maxY = other.maxY;
        this.maxZ = other.maxZ;

        this.originalMinX = minX;
        this.originalMinY = minY;
        this.originalMinZ = minZ;
        this.originalMaxX = maxX;
        this.originalMaxY = maxY;
        this.originalMaxZ = maxZ;
    }

    public BoundingBox(RangeI3d otherBox) {
        this(
                otherBox.getMinX(), otherBox.getMaxX(), otherBox.getMinY(),
                otherBox.getMaxY(), otherBox.getMinZ(), otherBox.getMaxZ()
        );
    }

    public RangeD1d getAxisRange(Axis axis) {
        return switch (axis) {
            case X -> new RangeD1d(minX, maxX);
            case Y -> new RangeD1d(minY, maxY);
            case Z -> new RangeD1d(minZ, maxZ);
        };
    }

    public BoundingBox(Vector3f min, Vector3f max) {
        this(min.x, max.x, min.y, max.y, min.z, max.z);
    }

    public void moveTo(Vector3f position) {
        minX = position.x + originalMinX;
        maxX = position.x + originalMaxX;

        minY = position.y + originalMinY;
        maxY = position.y + originalMaxY;

        minZ = position.z + originalMinZ;
        maxZ = position.z + originalMaxZ;

    }

    public float minX() {
        return minX;
    }

    public float minY() {
        return minY;
    }

    public float minZ() {
        return minZ;
    }


    public float maxX() {
        return maxX;
    }

    public float maxY() {
        return maxY;
    }

    public float maxZ() {
        return maxZ;
    }

    public Vector3f getMin() { return new Vector3f(minX, minY, minZ); }
    public Vector3f getMax() { return new Vector3f(maxX, maxY, maxZ); }

    public boolean contains(float x, float y, float z) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    public boolean contains(Vector3f p) {
        return contains(p.x, p.y, p.z);
    }

    public float getBorder(MovementDir dir) {
        return switch (dir) {
            case NORTH -> minZ;
            case SOUTH -> maxZ;
            case UP -> maxY;
            case DOWN -> minY;
            case WEST -> minX;
            case EAST -> maxX;
        };
    }

    public boolean isYInRange(BoundingBox other) {
        return !(maxY < other.minY() || minY > other.maxY());
    }

    public boolean isXInRange(BoundingBox other) {
        return !(maxX < other.minX() || minX > other.maxX());
    }

    public boolean isZInRange(BoundingBox other) {
        return !(maxZ < other.minZ() || minZ > other.maxZ());
    }



    public boolean intersects(BoundingBox other) {
        return this.minX <= other.maxX && this.maxX >= other.minX &&
                this.minY <= other.maxY && this.maxY >= other.minY &&
                this.minZ <= other.maxZ && this.maxZ >= other.minZ;
    }

    public boolean intersects(RangeI3d other) {
        return this.minX <= other.getMaxX() && this.maxX >= other.getMinX() &&
                this.minY <= other.getMaxY() && this.maxY >= other.getMinY() &&
                this.minZ <= other.getMaxZ() && this.maxZ >= other.getMinZ();
    }

    public boolean intersects(Axis axis, RangeI1d other) {
        return intersects(axis, other, 0);
    }

    public boolean intersects(Axis axis, RangeI1d other, double epsilon) {
        return switch (axis) {
            case X -> intersectsX(other, epsilon);
            case Y -> intersectsY(other, epsilon);
            case Z -> intersectsZ(other, epsilon);
        };
    }

    public boolean intersectsX(RangeI1d other) {
        return intersectsX(other, 0);
    }

    public boolean intersectsX(RangeI1d other, double epsilon) {
        return this.minX <= other.getHigherThreshold() + epsilon && this.maxX >= other.getLowerThreshold() - epsilon;
    }

    public boolean intersectsY(RangeI1d other) {
        return intersectsY(other, 0);
    }

    public boolean intersectsY(RangeI1d other, double epsilon) {
        return this.minY <= other.getHigherThreshold() + epsilon && this.maxY >= other.getLowerThreshold() - epsilon;
    }

    public boolean intersectsZ(RangeI1d other) {
        return intersectsZ(other, 0);
    }

    public boolean intersectsZ(RangeI1d other, double epsilon) {
        return this.minZ <= other.getHigherThreshold() + epsilon && this.maxZ >= other.getLowerThreshold() - epsilon;
    }

    public float width()  {
        return maxX - minX;
    }

    public float height() {
        return maxY - minY;
    }

    public float depth()  {
        return maxZ - minZ;
    }

    public Vector3f size() {
        return new Vector3f(width(), height(), depth());
    }

    public Vector3f center() {
        return new Vector3f(
                (minX + maxX) * 0.5f,
                (minY + maxY) * 0.5f,
                (minZ + maxZ) * 0.5f
        );
    }

    public BoundingBox translate(float dx, float dy, float dz) {
        minX += dx; minY += dy; minZ += dz;
        maxX += dx; maxY += dy; maxZ += dz;
        return this;
    }
}
