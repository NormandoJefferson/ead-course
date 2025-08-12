package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Não exibe campos null no retorno do JSON
@Entity
@Table(name = "TB_MODULES")
public class ModuleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID moduleId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss") // Formata exibição de data
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ocultar o campo quando for get
    @ManyToOne(optional = false) // Um módulo está vinculado a um curso.
    private CourseModel course;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ocultar o campo quando for get
    @OneToMany(mappedBy = "module") // Um módulo pode ter várias lições
    private Set<LessonModel> lessons; // Deve-se sempre utilizar o set para mapear as relações

}