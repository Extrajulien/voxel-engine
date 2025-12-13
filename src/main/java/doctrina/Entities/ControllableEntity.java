package doctrina.Entities;

import doctrina.Input.Controller;
import doctrina.Utils.BoundingBox;
import doctrina.rendering.Model;

public abstract class ControllableEntity<A extends Enum<A>, X extends Enum<X>> extends MovableEntity {
    protected final Controller<A,X> controller;
    public ControllableEntity(Model model, BoundingBox hitboxDimension, Controller<A, X> controller) {
        super(model, hitboxDimension);
        this.controller = controller;
    }
}
