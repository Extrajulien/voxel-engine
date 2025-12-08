package demo_game.World.Chunk;

public enum ChunkRenderingMode {
    NORMAL,
    HIGHLIGHT_PLAYER_CHUNK,
    CHUNK_LOADED_CHUNKS,
    WIREFRAME_CHUNKS;


    public ChunkRenderingMode next() {
        ChunkRenderingMode[] vals = values();
        return vals[(this.ordinal() + 1) % vals.length];
    }
}
