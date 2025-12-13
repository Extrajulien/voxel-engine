package doctrina.physic;

import doctrina.Utils.BoundingBox;
import doctrina.Utils.Range3d;
import doctrina.rendering.CameraView;
import org.joml.Vector3f;


public class CollisionCalculator {

    private HitBox speedXBox;
    private HitBox speedYBox;
    private HitBox speedZBox;

    private Vector3f position;

    private EntityMovement movement;



    public CollisionCalculator(BoundingBox originalSize) {
        speedXBox = new HitBox(originalSize);
        speedYBox = new HitBox(originalSize);
        speedZBox = new HitBox(originalSize);
    }


    public void drawSpeedBox(CameraView data) {
        speedXBox.update(position);
        speedXBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
        speedYBox.update(position);
        speedYBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
        speedZBox.update(position);
        speedZBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
    }


    public Vector3f getPossibleMovement() {
        return new Vector3f();
    }

    public void update(Vector3f speed) {

    }


    public void collide(Range3d otherBox) {
        collide(new BoundingBox(otherBox));
    }

    public void collide(BoundingBox otherBox) {

    }


    private void checkCollisionDirection(BoundingBox other, MovementDir dir) {
    }



    private HitBox getSpeedBox(MovementDir dir) {
        return switch (dir) {
            case NORTH, SOUTH -> speedZBox;
            case EAST, WEST -> speedXBox;
            case UP, DOWN -> speedYBox;
        };
    }

}


