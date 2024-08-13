package group.adminservice.security

import org.springframework.stereotype.Service

@Service
class ApiKeyService {
    // private val validApiKey = "your-secret-api-key" // Store securely
    private val validApiKey: String = System.getenv("JWT_SECRET_KEY") ?: "default_key"

    fun isValidApiKey(apiKey: String): Boolean {
        println("Api kljucic: $apiKey")
        return validApiKey == apiKey
    }
}
