package demo_game.World;

import demo_game.RaycastHit;
import doctrina.Utils.Ray;

public interface WorldQuery {
    RaycastHit raycast(Ray ray, float maxDistance);
}
