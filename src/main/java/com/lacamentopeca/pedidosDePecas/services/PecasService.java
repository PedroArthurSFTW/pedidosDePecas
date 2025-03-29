package com.lacamentopeca.pedidosDePecas.services;

import com.lacamentopeca.pedidosDePecas.model.Pecas;
import com.lacamentopeca.pedidosDePecas.repositories.PecasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PecasService {

    private final PecasRepository pecasRepository;

    public Optional<Pecas> getPecaById(Integer id) {
        return pecasRepository.findById(id);
    }

    public List<Pecas> getAllActivePecas() {
        return pecasRepository.findAllByActiveTrue();
    }

    public Pecas getPecaByCodPecaDatasul(Integer codPecaDatasul) {
        return pecasRepository.findByCodPecaDatasul(codPecaDatasul);
    }

    public List<Pecas> getPecasByPartNumber(String partNumber) {
        return pecasRepository.findAllByPartNumber(partNumber);
    }

    public Pecas getPecaByNome(String nomePeca) {
        return pecasRepository.findByNomePeca(nomePeca);
    }

    public Pecas getPecaByFabricante(String fabricante) {
        return pecasRepository.findByFabricante(fabricante);
    }

    public List<Pecas> getPecasByDescricao(String descricao) {
        return pecasRepository.findByNomePecaContainingIgnoreCase(descricao);
    }

    public List<Pecas> getPecasByPartNumberContains(String partNumber) {
        return pecasRepository.findByPartNumberContainingIgnoreCase(partNumber);
    }

    public List<Pecas> getPecasByFabricante(String fabricante) {
        return pecasRepository.findByFabricanteContainingIgnoreCase(fabricante);
    }
}