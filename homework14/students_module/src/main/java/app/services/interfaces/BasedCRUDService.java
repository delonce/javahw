package app.services.interfaces;

import app.exceptions.IncorrectBodyException;
import app.exceptions.NoDataException;

public interface BasedCRUDService<T> {

    T create(T obj) throws IncorrectBodyException, NoDataException;

    T read(Integer id) throws NoDataException;

    T update(Integer id, T obj) throws IncorrectBodyException, NoDataException;

    boolean delete(Integer id) throws NoDataException ;
}
