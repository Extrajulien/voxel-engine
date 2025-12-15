package doctrina.physic;

import doctrina.Utils.BoundingBox;


public class Collider {
    private final static float EPSILON = 0.01f;

    public float getAllowedX(float dx, BoundingBox box, CollisionCandidates obstacles) {
        if (dx == 0) return 0;

        float allowed = dx;

        for (BoundingBox o : obstacles) {

            if (!box.isYInRange(o) || !box.isZInRange(o)) {
                continue;
            }

            if (dx > 0) {
                float dist = o.minX() - box.maxX();
                if (dist >= 0)
                    allowed = Math.min(allowed, dist - EPSILON);
            } else {
                float dist = o.maxX() - box.minX();
                if (dist <= 0)
                    allowed = Math.max(allowed, dist + EPSILON);
            }
        }

        return allowed;
    }

    public float getAllowedY(float dy, BoundingBox box, CollisionCandidates obstacles) {
        if (dy == 0) return 0;

        float allowed = dy;

        for (BoundingBox o : obstacles) {

            if (!box.isXInRange(o) || !box.isZInRange(o)) {
                continue;
            }

            if (dy > 0) {
                float dist = o.minY() - box.maxY();
                if (dist >= 0)
                    allowed = Math.min(allowed, dist - EPSILON);
            } else {
                float dist = o.maxY() - box.minY();
                if (dist <= 0)
                    allowed = Math.max(allowed, dist + EPSILON);
            }
        }

        return allowed;
    }


    public float getAllowedZ(float dz, BoundingBox box, CollisionCandidates obstacles) {
        if (dz == 0) return 0;

        float allowed = dz;

        for (BoundingBox o : obstacles) {

            if (!box.isXInRange(o) || !box.isYInRange(o)) {
                continue;
            }

            if (dz > 0) {
                float dist = o.minZ() - box.maxZ();
                if (dist >= 0)
                    allowed = Math.min(allowed, dist - EPSILON);
            } else {
                float dist = o.maxZ() - box.minZ();
                if (dist <= 0)
                    allowed = Math.max(allowed, dist + EPSILON);
            }
        }

        return allowed;
    }


}


