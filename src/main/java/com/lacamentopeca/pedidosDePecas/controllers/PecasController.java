package com.lacamentopeca.pedidosDePecas.controllers;

import com.lacamentopeca.pedidosDePecas.model.Pecas;
import com.lacamentopeca.pedidosDePecas.repositories.PecasRepository;
import com.lacamentopeca.pedidosDePecas.DTO.RequestPecas;
import com.lacamentopeca.pedidosDePecas.services.PecasService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pecas")
@RequiredArgsConstructor
public class PecasController {

    private final PecasRepository repository;
    private final PecasService service;

    @CrossOrigin(origins = "*")
    @GetMapping("codpeca/{cod_item}")
    public ResponseEntity<Pecas> getPecaByCodigo(@PathVariable Integer cod_item) {
        Pecas peca = service.getPecaByCodPecaDatasul(cod_item);
        return peca != null ? ResponseEntity.ok(peca) : ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pn2/{partNumber}")
    public ResponseEntity getPecaByPartNumber(@PathVariable String partNumber) {
        Pecas peca = repository.findByPartNumber(partNumber);
        if (peca != null) {
            return ResponseEntity.ok(peca);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity getAllPecas(){
        var allPecas = repository.findAll();
        return ResponseEntity.ok(allPecas);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<List<Pecas>> getPecasByDescricao(@PathVariable String descricao) {
        List<Pecas> pecas = service.getPecasByDescricao(descricao);
        if (pecas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pecas);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pn/{partNumber}")
    public ResponseEntity<List<Pecas>> getPecaByPartNumberContains(@PathVariable String partNumber) {
        List<Pecas> pecas = service.getPecasByPartNumberContains(partNumber);
        if (pecas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pecas);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/fabricante/{fabricante}")
    public ResponseEntity<List<Pecas>> getPecaByFabricante(@PathVariable String fabricante) {
        List<Pecas> pecas = service.getPecasByFabricante(fabricante);
        if (pecas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pecas);
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<?> registerPecas(@RequestBody @Valid RequestPecas data) {
        try {
            Pecas newPecas = new Pecas(data);
            System.out.println(data);
            Pecas savedPecas = repository.save(newPecas);
            return ResponseEntity.ok(savedPecas);
        } catch (Exception e) {
            System.err.println("Erro ao registrar peça: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao registrar peça: " + e.getMessage());
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updatePecas(@RequestBody @Valid RequestPecas data) {
        try {
            Pecas pecas = repository.findById(data.id())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Peça não encontrada com o ID: " + data.id()
                    ));
            pecas.setNomePeca(data.nome_peca());
            pecas.setCodPecaDatasul(data.codPecaDatasul());
            pecas.setFabricante(data.fabricante());
            pecas.setPartNumber(data.partNumber());
            Pecas updatedPecas = repository.save(pecas);

            return ResponseEntity.ok(updatedPecas);

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao atualizar peça: " + e.getMessage(),
                    e
            );
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePecas(@PathVariable Integer id) {
        try {
            Pecas pecas = repository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Peça não encontrada com o ID: " + id
                    ));
            pecas.setActive(false);
            repository.save(pecas);
            return ResponseEntity.ok(pecas);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao desativar peça: " + e.getMessage(),
                    e
            );
        }
    }
}
