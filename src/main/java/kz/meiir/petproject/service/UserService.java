package kz.meiir.petproject.service;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import kz.meiir.petproject.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

import static kz.meiir.petproject.util.ValidationUtil.checkNotFound;
import static kz.meiir.petproject.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user){
        Assert.notNull(user,"user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id)  {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email,"email must not be null");
        return checkNotFound(repository.getByEmail(email),"email-" + email);
    }

    @Cacheable("users")
    public List<User> getAll(){
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user,"user must not be null");
        checkNotFoundWithId(repository.save(user),user.getId());
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled){
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user); // !! need only for JDBC implementation
    }

    public User getWithMeals(int id){
        return checkNotFoundWithId(repository.getWithMeals(id),id);
    }
}
