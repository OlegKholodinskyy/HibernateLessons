package lesson4_hw.controller;

import lesson4_hw.model.User;

public interface ObjectController<T> {
   T save(T t);
   void delete(long id);
   T update(T t);
   T findById(long id);

}
