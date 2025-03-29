package com.lacamentopeca.pedidosDePecas.services;
import com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse;
import com.lacamentopeca.pedidosDePecas.DTO.RequestPedidos;
import com.lacamentopeca.pedidosDePecas.model.Pecas;
import com.lacamentopeca.pedidosDePecas.model.Pedidos;
import com.lacamentopeca.pedidosDePecas.model.Usuarios;
import com.lacamentopeca.pedidosDePecas.repositories.PecasRepository;
import com.lacamentopeca.pedidosDePecas.repositories.PedidosRepository;
import com.lacamentopeca.pedidosDePecas.repositories.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidosRepository pedidosRepository;
    private final PecasRepository pecasRepository;
    private final UsuariosRepository usuariosRepository;
    private final UserService authorizationService;


    public List<CustomPedidoResponse> getPedidosByStatus(String status) {
        return pedidosRepository.findByStatus(status);
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

    @Transactional
    public Pedidos registerPedido(RequestPedidos pedidoDTO) {
        Pecas peca = pecasRepository.findById(pedidoDTO.peca_id())
                .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada"));

        if (!peca.getActive()) {
            throw new IllegalStateException("Não é possível criar pedido para peça inativa");
        }
        Usuarios usuarioAbertura = usuariosRepository.findById(pedidoDTO.usuarios_id_abertura())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Pedidos novoPedido = new Pedidos();
        novoPedido.setPeca(peca);
        novoPedido.setUsuarioAbertura(usuarioAbertura);
        novoPedido.setOrdem_servico(pedidoDTO.ordem_servico());
        novoPedido.setStatus("SOLICITADO");
        novoPedido.setData_pedidos(LocalDateTime.now());

        return pedidosRepository.save(novoPedido);
    }

    @Transactional
    public ResponseEntity<Pedidos> updateStatusPedido(Integer id, String status) {
        Optional<Pedidos> optionalPedidos = pedidosRepository.findById(id);
        if (optionalPedidos.isPresent()) {
            Pedidos pedido = optionalPedidos.get();
            pedido.setData_pedidos_fechamento(LocalDateTime.now());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Integer userId = authorizationService.obterIdPorNomeDeUsuario(username);
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


