package com.lgoncalves.user_service.utils;

import java.util.List;

public interface ICrud<E> {

    List<E> getAll();

    E add(E e);

    E update(String id, E e);

    void delete(String id);

    E getById(String id);

}
