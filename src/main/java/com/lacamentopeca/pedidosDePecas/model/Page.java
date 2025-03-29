package com.lacamentopeca.pedidosDePecas.model;

import com.lacamentopeca.pedidosDePecas.DTO.RequestPage;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "page")
@Entity(name="page")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "page_seq")
    @SequenceGenerator(name = "page_seq", sequenceName = "page_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "att_page")
    private Integer att_page;

    public Page(RequestPage requestPage){
        this.att_page = requestPage.att_page();
    }

}
