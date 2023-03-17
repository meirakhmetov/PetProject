package kz.meiir.petproject.web.user;

import kz.meiir.petproject.util.exception.ErrorType;
import kz.meiir.petproject.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.HEROKU;
import static kz.meiir.petproject.UserTestData.*;
import static kz.meiir.petproject.util.exception.ModificationRestrictionException.EXCEPTION_MODIFICATION_RESTRICTION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Meiir Akhmetov on 17.03.2023
 */
@ActiveProfiles({HEROKU})
class HerokuRestControllerTest extends AbstractControllerTest {

    public HerokuRestControllerTest(){
        super(AdminRestController.REST_URL);
    }

    @Test
    void delete() throws Exception{
        perform(doDelete(USER_ID).basicAuth(ADMIN))
                .andDo(print())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        perform(doPut(USER_ID).jsonUserWithPassword(USER).basicAuth(ADMIN))
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_MODIFICATION_RESTRICTION))
                .andExpect(status().isUnprocessableEntity());
    }
}
