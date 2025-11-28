package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import doctrina.Input.Controller;
import doctrina.rendering.Camera;
import doctrina.rendering.CameraMode;

public class GameCamera extends Camera {

    Controller<Action, Axis> controller;

    GameCamera(Controller<Action, Axis> controller, Player player) {
        super(player, CameraMode.FPS);
        this.controller = controller;
        setSensitivity(1);
    }

    @Override
    public void update() {
        addYaw((float) controller.getAxis(Axis.LOOK_X));
        addPitch((float) controller.getAxis(Axis.LOOK_Y));
        super.update();
    }
}
