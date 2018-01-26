package pl.rutynar.auctionsystem.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;
import pl.rutynar.auctionsystem.service.UserService;

@Component
public class CreateUserFormValidator implements Validator {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CreateUserFormDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CreateUserFormDTO form = (CreateUserFormDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");

        // login validation
        String login = form.getLogin();
        int loginLength = login.length();

        if(loginLength < 5 || loginLength > 32){
            errors.rejectValue("login", "Size.userForm.username");
        }
        if(userService.getUserByLogin(login).isPresent()){
            errors.rejectValue("login", "Duplicate.userForm.username");
        }
        if(userService.getUserByEmail(form.getEmail()).isPresent()){
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        // password validation
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        String password = form.getPassword();
        if(password.length() < 6){
            errors.rejectValue("password", "Size.userForm.password");
        }

        if(!form.getPasswordRepeated().equals(password)){
            errors.rejectValue("passwordRepeated", "Diff.userForm.passwordConfirm");
        }
    }
}
