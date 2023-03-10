package kz.meiir.petproject;

import javax.validation.groups.Default;

/**
 * @author Meiir Akhmetov on 10.03.2023
 */
public class View {
    public interface Persist extends Default{}
    public interface JsonREST {}
    public interface JsonUI {}

    // https://narmo7.wordpress.com/2014/04/26/how-to-set-up-validation-group-in-springmvc
    // http://forum.spring.io/forum/spring-projects/web/117289-validated-s-given-groups-should-consider-default-group-or-not
    public interface ValidatedUI{}
}
