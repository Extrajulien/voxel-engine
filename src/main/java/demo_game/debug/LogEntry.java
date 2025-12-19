package demo_game.debug;

public enum LogEntry {
    CHUNK_POS("Chunk_pos"),
    CAMERA_COMPONENT("Camera_component"),
    CAMERA_POSITION("Camera_position"),
    RAYCASTING_TARGET("Raycasting_target"),;

    public String name;

    LogEntry(String name) {
        this.name = name;
    }
}
