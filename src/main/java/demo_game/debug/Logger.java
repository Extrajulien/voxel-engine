package demo_game.debug;

import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.HashMap;

public class Logger {
    private static Logger instance;
    HashMap<LogEntry, Boolean> logEntryMap;

    private Logger() {
        logEntryMap = new HashMap<>();
    }

    public void Log(LogEntry logEntry, Vector3i value) {
        if (logEntryMap.get(logEntry) != null) {
            if (logEntryMap.get(logEntry)) {
                System.out.println(logEntry.name() + ": (" + value.x + ", " + value.y + ", " + value.z + ")");
                logEntryMap.put(logEntry, false);
            }
        }
    }

    public void Log(LogEntry logEntry, Vector3f value) {
        if (logEntryMap.get(logEntry) != null) {
            if (logEntryMap.get(logEntry)) {
                System.out.println(logEntry.name() + ": (" + value.x + ", " + value.y + ", " + value.z + ")");
                logEntryMap.put(logEntry, false);
            }
        }
    }

    public void refreshLog(LogEntry logEntry) {
        logEntryMap.put(logEntry, true);
    }


    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
