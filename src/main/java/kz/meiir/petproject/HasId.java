package kz.meiir.petproject;

import org.springframework.util.Assert;

/**
 * @author Meiir Akhmetov on 24.02.2023
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default int id(){
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
