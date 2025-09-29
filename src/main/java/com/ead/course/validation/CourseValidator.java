package com.ead.course.validation;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.CourseDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator") // Evita conflitos quando se tem mais de um validator
    private Validator validator;

    @Autowired
    AuthUserClient authUserClient;

    // Implementação obrigatória da interface Validator
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDto courseDto = (CourseDto) o; // Casting para transformar object em CourseDto

        validator.validate(courseDto, errors); // Faz uma validação das anotação em CourseDTO. Ex: @NotBlank, @NotNull

        // Se não existir erro entra na validação do instrutor
        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(UUID instructorId, Errors errors) {

        ResponseEntity<UserDto> responseUserInstructor;

        try {
            responseUserInstructor = authUserClient.getOneUserById(instructorId); // Busca o usuário em authuser

            // Se for do tipo  STUDENT vamos setar um erro
            if (responseUserInstructor.getBody().getUserType().equals(UserType.STUDENT)) {
                // Campo com erro - erro - msg de erro
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
            }
        } catch (HttpStatusCodeException e) {

            // Se o usuário não for encontrado em authuser
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                // Campo com erro - erro - msg de erro
                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found");
            }
        }
    }

}