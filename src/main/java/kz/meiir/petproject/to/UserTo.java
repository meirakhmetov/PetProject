package kz.meiir.petproject.to;

import kz.meiir.petproject.HasEmail;
import kz.meiir.petproject.util.UserUtil;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Meiir Akhmetov on 24.02.2023
 */
public class UserTo extends BaseTo implements HasEmail, Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min=2, max=100)
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @Size(max=100)
    @SafeHtml //https://stackoverflow.com/questions/17480809
    private String email;

    @NotBlank
    @Size(min=5, max=32, message = "length must be between 5 and 32 characters")
    private String password;

    @Range(min=10,max=10000)
    @NotNull
    private Integer caloriesPerDay = UserUtil.DEFAULT_CALORIES_PER_DAY;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, int caloriesPerDay) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaloriesPerDay(Integer caloriesPerDay){
        this.caloriesPerDay = caloriesPerDay;
    }

    public Integer getCaloriesPerDay(){
        return caloriesPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", caloriesPerDay='" + caloriesPerDay + '\''+
                '}';
    }
}
