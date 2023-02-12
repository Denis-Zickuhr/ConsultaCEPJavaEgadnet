package model;

import java.util.Map;

public interface Repository<T> {

    Map<Integer, T> getAll();

}
