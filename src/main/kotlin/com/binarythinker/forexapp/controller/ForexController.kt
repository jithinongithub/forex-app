package com.binarythinker.forexapp.controller

import com.binarythinker.forexapp.service.ForexService
import com.binarythinker.forexapp.service.ForexServiceInput
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/forex")
class ForexController(val forexService: ForexService) {

    @PostMapping
    @ResponseBody
    fun getForex(@RequestBody forexRequest: ForexRequest): ForexResponse {
        return ForexResponse(
            quotes = forexService.getForex(
                ForexServiceInput(
                    sourceCurrency = forexRequest.sourceCurrency,
                    targetCurrencies = forexRequest.targetCurrencies
                )
            ).quotes.map { ForexQuote(
                key = it.key,
                value = it.value
            ) }
        )
    }
}

data class ForexRequest(
    val sourceCurrency: String,
    val targetCurrencies: List<String>
)

data class ForexResponse(
    val quotes: List<ForexQuote>
)

data class ForexQuote(
    val key: String,
    val value: Double
)