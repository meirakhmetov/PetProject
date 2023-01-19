package kz.meiir.petproject.model;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    protected String name;

    public AbstractNamedEntity(){
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() +'(' + name +  ')';
    }
}
