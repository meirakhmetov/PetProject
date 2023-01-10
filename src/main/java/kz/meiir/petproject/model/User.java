package kz.meiir.petproject.model;

import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import static kz.meiir.petproject.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class User extends AbstractNamedEntity{

    private String email;

    private String password;

    private boolean enable = true;

    private Date registered = new Date();

    private Set<Role> roles;

    private int caloriesPerDay = DEFAULT_CALORIES_PER_DAY;

    public User(Integer id, String name, String email, String password, Role role, Role... roles){
        this(id, name,email, password,DEFAULT_CALORIES_PER_DAY,true, EnumSet.of(role,roles));
    }

    public User(Integer id, String name, String email, String password, int caloriesPerDay, boolean enable, Set<Role> roles) {
        super(id, name);
        this.email=email;
        this.password=password;
        this.caloriesPerDay=caloriesPerDay;
        this.enable=enable;
        this.roles=roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public boolean isEnable() {
        return enable;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email +
                ", name='" + name +
                ", enable=" + enable +
                ", roles=" + roles +
                ", caloriesPerDay=" + caloriesPerDay +
                '}';
    }
}
