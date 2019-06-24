package lesson4_hw.controller;

import lesson4_hw.Exception.ObjectNotFoundInBDException;
import lesson4_hw.Exception.PermissionException;
import lesson4_hw.model.User;

public interface ObjectController<T> {
   T save(T t);
   void delete(long id) throws ObjectNotFoundInBDException, PermissionException;
   T update(T t) throws ObjectNotFoundInBDException;
   T findById(long id);

}
