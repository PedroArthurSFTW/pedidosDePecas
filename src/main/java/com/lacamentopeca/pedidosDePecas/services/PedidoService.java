package com.lacamentopeca.pedidosDePecas.services;
import com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse;
import com.lacamentopeca.pedidosDePecas.DTO.RequestPedidos;
import com.lacamentopeca.pedidosDePecas.model.Pedidos;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import com.lacamentopeca.pedidosDePecas.repositories.PecasRepository;
import com.lacamentopeca.pedidosDePecas.repositories.PedidosRepository;
import com.lacamentopeca.pedidosDePecas.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidosRepository pedidosRepository;

    private PecasRepository pecasRepository;

    private UsuariosRepository usuariosRepository;

    @Autowired
    public PedidoService(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    public List<CustomPedidoResponse> getPedidosByStatus(String status) {
        return pedidosRepository.findByStatus(status);
    }

    public List<CustomPedidoResponse> getPedidosByStatusOrStatus(String status1, String status2) {
        return pedidosRepository.findByStatusOrStatus(status1, status2);
    }

    public List<CustomPedidoResponse> getPedidosByIdAndStatus(Integer id, String status) {
        return pedidosRepository.findByIdAndStatus(id, status);
    }

    public List<CustomPedidoResponse> getPedidosByDescricao(String descricao) {
        return pedidosRepository.findCustomPedidoResponsesByDescricao(descricao);
    }

    public List<CustomPedidoResponse> getPedidosByCodAndStatus(Integer codPeca, String status) {
        return pedidosRepository.findByCodPecaAndStatus(codPeca, status);
    }

    public List<CustomPedidoResponse> getPedidosByNomeAndStatus(String nome, String status) {
        return pedidosRepository.findByNomeAndStatus(nome, status);
    }

    public List<CustomPedidoResponse> getPedidosByPartnumberAndStatus(String partNumber, String status) {
        return pedidosRepository.findByPartnumberAndStatus(partNumber, status);
    }

    public List<CustomPedidoResponse> getPedidosByOsAndStatus(Integer os, String status) {
        return pedidosRepository.findByOsAndStatus(os, status);
    }

    public Optional<Pedidos> getPedidoById(Integer id) {
        return pedidosRepository.findById(id);
    }

    public List<CustomPedidoResponse> getPedidosByCod(Integer codPeca) {
        return pedidosRepository.findByCodPecaDatasul(codPeca);
    }

    public List<CustomPedidoResponse> getPedidosByName(String nomePeca) {
        return pedidosRepository.findByName(nomePeca);
    }

    public List<CustomPedidoResponse> getPedidosByPartnumber(String partNumber) {
        return pedidosRepository.findByPartnumber(partNumber);
    }

    public List<CustomPedidoResponse> getPedidosByOs(Integer os) {
        return pedidosRepository.findByOs(os);
    }

    public List<Pedidos> findAll() {
        return pedidosRepository.findAll();
    }

    public ResponseEntity<Void> registerPedido(RequestPedidos pedido) {
        if (pecasRepository.findById(pedido.peca_id()).orElseThrow().getActive()) {
            Pedidos pedidos = new Pedidos(pedido);
            pedidos.setData_pedidos(LocalDateTime.now());
            pedidos.setOrdem_servico(pedido.ordem_servico());

            Usuarios usuarioAbertura = usuariosRepository.findById(pedido.usuarios_id_abertura())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            pedidos.setUsuarioAbertura(usuarioAbertura);

            pedidosRepository.save(pedidos);
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Peça inativa");
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional
    public ResponseEntity<Pedidos> updateStatusPedido(Integer id, String status) {
        Optional<Pedidos> optionalPedidos = pedidosRepository.findById(id);
        if (optionalPedidos.isPresent()) {
            Pedidos pedido = optionalPedidos.get();
            pedido.setData_pedidos_fechamento(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Integer userId = AuthorizationService.obterIdPorNomeDeUsuario(username);

            Usuarios usuarioFechamento = usuariosRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            pedido.setUsuarioFechamento(usuarioFechamento);

            pedido.setStatus(status);
            pedidosRepository.save(pedido);
            return ResponseEntity.ok(pedido);
        } else {
            throw new EntityNotFoundException("Pedido não encontrado");
        }
    }

}


