package idmRestBackEnd.system.exceptions.application;

import java.util.function.Supplier;

import idmRestBackEnd.system.exceptions.EntityBusinessException;



public class ApplicationDoesNotExistException extends EntityBusinessException {

    private static final String EXCEPTION_MESSAGE = "Application does not exist";

    public ApplicationDoesNotExistException() {
        super(EXCEPTION_MESSAGE);
    }

    public static Supplier<ApplicationDoesNotExistException> newMenuCategoryCreateException2() {
        return () -> new ApplicationDoesNotExistException();
    }
}
