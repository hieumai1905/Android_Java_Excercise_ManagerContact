package com.example.sqlite.service;

import java.util.List;

public interface IGeneralService<T, K> {
    boolean add(T t);
    List<T> findAll();
    T find(K id);
    boolean update(T t);
    boolean remove(K id);
}
