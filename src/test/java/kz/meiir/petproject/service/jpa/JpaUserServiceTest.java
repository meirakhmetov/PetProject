package kz.meiir.petproject.service.jpa;


import kz.meiir.petproject.service.AbstractUserServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.JPA;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(JPA)
class JpaUserServiceTest extends AbstractUserServiceTest {
}
