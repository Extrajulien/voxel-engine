package demo_game.Player;

import demo_game.Inputs.Action;
import demo_game.Inputs.Axis;
import doctrina.Input.Controller;
import doctrina.rendering.Camera;
import doctrina.rendering.CameraMode;

public final class PlayerCamera extends Camera {

    private final Controller<Action, Axis> controller;

    public PlayerCamera(Player player) {
        super(player, CameraMode.FPS);
        this.controller = player.getController();
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

    public void switchMode() {
        if (getMode() == CameraMode.ORBITAL) {
            setMode(CameraMode.FPS);
            return;
        }
        setMode(CameraMode.ORBITAL);
    }
}
