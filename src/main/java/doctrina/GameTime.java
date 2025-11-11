package doctrina;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GameTime {

    private static long fpsDeltaTime;

    private int currentFps;
    private int fpsCount;
    private int fpsTarget;
    private double lastFrameTime;
    private double syncTime;

    public static double getElapsedTimeSec() {
        return glfwGetTime();
    }
    public static double getElapsedTimeMs() {
        return glfwGetTime() * 1000;
    }

    public int getCurrentFps() {
        return (currentFps > 0) ? currentFps : fpsCount;
    }


    public static String getElapsedFormattedTime() {
        long time = (long) getElapsedTimeSec() * 1000;
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public GameTime() {
        syncTime = getElapsedTimeMs();
        lastFrameTime = 0;
        fpsDeltaTime = 0;
        currentFps = 0;
        fpsTarget = 60;
    }

    public void setFpsTarget(int target) {
        fpsTarget = target;
    }

    public void synchronize() {
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        syncTime = getElapsedTimeMs();
    }

    public double deltaTime() {
        return getElapsedTimeSec() - lastFrameTime;
    }

    private void update() {
        fpsCount++;
        long currentSecond = (long) getElapsedTimeSec();
        if (fpsDeltaTime != currentSecond) {
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsDeltaTime = currentSecond;
        lastFrameTime = getElapsedTimeSec();
    }

    private long getSleepTime() {
        long targetTime = 1000L / fpsTarget;
        long sleep = targetTime - (long) (getElapsedTimeMs() - syncTime);
        if (sleep < 0) {
            sleep = 4;
        }
        return sleep;
    }
}
