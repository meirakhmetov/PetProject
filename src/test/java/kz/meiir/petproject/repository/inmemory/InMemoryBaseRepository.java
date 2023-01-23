package kz.meiir.petproject.repository.inmemory;

import kz.meiir.petproject.model.AbstractBaseEntity;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static kz.meiir.petproject.model.AbstractBaseEntity.START_SEQ;

/**
 * @author Meiir Akhmetov on 10.01.2023
 */
@Repository
public class InMemoryBaseRepository<T extends AbstractBaseEntity> {

    private static AtomicInteger counter = new AtomicInteger(START_SEQ);

    Map<Integer, T> map = new ConcurrentHashMap<>();

    public T save(T entry){
        Objects.requireNonNull(entry, "Entry must not be null");
        if(entry.isNew()){
            entry.setId(counter.incrementAndGet());
            map.put(entry.getId(),entry);
            return entry;
        }
        return map.computeIfPresent(entry.getId(),(id,OldT)->entry);
    }

    public boolean delete(int id){
        return map.remove(id)!=null;
    }

    public T get(int id){
        return map.get(id);
    }

    Collection<T> getCollection(){
        return map.values();
    }
}
