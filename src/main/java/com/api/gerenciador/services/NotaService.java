package com.api.gerenciador.services;

import com.api.gerenciador.models.NotaModel;

import java.util.List;
import java.util.Optional;

public interface NotaService {

    public NotaModel criarNota(NotaModel notaModel);

    public List<NotaModel> listarNotas();

    public Optional<NotaModel> getNotaById(Integer codNota);

    public Optional<NotaModel> getNotasByMatricula(Integer codMatricula);

    public void deletarNota(Integer codNota);

    public void deletarNotaByMatricula(Integer codMatricula);

    public NotaModel atualizarNota(NotaModel notaModel);
}
