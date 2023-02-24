package kz.meiir.petproject.to;

import kz.meiir.petproject.HasId;

/**
 * @author Meiir Akhmetov on 24.02.2023
 */
public class BaseTo implements HasId {
    protected Integer id;

    public BaseTo() {
    }

    public BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;

    }
}
