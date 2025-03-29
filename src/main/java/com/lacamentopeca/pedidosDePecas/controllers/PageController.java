package com.lacamentopeca.pedidosDePecas.controllers;

import com.lacamentopeca.pedidosDePecas.model.Page;
import com.lacamentopeca.pedidosDePecas.DTO.RequestPage;
import com.lacamentopeca.pedidosDePecas.repositories.PageRepository;
import com.lacamentopeca.pedidosDePecas.services.PageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{id}")
    public ResponseEntity<Page> getPageById(@PathVariable Integer id) {
        Page page = pageService.getPageById(id);
        return ResponseEntity.ok(page);
    }

    @PostMapping()
    public ResponseEntity<Page> createPage(@RequestBody @Valid RequestPage data) {
        Page page = pageService.createPage(data);
        return ResponseEntity.ok(page);
    }

    @PutMapping("{id}")
    public ResponseEntity<Page> updatePage(@RequestBody @Valid RequestPage data, @PathVariable Integer id) {
        Page page = pageService.updatePage(id, data);
        return ResponseEntity.ok(page);
    }
}