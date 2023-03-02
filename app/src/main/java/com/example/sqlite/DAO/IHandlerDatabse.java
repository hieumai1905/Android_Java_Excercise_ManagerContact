package com.example.sqlite.DAO;

import java.util.List;

public interface IHandlerDatabse<T, K> {
    void addContact(T t);
    T getContact(K id);
    List<T> getAllContact();
    void updateContact(T t);
    void removeContact(K id);
}
