package kz.meiir.petproject.repository;

import kz.meiir.petproject.model.User;

import java.util.List;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public interface UserRepository {
    //null if not found, when updated
    User save(User user);

    //false if not found
    boolean delete(int id);

    //null if not found
    User get(int id);

    //null if not found
    User getByEmail(String email);

    List<User> getAll();

    default User getWithMeals(int id){
        throw new UnsupportedOperationException();
    }
}
