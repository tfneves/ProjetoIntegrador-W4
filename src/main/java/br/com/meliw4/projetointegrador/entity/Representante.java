package br.com.meliw4.projetointegrador.entity;

import javax.persistence.*;

@Entity
public class Representante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;

}
