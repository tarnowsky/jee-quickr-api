package pg.eti.kask.jee.quickr.component;

import pg.eti.kask.jee.quickr.user.dto.function.CreateUserFunction;
import pg.eti.kask.jee.quickr.user.dto.function.ReturnUserFunction;
import pg.eti.kask.jee.quickr.user.dto.function.ReturnUsersFunction;
import pg.eti.kask.jee.quickr.user.dto.function.UpdateUserFunction;

public class DtoFunctionFactory {
    public CreateUserFunction createUserFunction() {
        return new CreateUserFunction();
    }
    public ReturnUserFunction returnUserFunction() {
        return new ReturnUserFunction();
    }
    public ReturnUsersFunction returnUsersFunction() {
        return new ReturnUsersFunction();
    }
    public UpdateUserFunction updateUserFunction() {
        return new UpdateUserFunction();
    }
}
