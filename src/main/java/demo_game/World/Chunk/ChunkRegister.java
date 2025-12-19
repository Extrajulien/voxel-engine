package demo_game.World.Chunk;

import demo_game.BlockType;
import demo_game.World.Direction;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ChunkRegister {
    private final Map<ChunkPos, Chunk> chunks;

    public ChunkRegister() {
        chunks = new HashMap<>();
    }

    public Collection<Chunk> getAllChunks() {
        return chunks.values();
    }

    public boolean hasChunk(ChunkPos chunkPos) {
        return chunks.containsKey(chunkPos);
    }

    public Chunk getChunk(ChunkPos pos) {
        return chunks.get(pos);
    }

    public Chunk getNeighbour(ChunkPos self, Direction direction) {
        ChunkPos pos = new ChunkPos(
                self.x() + direction.x,
                self.y() + direction.y,
                self.z() + direction.z
        );
        return chunks.get(pos);
    }

    public NeighboringChunks getNeighboringChunks(ChunkPos pos) {
       return findNeighboringChunks(pos);
    }

    public void addChunk(ChunkPos pos, Chunk chunk) {
        chunks.put(pos, chunk);
    }

    public BlockType getBlock(Vector3i blockPos) {
        ChunkPos chunkPos = new ChunkPos(Chunk.worldToChunkSpace(new Vector3f(blockPos)));

        return chunks.get(chunkPos).getBlockType(Chunk.posWorldWrapToChunk(blockPos));
    }

    public void setBlock(Vector3i blockPos, BlockType blockType) {
        ChunkPos chunkPos = new ChunkPos(Chunk.worldToChunkSpace(blockPos));
        if (hasChunk(chunkPos)) {
            chunks.get(chunkPos).setBlockType(Chunk.posWorldWrapToChunk(blockPos), blockType);
        }
    }


    public void markNeighboursDirty(ChunkPos self) {
        NeighboringChunks neighbours = getNeighboringChunks(self);
        for (Direction direction : Direction.values()) {
            Chunk neighbour = neighbours.get(direction);
            if (neighbour != null) {
                neighbour.markDirty(ChunkDirty.getDirtySide(direction));
            }
        }
    }




    private NeighboringChunks findNeighboringChunks(ChunkPos pos) {
        ChunkPos top    = new ChunkPos(pos.x(), pos.y() + 1, pos.z());
        ChunkPos bottom = new ChunkPos(pos.x(), pos.y() - 1, pos.z());
        ChunkPos north  = new ChunkPos(pos.x(), pos.y(), pos.z() - 1);
        ChunkPos south  = new ChunkPos(pos.x(), pos.y(), pos.z() + 1);
        ChunkPos east   = new ChunkPos(pos.x() + 1, pos.y(), pos.z());
        ChunkPos west   = new ChunkPos(pos.x() - 1, pos.y(), pos.z());

        return new NeighboringChunks(chunks.get(top), chunks.get(bottom), chunks.get(north),
                chunks.get(south), chunks.get(east), chunks.get(west));
    }
}
