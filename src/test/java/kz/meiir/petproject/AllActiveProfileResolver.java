package kz.meiir.petproject;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * @author Meiir Akhmetov on 14.02.2023
 */
public class AllActiveProfileResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> aCLass) {
        return new String[]{Profiles.REPOSITORY_IMPLEMENTATION, Profiles.getActiveDbProfile()};
    }
}
