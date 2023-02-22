package kz.meiir.petproject.web.user;

import kz.meiir.petproject.model.Role;
import kz.meiir.petproject.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Meiir Akhmetov on 22.02.2023
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll(){
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password){

        User user = new User(id,name,email,password, Role.ROLE_USER);
        if(user.isNew()){
            super.create(user);
        }
    }
}
