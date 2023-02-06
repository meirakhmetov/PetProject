package kz.meiir.petproject;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * @author Meiir Akhmetov on 26.01.2023
 */
public class ActiveDbProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> testClass) {
        return new String[]{Profiles.getActiveDbProfile()};
    }
}
