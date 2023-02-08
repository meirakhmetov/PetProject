package kz.meiir.petproject.util;

import kz.meiir.petproject.model.AbstractBaseEntity;
import kz.meiir.petproject.util.exception.NotFoundException;

/**
 * @author Meiir Akhmetov on 09.01.2023
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id){
        return checkNotFound(object, "id="+id);
    }

    public static void checkNotFoundWithId(boolean found, int id){
        checkNotFound(found,"id="+id);
    }

    public static <T> T checkNotFound(T object, String msg){
        checkNotFound(object!=null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg){
        if(!found){
            throw new NotFoundException("Not found entity with "+msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity){
        if(!entity.isNew()){
            throw new IllegalArgumentException(entity + "must be new (id=null");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id){
        // conservative when you reply, but  accept liberally (http://stackoverflow.com/a/32728226/548473)
        if(entity.isNew()){
            entity.setId(id);
        }else if(entity.getId() !=id){
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    // http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t){
        Throwable result = t;
        Throwable cause;

        while(null!=(cause = result.getCause())&&(result!=cause)){
            result = cause;
        }
        return result;
    }

}
