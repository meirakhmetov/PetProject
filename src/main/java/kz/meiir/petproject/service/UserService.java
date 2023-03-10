package kz.meiir.petproject.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import kz.meiir.petproject.AuthorizedUser;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;

import java.util.List;

import static kz.meiir.petproject.util.UserUtil.prepareToSave;
import static kz.meiir.petproject.util.ValidationUtil.checkNotFound;
import static kz.meiir.petproject.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user){
        Assert.notNull(user,"user must not be null");
        return prepareAndSave(user);
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
        return checkNotFound(repository.getByEmail(email),"email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll(){
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user,"user must not be null");
//      checkNotFoundWithId: check works only for JDBC, disabled
        prepareAndSave(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(UserTo userTo){
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled){
        User user = get(id);
        user.setEnabled(enabled);
        repository.save(user); // !! need only for JDBC implementation
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if(user == null){
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user){
        return repository.save(prepareToSave(user, passwordEncoder));
    }
    public User getWithMeals(int id){
        return checkNotFoundWithId(repository.getWithMeals(id),id);
    }
}
