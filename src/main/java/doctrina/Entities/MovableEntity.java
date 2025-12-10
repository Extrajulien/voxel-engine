package doctrina.Entities;

import demo_game.Models;
import doctrina.Utils.BoundingBox;
import doctrina.Utils.Range3d;
import doctrina.physic.HitBox;
import doctrina.rendering.CameraView;
import doctrina.rendering.Mesh;
import doctrina.rendering.Model;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class MovableEntity extends Entity {
    private final HitBox speedXBox;
    private final HitBox speedYBox;
    private final HitBox speedZBox;
    private final BoundingBox speedBoxBounds;
    protected float walkSpeed;
    protected final Vector3f currentSpeed;
    protected EntityMovementFlag possibleMovements;
    private boolean isPossibleMovementsUpdated;
    private float gravityStrength;
    protected float jumpHeight;

    public MovableEntity(Model model, BoundingBox hitboxDimension) {
        super(model, hitboxDimension);
        speedBoxBounds = new BoundingBox(hitboxDimension);
        speedXBox = new HitBox(this, speedBoxBounds);
        speedYBox = new HitBox(this, speedBoxBounds);
        speedZBox = new HitBox(this, speedBoxBounds);
        possibleMovements = new EntityMovementFlag();
        possibleMovements.enableAll();
        currentSpeed = new Vector3f();
        isPossibleMovementsUpdated = true;
        walkSpeed = 1;
        jumpHeight = 1;
    }

    public void move() {
        adjustSpeedFromPossibleDirection();

        moveEntity();
    }

    private void moveEntity() {
        this.position.add(currentSpeed);
        modelMatrix.setTranslation(position);
    }

    public void drawSpeedBox(CameraView data) {
        speedXBox.update();
        speedXBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
        speedYBox.update();
        speedYBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
        speedZBox.update();
        speedZBox.drawBounds(data.viewMatrix(), data.projectionMatrix());
    }

    private void adjustSpeedFromPossibleDirection() {
        if (!possibleMovements.canMoveInDirection(EntityMovementFlag.MovementDir.WEST)) {
            if (currentSpeed.x < 0) {
                currentSpeed.setComponent(0, 0);
            }
        }

        if (!possibleMovements.canMoveInDirection(EntityMovementFlag.MovementDir.SOUTH)) {
            if (currentSpeed.z > 0) {
                currentSpeed.setComponent(2, 0);
            }
        }
    }

    public void update(double deltaTime) {
        isPossibleMovementsUpdated = true;
        updateSpeedBox();
    }

    public void collide(Range3d otherBox) {
        if (isPossibleMovementsUpdated) {
            refreshPossibleMovementDirs();
            updateSpeedBox();
        }
        checkCollisionDirection(otherBox);
    }

    private void checkCollisionDirection(Range3d otherBox) {
        final float EPSILON = 0.2f;

        boolean isCollided = false;

        //WEST
        if (speedXBox.getWorldBounds().intersectsX(otherBox.rangeX(), EPSILON)) {
            possibleMovements.disableDirection(EntityMovementFlag.MovementDir.WEST);
            if (speedXBox.getWorldBounds().intersectsX(otherBox.rangeX())) {
                currentSpeed.x = -(speedXBox.getWorldBounds().minX() - otherBox.getMaxX());
                isCollided = true;
            }
        } else {
            possibleMovements.enableDirection(EntityMovementFlag.MovementDir.WEST);
        }

        //SOUTH
        if (speedZBox.getWorldBounds().intersectsZ(otherBox.rangeZ(), EPSILON)) {
            possibleMovements.disableDirection(EntityMovementFlag.MovementDir.SOUTH);
            if (speedZBox.getWorldBounds().intersectsZ(otherBox.rangeZ())) {
                currentSpeed.z = -(speedZBox.getWorldBounds().minZ() - otherBox.getMaxZ());
                isCollided = true;
            }
        } else {
            possibleMovements.enableDirection(EntityMovementFlag.MovementDir.SOUTH);
        }

        if (isCollided) {
            moveEntity();

        }
    }

    private void updateSpeedBox() {
        Vector3f negativeSpeed = new Vector3f(currentSpeed).min(new Vector3f(0,0,0));
        Vector3f positiveSpeed = new Vector3f(currentSpeed).sub(negativeSpeed);


        BoundingBox speedXBounds = new BoundingBox(
                speedBoxBounds.minX() + negativeSpeed.x,
                speedBoxBounds.maxX() + positiveSpeed.x,
                speedBoxBounds.minY(),
                speedBoxBounds.maxY(),
                speedBoxBounds.minZ(),
                speedBoxBounds.maxZ()
        );

        BoundingBox speedYBounds = new BoundingBox(
                speedBoxBounds.minX(),
                speedBoxBounds.maxX(),
                speedBoxBounds.minY() + negativeSpeed.y,
                speedBoxBounds.maxY() + positiveSpeed.y,
                speedBoxBounds.minZ(),
                speedBoxBounds.maxZ()
        );

        BoundingBox speedZBounds = new BoundingBox(
                speedBoxBounds.minX(),
                speedBoxBounds.maxX(),
                speedBoxBounds.minY(),
                speedBoxBounds.maxY(),
                speedBoxBounds.minZ() + negativeSpeed.z,
                speedBoxBounds.maxZ() + positiveSpeed.z
        );


        speedXBox.setBounds(speedXBounds);
        speedYBox.setBounds(speedYBounds);
        speedZBox.setBounds(speedZBounds);
    }

    private void refreshPossibleMovementDirs() {
        possibleMovements.enableAll();
        isPossibleMovementsUpdated = false;
    }


    public void jump() {
        possibleMovements.disableDirection(EntityMovementFlag.MovementDir.IS_GROUNDED);
    }

    public void setGravity(float unitSquareBySeconds) {
        gravityStrength = unitSquareBySeconds;
    }

}
