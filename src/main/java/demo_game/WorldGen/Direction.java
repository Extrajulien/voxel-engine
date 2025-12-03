package demo_game.WorldGen;

import org.joml.Vector3i;

public enum Direction {
    TOP    (0, 1, 0, new Vector3i[]{
            new Vector3i(0,1,0),
            new Vector3i(1,1,0),
            new Vector3i(1,1,1),
            new Vector3i(0,1,1)
    }),
    BOTTOM (0, -1, 0, new Vector3i[]{
            new Vector3i(0,0,1),
            new Vector3i(1,0,1),
            new Vector3i(1,0,0),
            new Vector3i(0,0,0)
    }),
    NORTH  (0, 0, -1, new Vector3i[]{
            new Vector3i(0,0,0),
            new Vector3i(1,0,0),
            new Vector3i(1,1,0),
            new Vector3i(0,1,0)
    }),
    SOUTH  (0, 0, 1, new Vector3i[]{
            new Vector3i(1,0,1),
            new Vector3i(0,0,1),
            new Vector3i(0,1,1),
            new Vector3i(1,1,1)
    }),
    EAST   (1, 0, 0, new Vector3i[]{
            new Vector3i(1,0,0),
            new Vector3i(1,0,1),
            new Vector3i(1,1,1),
            new Vector3i(1,1,0)
    }),
    WEST   (-1, 0, 0, new Vector3i[]{
            new Vector3i(0,0,1),
            new Vector3i(0,0,0),
            new Vector3i(0,1,0),
            new Vector3i(0,1,1)
    });

    public final int x;
    public final int y;
    public final int z;
    private final Vector3i[] corners;

    Direction(int x, int y, int z, Vector3i[] corners) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.corners = corners;
    }

    public Vector3i getBottomLeftCorner() {
        return corners[0];
    }

    public Vector3i getBottomRightCorner() {
        return corners[1];
    }

    public Vector3i getTopRightCorner() {
        return corners[2];
    }

    public Vector3i getTopLeftCorner() {
        return corners[3];
    }


}
