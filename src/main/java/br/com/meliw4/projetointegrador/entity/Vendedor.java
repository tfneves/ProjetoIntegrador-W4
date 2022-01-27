package br.com.meliw4.projetointegrador.entity;

import javax.persistence.*;

@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
}
