package com.lacamentopeca.pedidosDePecas.repositories;

import com.lacamentopeca.pedidosDePecas.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
}
