package demo_game;

public enum BlockType {
    AIR(null),
    DIRT(new String[] {"block/dirt.jpg"});

    String[] texturePaths;

    BlockType(String[] texturePaths) {

    }
}
