package doctrina.Entities;

import doctrina.Input.Controller;
import doctrina.rendering.Model;
import org.joml.Vector3f;

public abstract class ControllableEntity<A extends Enum<A>, X extends Enum<X>> extends MovableEntity {
    protected Controller<A,X> controller;
    public ControllableEntity(Model model, Vector3f hitboxDimension, Controller<A, X> controller) {
        super(model, hitboxDimension);
        this.controller = controller;
    }
}
