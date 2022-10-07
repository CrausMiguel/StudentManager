package com.api.gerenciador.controllers;

import com.api.gerenciador.dtos.UsuarioDTO;
import com.api.gerenciador.models.FuncaoEnum;
import com.api.gerenciador.models.UsuarioModel;
import com.api.gerenciador.services.UsuarioService;
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
@Api(value = "API dos usuarios", tags = "Usuarios",description = "Controle de Usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Metodo para criar usuario
    @ApiOperation("Cadastrar um usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario cadastrado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PostMapping("/usuario")
    public ResponseEntity<UsuarioModel> salvaUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO,usuarioModel); //Copia as propriedades do DTO para o Model
        //Setta o grupo passado no DTO para valor ENUM
        usuarioModel.setFuncao(FuncaoEnum.valueOf(usuarioDTO.getFuncao()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuarioModel));
    }

    //Metodo para deletar usuario
    @ApiOperation("Deletar usuario especifico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario deletado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @DeleteMapping("/usuario/{codUsuario}")
    public ResponseEntity<String> deletarUsuario (@PathVariable Integer codUsuario){
        usuarioService.deletarUsuario(codUsuario);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Meotodo para atualizar usuario
    @ApiOperation("Atualizar usuario especifico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario atualizado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @PutMapping("/usuario/{codUsuario}")
    public ResponseEntity<UsuarioModel> atualizarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO, @PathVariable Integer codUsuario) {
        var usuarioInformado = usuarioService.getUsuarioById(codUsuario);
            if(usuarioInformado.isPresent()){
                UsuarioModel usuarioAtualizado = usuarioInformado.get();
                //Copia as propriedades do DTO para o Model
                BeanUtils.copyProperties(usuarioDTO,usuarioAtualizado);
                //Setta o grupo passado no DTO para valor ENUM
                usuarioAtualizado.setFuncao(FuncaoEnum.valueOf(usuarioDTO.getFuncao()));
                return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(usuarioAtualizado));
            } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //Metodo para listar todos usuarios
    @ApiOperation("Obter listagem detalhada de todos os usuarios")
    @GetMapping("/usuario")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario encontrados"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    public ResponseEntity<List<UsuarioModel>> listarUsuarios(){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.listarUsuarios());
    }

    //Metodo para listar um usuario por ID
    @ApiOperation("Obter dados detalhados de um usuario especifico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Pagina não encontrada")
    })
    @GetMapping("/usuario/{codUsuario}")
    public ResponseEntity<Optional<UsuarioModel>> listarUsuarioPorId(@PathVariable Integer codUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.getUsuarioById(codUsuario));
    }

}
