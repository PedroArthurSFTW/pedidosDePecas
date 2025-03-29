package com.lacamentopeca.pedidosDePecas.repositories;
import com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse;
import com.lacamentopeca.pedidosDePecas.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedidos, Integer> {

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByStatus(@Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(pe.nomePeca) LIKE LOWER(CONCAT('%', :descricao, '%'))")
    List<CustomPedidoResponse> findCustomPedidoResponsesByDescricao(@Param("descricao") String descricao);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(p.status) = LOWER(:status1) OR LOWER(p.status) = LOWER(:status2)")
    List<CustomPedidoResponse> findByStatusOrStatus(@Param("status1") String status1, @Param("status2") String status2);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE p.id = :id AND LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByIdAndStatus(@Param("id") Integer id, @Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE pe.codPecaDatasul = :codPeca AND LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByCodPecaAndStatus(@Param("codPeca") Integer codPeca, @Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE pe.nomePeca = :nomePeca AND LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByNomeAndStatus(@Param("nome") String nome, @Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(pe.partNumber) LIKE LOWER(CONCAT('%', :partNumber, '%')) AND LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByPartnumberAndStatus(@Param("partNumber") String partNumber, @Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE p.ordem_servico = :os AND LOWER(p.status) = LOWER(:status)")
    List<CustomPedidoResponse> findByOsAndStatus(@Param("os") Integer os, @Param("status") String status);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE pe.codPecaDatasul = :codPeca")
    List<CustomPedidoResponse> findByCodPecaDatasul(@Param("codPeca") Integer codPeca);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(pe.nomePeca) LIKE LOWER(CONCAT('%', :nomePeca, '%'))")
    List<CustomPedidoResponse> findByName(@Param("nomePeca") String nomePeca);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE LOWER(pe.partNumber) LIKE LOWER(CONCAT('%', :partNumber, '%'))")
    List<CustomPedidoResponse> findByPartnumber(@Param("partNumber") String partNumber);

    @Query("SELECT new com.lacamentopeca.pedidosDePecas.DTO.CustomPedidoResponse(" +
            "p.id, pe.codPecaDatasul, pe.nomePeca, pe.partNumber, p.ordem_servico, " +
            "p.data_pedidos, p.data_pedidos_fechamento, u.username, uf.username, " +
            "pe.fabricante, p.status) " +
            "FROM Pedidos p " +
            "JOIN p.peca pe " +
            "JOIN p.usuarioAbertura u " +
            "LEFT JOIN p.usuarioFechamento uf " +
            "WHERE p.ordem_servico = :os")
    List<CustomPedidoResponse> findByOs(@Param("os") Integer os);

}
