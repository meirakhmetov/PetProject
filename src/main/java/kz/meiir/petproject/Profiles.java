package kz.meiir.petproject;

/**
 * @author Meiir Akhmetov on 26.01.2023
 */
public class Profiles {
    public static final String
            JDBC = "jdbc",
            JPA = "jpa",
            DATAJPA = "datajpa",
            HEROKU = "heroku";

    public static final String REPOSITORY_IMPLEMENTATION = DATAJPA;

    public static final String
            POSTGRES_DB = "postgres",
            HSQL_DB = "hsqldb";

    public static String getActiveDbProfile(){
        try{
            Class.forName("org.postgresql.Driver");
            return POSTGRES_DB;
        }catch (ClassNotFoundException ex){
            try{
                Class.forName("org.hsqldb.jdbcDriver");
                return Profiles.HSQL_DB;
            } catch (ClassNotFoundException e){
                throw new IllegalStateException("Could not find DB driver");
            }

        }
    }
}
