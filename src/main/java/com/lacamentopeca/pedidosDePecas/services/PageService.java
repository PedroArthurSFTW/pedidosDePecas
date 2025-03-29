package com.lacamentopeca.pedidosDePecas.services;

import com.lacamentopeca.pedidosDePecas.DTO.RequestPage;
import com.lacamentopeca.pedidosDePecas.model.Page;
import com.lacamentopeca.pedidosDePecas.repositories.PageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {

    private final PageRepository pageRepository;

    @Autowired
    public PageService(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Transactional
    public Page createPage(RequestPage data) {
        Page page = new Page(data);
        return pageRepository.save(page);
    }

    @Transactional
    public Page updatePage(Integer id, RequestPage data) {
        Page page = pageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        page.setAtt_page(data.att_page());
        return page;
    }

    public Page getPageById(Integer id) {
        Optional<Page> pageOptional = pageRepository.findById(id);
        return pageOptional.orElse(null);
    }

    public List<Page> getAllPages() {
        return pageRepository.findAll();
    }
}

