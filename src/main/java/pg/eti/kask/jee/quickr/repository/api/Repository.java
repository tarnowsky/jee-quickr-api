package pg.eti.kask.jee.quickr.repository.api;

import java.util.List;
import java.util.Optional;

public interface Repository<E, K> {
    Optional<E> findById(K id);
    List<E> findAll();
    Optional<E> create(E entity);
    Optional<E> update(E entity);
    Optional<E> delete(K id);
}
