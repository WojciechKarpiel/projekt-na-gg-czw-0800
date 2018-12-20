package pl.edu.agh.gg.domain;

import pl.edu.agh.gg.domain.hyperEdge.HyperEdge;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Jest albo Wierzchołkiem albo hiperkrawędzią
 */
public abstract class VertexLike {
    private static AtomicInteger ŻAL_PL = new AtomicInteger(0);
    private final EntityType entityType;
    private final int id;

    public VertexLike(EntityType entityType) {
        this.entityType = entityType;
        this.id = ŻAL_PL.getAndIncrement();
    }

    public String getId() {
        return String.valueOf(id);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public boolean isVertex() {
        return entityType == EntityType.VERTEX;
    }

    public boolean isEdge() {
        return entityType == EntityType.EDGE;
    }

    public <T> T map(Function<Vertex, T> forVertex, Function<HyperEdge, T> forEdge) {
        if (isVertex()) {
            return forVertex.apply((Vertex) this);
        } else {
            return forEdge.apply((HyperEdge) this);
        }
    }

    public Optional<Vertex> getAsVertex() {
        return map(Optional::of, i -> Optional.empty());
    }

    public Optional<HyperEdge> getAsEdge() {
        return map(i -> Optional.empty(), Optional::of);
    }

    public static enum EntityType {
        VERTEX, EDGE;
    }


}
