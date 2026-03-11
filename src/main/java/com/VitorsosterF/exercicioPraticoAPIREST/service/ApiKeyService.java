package com.VitorsosterF.exercicioPraticoAPIREST.service;

import com.VitorsosterF.exercicioPraticoAPIREST.model.ApiKey;
import com.VitorsosterF.exercicioPraticoAPIREST.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class ApiKeyService {
    
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public String gerarApiKey(String nomeCliente) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);

        String keyOriginal = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        String keyHash = hashKey(keyOriginal);

        ApiKey apiKey = new ApiKey(keyHash, nomeCliente);
        apiKeyRepository.save(apiKey);

        return keyOriginal;
    }

    public boolean validarApiKey(String keyOriginal) {
        String keyHash = hashKey(keyOriginal);
        return apiKeyRepository.findByKeyHashAndAtivaTrue(keyHash).isPresent();
    }

    private String hashKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(key.getBytes());
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da API Key", e);
        }
    }
}
