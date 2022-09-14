package com.api.gerenciador.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="Notas")
public class NotaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer codNotas;
    @Column(nullable = false)
    private Integer semestre;
    @Column(nullable = false)
    private Integer notaProvaUm;
    @Column(nullable = false)
    private Integer notaProvaDois;
    @Column(nullable = false)
    private Integer notaProvaFinal;
    @Column(nullable = false)
    private Integer notaFinal;
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "MatriculaModel", referencedColumnName = "codMatricula")
    private MatriculaModel aluno;
}
