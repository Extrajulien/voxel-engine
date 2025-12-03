package demo_game;

public enum BlockType {
    AIR(true,null),
    DIRT(false, new String[] {"block/dirt.jpg"});

    private final boolean isTransparent;
    private final String[] texturePaths;

    BlockType(boolean isTransparent, String[] texturePaths) {
        this.isTransparent = isTransparent;
        this.texturePaths = texturePaths;
    }

    public boolean isTransparent() {
        return isTransparent;
    }

    public String[] getTexturePaths() {
        return texturePaths;
    }
}
