package com.api.gerenciador.services;

import com.api.gerenciador.models.TurmaModel;

import java.util.List;
import java.util.Optional;

public interface TurmaService {
    public List<TurmaModel> listarTurma();

    public Optional<TurmaModel> getTurmaById(Integer codTurma);

    public TurmaModel criarTurma(TurmaModel turmaModel);

    public void deletarTurma(Integer codTurma);

    public TurmaModel atualizarTurma(TurmaModel turmaModel);
}
