package com.VitorsosterF.exercicioPraticoAPIREST.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String keyHash;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    private boolean ativa;

    public ApiKey() {}

    public ApiKey(String keyHash, String nomeCliente) {
        this.keyHash = keyHash;
        this.nomeCliente = nomeCliente;
        this.criadaEm = LocalDateTime.now();
        this.ativa = true;
    }

    public Long getId() { return id; }

    public String getKeyHash() { return keyHash; }
    public void setKeyHash(String keyHash) { this.keyHash = keyHash; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public LocalDateTime getCriadaEm() { return criadaEm; }

    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
}
