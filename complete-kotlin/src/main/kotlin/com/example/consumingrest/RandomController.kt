package com.example.consumingrest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

// Internal DTOs to shape the JSON response to match expected Quote structure
data class RandomValueResponse(
    val id: Long,
    val quote: String
)

data class RandomQuoteResponse(
    val type: String,
    val value: RandomValueResponse
)

@RestController
@RequestMapping("/api")
class RandomController {

    @GetMapping("/random")
    fun random(): RandomQuoteResponse {
        val id = Random.nextLong(1, 10_000)
        val message = listOf(
            "All limitations are self-imposed.",
            "When nothing goes right, go left.",
            "Dreams don’t work unless you do.",
            "Simplicity is the ultimate sophistication.",
            "Stay hungry, stay foolish."
        ).random()

        return RandomQuoteResponse(
            type = "success",
            value = RandomValueResponse(id = id, quote = message)
        )
    }
}
