package dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T> {
    T create(T var1);

    Optional<T> get(Long var1);

    List<T> getAll();

    T update(T var1);

    boolean delete(Long var1);
}
