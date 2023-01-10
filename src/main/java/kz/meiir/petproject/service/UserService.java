package kz.meiir.petproject.service;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import kz.meiir.petproject.util.exception.NotFoundException;

import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.checkNotFound;
import static kz.meiir.petproject.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class UserService {

    private UserRepository repository;

    public User create(User user){
        return repository.save(user);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id)throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException{
        return checkNotFound(repository.getByEmail(email),"email-" + email);
    }

    public List<User> getAll(){
        return repository.getAll();
    }

    public void update(User user) throws NotFoundException{
        checkNotFoundWithId(repository.save(user),user.getId());
    }
}
