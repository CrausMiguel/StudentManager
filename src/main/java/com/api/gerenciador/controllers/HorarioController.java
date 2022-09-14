package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.HorarioDTO;
import com.api.gerenciador.models.HorarioModel;
import com.api.gerenciador.services.HorarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/gerenciador")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    //Metodo para criar horario
    @PostMapping("/horario")
    public ResponseEntity<HorarioModel> salvaHorario(@RequestBody HorarioDTO horarioDTO) {
        var horarioModel = new HorarioModel();
        BeanUtils.copyProperties(horarioDTO,horarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.criarHorario(horarioModel));
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
