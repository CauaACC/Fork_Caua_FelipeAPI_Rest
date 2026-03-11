package com.VitorsosterF.exercicioPraticoAPIREST.controller;

import com.VitorsosterF.exercicioPraticoAPIREST.service.ApiKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class ApiKeyController {
    
    private final ApiKeyService apiKeyService;

    public ApiKeyController(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("/gerar-chave")
    public ResponseEntity<Map<String, String>> gerarChave(@RequestBody Map<String, String> body) {
        String nomeCliente = body.get("nomeCliente");

        if (nomeCliente == null || nomeCliente.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Campo 'nomeCliente' é obrigatório"));
        }

        String apiKey = apiKeyService.gerarApiKey(nomeCliente);

        return ResponseEntity.ok(Map.of(
                "nomeCliente", nomeCliente,
                "apiKey", apiKey,
                "aviso", "Guarde esta chave! Ela não poderá ser recuperada novamente."
        ));
    }
}