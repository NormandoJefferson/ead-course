package com.ead.course.specification;

import com.ead.course.models.CourseModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    /**
     * Especificação para filtrar usuários com base em parâmetros da requisição.
     *
     * Exemplos de uso:
     * spec = Equal.class -> Busca apenas o valor exato
     * spec = Like.class -> Não precisa do valor exato
     *
     * @And -> Combina os filtros.
     * @Or 0
     */
    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

}