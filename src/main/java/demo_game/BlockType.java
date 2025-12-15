package demo_game;

public enum BlockType {
    AIR(new BlockData[] {BlockData.IS_TRANSPARENT},null),
    DIRT(new BlockData[] {BlockData.IS_SOLID}, new String[] {"block/dirt.png"});


    private final BlockFlags flags;

    private final String[] texturePaths;

    BlockType(BlockData[] blockData, String[] texturePaths) {
        flags = new BlockFlags();
        for (BlockData data : blockData) {
            flags.enable(data);
        }
        this.texturePaths = texturePaths;
    }

    public boolean isTransparent() {
        return flags.isOn(BlockData.IS_TRANSPARENT);
    }


    public boolean isOn(BlockData data) {
        return flags.isOn(data);
    }

    public String[] getTexturePaths() {
        return texturePaths;
    }
}
