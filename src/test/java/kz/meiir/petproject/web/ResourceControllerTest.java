package kz.meiir.petproject.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Meiir Akhmetov on 20.02.2023
 */
class ResourceControllerTest extends AbstractControllerTest {

    ResourceControllerTest() {
        super("/resources/css/style.css");
    }

    @Test
    void resources() throws Exception{
        perform(doGet())
                .andDo(print())
                .andExpect(content().contentType(MediaType.valueOf("text/css")))
                .andExpect(status().isOk());
    }
}
