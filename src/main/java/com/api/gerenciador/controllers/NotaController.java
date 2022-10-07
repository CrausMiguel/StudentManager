package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.NotaDTO;
import com.api.gerenciador.models.NotaModel;
import com.api.gerenciador.services.NotaService;
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
@Api(value = "Api de Nota", tags = "Notas", description = "Controlador de Notas")
public class NotaController {

    final
    NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    //Metodo para listar todas as nota
    @ApiOperation("Obter listagem detalhada de todas as notas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Notas encontradas"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/nota")
    public ResponseEntity<List<NotaModel>> listarNotas() {
        return ResponseEntity.status(HttpStatus.OK).body(notaService.listarNotas());
    }

    //Metodo para listar nota por ID
    @ApiOperation("Obter nota detalhada pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota encontrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/nota/{codNota}")
    public ResponseEntity<Optional<NotaModel>> listarNotaPorId(@PathVariable Integer codNota) {
        return ResponseEntity.status(HttpStatus.OK).body(notaService.getNotaById(codNota));
    }

    //Metodo para listar nota por Matricula
    @ApiOperation("Obter listagem de notas detalhadas pelo numero da matricula do aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota encontrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/nota/matricula/{codMatricula}")
    public ResponseEntity<Optional<NotaModel>> listarNotaByMatricula(@PathVariable Integer codMatricula) {
        return ResponseEntity.status(HttpStatus.OK).body(notaService.getNotasByMatricula(codMatricula));
    }

    //Metodo para criar nota
    @ApiOperation("Cadastrar uma nota")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota cadastrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PostMapping("/nota")
    public ResponseEntity<NotaModel> salvarNota(@RequestBody @Valid NotaDTO notaDTO) {
        var notaModel = new NotaModel();
        BeanUtils.copyProperties(notaDTO,notaModel); //Converte DTO para Model
        float notaFinal = (notaDTO.getNotaProvaUm()+notaDTO.getNotaProvaDois()+ notaDTO.getNotaProvaFinal())/3;
        if (notaFinal!=0){
            notaModel.setNotaFinal(notaFinal);
        }else {
            notaModel.setNotaFinal(0.0F);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.criarNota(notaModel));
    }

    //Metodo para deletar nota
    @ApiOperation("Deletar uma nota pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota deletada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @DeleteMapping("/nota/{codNota}")
    public ResponseEntity<Object> deletarNota(@PathVariable Integer codNota) {
        notaService.deletarNota(codNota);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation("Deletar uma nota por codigo de matricula do aluno")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota deletada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @DeleteMapping("/nota/matricula/{codMatricula}")
    public ResponseEntity<Object> deletarNotaByMatricula(@PathVariable Integer codMatricula){
        notaService.deletarNotaByMatricula(codMatricula);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Metodo para atualizar nota
    @ApiOperation("Atualizar uma nota pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Nota atualizada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PutMapping("/nota/{codNota}")
    public ResponseEntity<NotaModel> atualizarNota(@PathVariable Integer codNota, @RequestBody @Valid NotaDTO notaDTO){
        var NotaInformado = notaService.getNotaById(codNota);
        if(NotaInformado.isPresent()){
            var notaModel = NotaInformado.get();
            BeanUtils.copyProperties(notaDTO,notaModel);
            float notaFinal = (notaDTO.getNotaProvaUm()+notaDTO.getNotaProvaDois()+ notaDTO.getNotaProvaFinal())/3;
            if (notaFinal!=0){
                notaModel.setNotaFinal(notaFinal);
            }else {
                notaModel.setNotaFinal(0.0F);
            }
            return ResponseEntity.status(HttpStatus.OK).body(notaService.atualizarNota(notaModel));
        }
        return ResponseEntity.notFound().build();
    }

}
