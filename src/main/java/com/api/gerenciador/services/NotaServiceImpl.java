package com.api.gerenciador.services;

import com.api.gerenciador.models.NotaModel;
import com.api.gerenciador.repositories.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaServiceImpl implements NotaService{

    final
    NotaRepository notaRepository;

    public NotaServiceImpl(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    @Override
    public NotaModel criarNota(NotaModel notaModel){
        return notaRepository.save(notaModel);
    }

    @Override
    public void deletarNota(Integer codNota){
        notaRepository.deleteById(codNota);
    }

    @Override
    public List<NotaModel> listarNotas(){
        return notaRepository.findAll();
    }

    @Override
    public Optional<NotaModel> getNotaById(Integer codNota){
        return notaRepository.findById(codNota);
    }

    @Override
    public Optional<NotaModel> getNotasByMatricula(Integer codMatricula){
        return notaRepository.getNotasByMatricula(codMatricula);
    }

    @Override
    public void deletarNotaByMatricula(Integer codMatricula){
        notaRepository.deletarNotaByMatricula(codMatricula);
    }

    @Override
    public NotaModel atualizarNota(NotaModel notaModel){
        return notaRepository.save(notaModel);
    }

}
