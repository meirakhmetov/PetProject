package kz.meiir.petproject.web.user;

import kz.meiir.petproject.util.exception.ErrorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;
import kz.meiir.petproject.web.AbstractControllerTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static kz.meiir.petproject.util.exception.ErrorType.VALIDATION_ERROR;
import static kz.meiir.petproject.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static kz.meiir.petproject.TestUtil.readFromJson;
import static kz.meiir.petproject.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Meiir Akhmetov on 17.02.2023
 */
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    ProfileRestControllerTest() {
        super(ProfileRestController.REST_URL);
    }

    @Test
    void get() throws Exception{
        perform(doGet().basicAuth(USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHERS.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception{
        perform(doGet())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception{
        perform(doDelete().basicAuth(USER))
                .andExpect(status().isNoContent());
        USER_MATCHERS.assertMatch(userService.getAll(),ADMIN);
    }

    @Test
    void register() throws Exception{
        UserTo newTo = new UserTo(null, "newName", "newemail@ok.kz","newPassword",1500);
        User newUser = UserUtil.creteNewFromTo(newTo);
        ResultActions action = perform(doPost("/register").jsonBody(newTo))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHERS.assertMatch(created, newUser);
        USER_MATCHERS.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void update() throws Exception{
        UserTo updatedTo = new UserTo(null, "newName","newemail@ok.kz","newPassword",1500);
        perform(doPut().jsonBody(updatedTo).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

    @Test
    void updateInvalid() throws Exception{
        UserTo updatedTo = new UserTo(null,null,"password",null,1500);

        perform(doPut().jsonBody(updatedTo).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception{
        UserTo updatedTo = new UserTo(null, "newName", "admin@ok.kz","newPassword",1500);

        perform(doPut().jsonBody(updatedTo).basicAuth(USER))
                .andExpect(status().isConflict())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_EMAIL))
                .andDo(print());
    }

}
