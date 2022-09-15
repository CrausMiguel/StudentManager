package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.HorarioDTO;
import com.api.gerenciador.models.FuncaoEnum;
import com.api.gerenciador.models.HorarioModel;
import com.api.gerenciador.models.UsuarioModel;
import com.api.gerenciador.services.HorarioService;
import com.api.gerenciador.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/gerenciador")
public class HorarioController {

    private final HorarioService horarioService;
    private final UsuarioService usuarioService;

    public HorarioController(UsuarioService usuarioService, HorarioService horarioService) {
        this.usuarioService = usuarioService;
        this.horarioService = horarioService;
    }

    //Metodo para criar horario
    @PostMapping("/horario")
    public ResponseEntity<Object> salvaHorario(@Valid @RequestBody HorarioDTO horarioDTO) {
        var horarioModel = new HorarioModel();
        BeanUtils.copyProperties(horarioDTO,horarioModel); //Copia as propriedades do DTO para o Model

        //Instacia o usuario retornado pelo FindId utilizando o codUsuario passado no Json
        Optional<UsuarioModel> usuarioInformado = usuarioService.getUsuarioById(horarioDTO.getCodUsuario());

        if(usuarioInformado.isPresent()){ //Verifica se o usuario existe
            UsuarioModel usuarioModel = usuarioInformado.get(); //Passa a informações achadas em Optional para Model
            //Verificar se o usuario é do grupo PROFESSOR
            if(usuarioModel.getFuncao().equals(FuncaoEnum.valueOf("PROFESSOR"))){
                horarioModel.setProfessor(usuarioModel); //Setta o professor do Horario com as infos achadas
                return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.criarHorario(horarioModel));
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario informado não é um professor");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado");
    }

    //Metodo para deletar horario
    @DeleteMapping("/horario/{codHorario}")
    public ResponseEntity<String> deletarHorario(@PathVariable Integer codHorario) {
        horarioService.deletarHorario(codHorario);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
    }

    //Metodo para listar todos horarios
    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioModel>> listarHorarios() {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.listarHorarios());
    }

    //Metodo para listar um horario por ID
    @GetMapping("/horario/{codHorario}")
    public ResponseEntity<Optional<HorarioModel>> listarHorarioPorId(@PathVariable Integer codHorario) {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.getHorarioById(codHorario));
    }


}
