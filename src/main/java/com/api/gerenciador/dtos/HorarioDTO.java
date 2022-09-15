package com.api.gerenciador.dtos;


import com.api.gerenciador.models.UsuarioModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HorarioDTO {

    @NotBlank
    private String diaDaSemana;
    @NotBlank
    private String periodo;
    @NotBlank
    private String horario;
    @NotBlank
    private Integer codUsuario;
}
