package kz.meiir.petproject.repository.inmemory;

import kz.meiir.petproject.UserTestData;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static kz.meiir.petproject.UserTestData.ADMIN;
import static kz.meiir.petproject.UserTestData.USER;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
@Repository
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {

    public void init(){
        map.clear();
        map.put(UserTestData.USER_ID, USER);
        map.put(UserTestData.ADMIN_ID,ADMIN);
    }

    @Override
    public List<User> getAll() {
        return getCollection().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        return getCollection().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}
