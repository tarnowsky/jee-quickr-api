package pg.eti.kask.jee.quickr.dto.util;

@FunctionalInterface
public interface TriFunction<F, S, T, R> {
    R apply(F f, S s, T t);
}
