package kz.meiir.petproject.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import kz.meiir.petproject.model.User;
import kz.meiir.petproject.service.UserService;
import kz.meiir.petproject.to.UserTo;
import kz.meiir.petproject.util.UserUtil;
import kz.meiir.petproject.web.AbstractControllerTest;
import kz.meiir.petproject.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static kz.meiir.petproject.UserTestData.*;
import static kz.meiir.petproject.web.user.ProfileRestController.REST_URL;

/**
 * @author Meiir Akhmetov on 17.02.2023
 */
class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        assertMatch(userService.getAll(),ADMIN);
    }

    @Test
    void update() throws Exception{
        UserTo updatedTo = new UserTo(null, "newName","newemail@ok.kz","newPassword",1500);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertMatch(userService.get(USER_ID), UserUtil.updateFromTo(new User(USER), updatedTo));
    }

}
