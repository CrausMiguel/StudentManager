package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.MatriculaDTO;
import com.api.gerenciador.models.MatriculaModel;
import com.api.gerenciador.services.MatriculaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gerenciador")
@Api(value = "Api de matriculas", tags = "Matriculas", description = "Controlador de Matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @ApiOperation("Obter listagem detalhados de todas as matriculas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matricula cadastrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/matricula")
    public ResponseEntity<List<MatriculaModel>> listarMatricula(){
        return ResponseEntity.status(HttpStatus.OK).body(matriculaService.listarMatricula());
    }

    @ApiOperation("Obter dados detalhados de uma matricula especifica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matricula encontrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/matricula/{codMatricula}")
    public ResponseEntity<Optional<MatriculaModel>> listarMatriculaPorId(@PathVariable Integer codMatricula){
        return ResponseEntity.status(HttpStatus.OK).body(matriculaService.getMatriculaById(codMatricula));
    }


    @ApiOperation("Cadastrar uma matricula")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matricula cadastrada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PostMapping("/matricula")
    public ResponseEntity<MatriculaModel> salvaMatricula(@RequestBody @Valid MatriculaDTO matriculaDTO){
        var matriculaModel = new MatriculaModel();
        BeanUtils.copyProperties(matriculaDTO,matriculaModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(matriculaService.criarMatricula(matriculaModel));
    }


    @ApiOperation("Atualizar uma matricula especifica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matricula atualizada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PutMapping("/matricula/{codMatricula}")
    public ResponseEntity<MatriculaModel> atualizarMatricula(@PathVariable Integer codMatricula, @RequestBody @Valid MatriculaDTO matriculaDTO){
        var matriculaInformada = matriculaService.getMatriculaById(codMatricula);
        if (matriculaInformada.isPresent()){
            var matriculaModel = matriculaInformada.get();
            BeanUtils.copyProperties(matriculaDTO,matriculaModel);
            return ResponseEntity.status(HttpStatus.OK).body(matriculaService.atualizarMatricula(matriculaModel));
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation("Deletar uma matricula especifica")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Matricula deletada"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @DeleteMapping("/matricula/{codMatricula}")
    public ResponseEntity<String> deletarMatricula(@PathVariable Integer codMatricula){
        matriculaService.deletarMatricula(codMatricula);
        return ResponseEntity.status(HttpStatus.OK).body("Matrícula deletada com sucesso!");
    }
}
