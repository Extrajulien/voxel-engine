package doctrina.Utils;

public class FloatUtils {
    public static boolean nearlyEqual(float a, float b, float eps) {
        return Math.abs(a - b) <= eps;
    }
}
