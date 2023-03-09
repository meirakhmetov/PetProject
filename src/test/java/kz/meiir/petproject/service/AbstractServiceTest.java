package kz.meiir.petproject.service;

import kz.meiir.petproject.ActiveDbProfileResolver;
import kz.meiir.petproject.Profiles;
import kz.meiir.petproject.TimingExtension;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


import static kz.meiir.petproject.util.ValidationUtil.getRootCause;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
//@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest {

    @Autowired
    public Environment env;

    //Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
     <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass){
        assertThrows(exceptionClass, () -> {
            try{
                runnable.run();
            }catch (Exception e){
                throw getRootCause(e);
            }
        });
    }
}
