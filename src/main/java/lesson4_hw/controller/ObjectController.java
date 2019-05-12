package lesson4_hw.controller;

public interface ObjectController<T> {
   T save(T t);
   void delete(long id);
   T update(T t);
   T findById(long id);

}
