package kz.meiir.petproject.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;
import kz.meiir.petproject.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static kz.meiir.petproject.UserTestData.*;


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
    void update() throws Exception{
        UserTo updatedTo = new UserTo(null, "newName","newemail@ok.kz","newPassword",1500);
        perform(doPut().jsonBody(updatedTo).basicAuth(USER))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHERS.assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

}
