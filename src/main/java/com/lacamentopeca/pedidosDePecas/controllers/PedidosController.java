package com.lacamentopeca.pedidosDePecas.controllers;

import com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse;
import com.lacamentopeca.pedidosDePecas.model.Pedidos;
import com.lacamentopeca.pedidosDePecas.DTO.RequestPedidos;
import com.lacamentopeca.pedidosDePecas.repositories.PecasRepository;
import com.lacamentopeca.pedidosDePecas.repositories.PedidosRepository;
import com.lacamentopeca.pedidosDePecas.services.UserService;
import com.lacamentopeca.pedidosDePecas.services.PedidoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class PedidosController {

    private final PedidosRepository repository;
    private final UserService userService;
    private final PecasRepository pecasRepository;
    private final PedidoService pedidoService;


    @GetMapping("all")
    public ResponseEntity<List<Pedidos>> getAllPedidos() {
        List<Pedidos> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByStatus(@PathVariable String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByStatus(status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedido/{status}/{id}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByIdAndStatus(@PathVariable Integer id, @PathVariable String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByIdAndStatus(id, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/codPeca/{status}/{codPeca}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByCodAndStatus(@PathVariable("codPeca") Integer codPeca,
                                                                               @PathVariable("status") String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByCodAndStatus(codPeca, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/descricao/{status}/{nome_peca}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByNameAndStatus(@PathVariable String nome_peca, @PathVariable String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByNomeAndStatus(nome_peca, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/partnumber/{status}/{part_number}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByPartnumberAndStatus(@PathVariable String part_number, @PathVariable String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByPartnumberAndStatus(part_number, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/os/{status}/{os}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByOsAndStatus(@PathVariable Integer os, @PathVariable String status) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByOsAndStatus(os, status);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<Optional<Pedidos>> getPedidoById(@PathVariable Integer id) {
        Optional<Pedidos> pedidos = pedidoService.getPedidoById(id);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/codPeca/{cod_peca}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByCod(@PathVariable Integer cod_peca) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByCod(cod_peca);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/descricao/{nome_peca}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByName(@PathVariable String nome_peca) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByName(nome_peca);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/partnumber/{part_number}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByPartnumber(@PathVariable String part_number) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByPartnumber(part_number);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/os/{os}")
    public ResponseEntity<List<CustomPedidoResponse>> getPedidosByOs(@PathVariable Integer os) {
        List<CustomPedidoResponse> pedidos = pedidoService.getPedidosByOs(os);
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Pedidos> registerPedido(@RequestBody @Valid RequestPedidos pedidoDTO) {
        try {
            Pedidos pedidoCriado = pedidoService.registerPedido(pedidoDTO);
            return ResponseEntity
                    .created(URI.create("/pedidos/" + pedidoCriado.getId()))
                    .body(pedidoCriado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Pedidos> updatePedido(@PathVariable Integer id) {
        return pedidoService.updateStatusPedido(id, "CONCLUIDO");
    }

    @PutMapping("fat/{id}")
    @Transactional
    public ResponseEntity updatePedidoFat(@PathVariable Integer id) {
        Optional<Pedidos> optionalPedidos = repository.findById(id);
        if (optionalPedidos.isPresent()) {
            Pedidos pedido = optionalPedidos.get();
            pedido.setStatus("FATURADO");
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/cancel/{id}")
    @Transactional
    public ResponseEntity<Pedidos> cancelPedido(@PathVariable Integer id) {
        return pedidoService.updateStatusPedido(id, "CANCELADO");
    }

}
