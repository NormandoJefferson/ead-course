package com.ead.course.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")// Formata exibição de data
    private LocalDateTime creationDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ocultar o campo quando for get
    @ManyToOne(optional = false) // Um módulo está vinculado a um curso.
    private CourseModel course;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Ocultar o campo quando for get
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY) // Lazy - Não Carrega as lições (teriamos que usar EntityGraph se quisermos as lições)
    @Fetch(FetchMode.SUBSELECT)
    private Set<LessonModel> lessons; // Deve-se sempre utilizar o set para mapear as relações

}