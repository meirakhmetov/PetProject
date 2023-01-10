package kz.meiir.petproject.repository.inmemory;

import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger Log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    @Override
    public boolean delete(int id) {
        Log.info("delete {}",id);
        return true;
    }

    @Override
    public User save(User user) {
        Log.info("save {}",user);
        return user;
    }

    @Override
    public User get(int id) {
        Log.info("get {}",id);
        return null;
    }

    @Override
    public List<User> getAll() {
        Log.info("getAll");
        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        Log.info("getByEmail {}", email);
        return null;
    }
}
