package demo_game;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import doctrina.Input.Controller;
import doctrina.rendering.Camera;
import doctrina.rendering.CameraMode;

public class PlayerCamera extends Camera {

    Controller<Action, Axis> controller;

    PlayerCamera(Controller<Action, Axis> controller, Player player) {
        super(player, CameraMode.FPS);
        this.controller = controller;
        setSensitivity(1);
        setFpsCamRadius(0.6f);
        setFpsCamYTranslation(0.7f);
    }

    @Override
    public void update() {
        addYaw((float) controller.getAxis(Axis.LOOK_X));
        addPitch((float) controller.getAxis(Axis.LOOK_Y));
        super.update();
    }
}
