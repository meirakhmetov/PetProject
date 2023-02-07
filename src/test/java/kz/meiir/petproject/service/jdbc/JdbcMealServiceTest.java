package kz.meiir.petproject.service.jdbc;

import kz.meiir.petproject.service.AbstractMealServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.JDBC;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
}
