package demo_game;

public enum BlockType {
    AIR(false,null),
    DIRT(true, new String[] {"block/dirt.jpg"});

    private final boolean isSolid;
    private final String[] texturePaths;

    BlockType(boolean isSolid, String[] texturePaths) {
        this.isSolid = isSolid;
        this.texturePaths = texturePaths;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public String[] getTexturePaths() {
        return texturePaths;
    }
}
