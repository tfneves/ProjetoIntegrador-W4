package br.com.meliw4.projetointegrador.dto;

import java.io.Serializable;

public class RepresentanteDTO implements Serializable {
    private static final long serialVersionUID=1l;
    private Long id;
    private String nome;

    public RepresentanteDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
