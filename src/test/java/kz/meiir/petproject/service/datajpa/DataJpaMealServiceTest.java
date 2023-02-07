package kz.meiir.petproject.service.datajpa;

import kz.meiir.petproject.service.AbstractMealServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static kz.meiir.petproject.Profiles.DATAJPA;

/**
 * @author Meiir Akhmetov on 07.02.2023
 */
@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
}
