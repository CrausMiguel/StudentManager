package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.HorarioDTO;
import com.api.gerenciador.models.HorarioModel;
import com.api.gerenciador.services.HorarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gerenciador")
@Api(value = "Api de Horarios", tags = "Horarios", description = "Controlador de Horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    //Metodo para criar horario
    @ApiOperation("Cadastrar um horario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Horario cadastrado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PostMapping("/horario")
    public ResponseEntity<Object> salvaHorario(@Valid @RequestBody HorarioDTO horarioDTO) {
        var horarioModel = new HorarioModel();
        BeanUtils.copyProperties(horarioDTO,horarioModel); //Copia as propriedades do DTO para o Model
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.criarHorario(horarioModel));
    }

    //Metodo para deletar horario
    @ApiOperation("Deletar um horario especifico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Horario deletada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @DeleteMapping("/horario/{codHorario}")
    public ResponseEntity<HorarioModel> deletarHorario(@PathVariable Integer codHorario) {
        horarioService.deletarHorario(codHorario);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Metodo para atualizar horario
    @ApiOperation("Atualizar um horario especifico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Horario atualizada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PutMapping("/horario/{codHorario}")
    public ResponseEntity<HorarioModel> atualizarHorario(@PathVariable Integer codHorario, @RequestBody @Valid HorarioDTO horarioDTO){
        var horarioInformado = horarioService.getHorarioById(codHorario);
        if(horarioInformado.isPresent()){
            HorarioModel horarioAtualizado = horarioInformado.get();
            BeanUtils.copyProperties(horarioDTO,horarioAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(horarioService.atualizarHorario(horarioAtualizado));
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //Metodo para listar todos horarios
    @ApiOperation("Obter listagem detalhada de todos os horarios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Horarios cadastrados"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/horario")
    public ResponseEntity<List<HorarioModel>> listarHorarios() {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.listarHorarios());
    }

    //Metodo para listar um horario por ID
    @ApiOperation("Obter listagem detalhada de todos os horarios")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Horario encontrado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/horario/{codHorario}")
    public ResponseEntity<Optional<HorarioModel>> listarHorarioPorId(@PathVariable Integer codHorario) {
        return ResponseEntity.status(HttpStatus.OK).body(horarioService.getHorarioById(codHorario));
    }


}
