package com.binarythinker.forexapp.service

import org.springframework.stereotype.Service

@Service
class ForexService {

    fun getForex(forexServiceInput: ForexServiceInput):ForexServiceOutput{
        return ForexServiceOutput(quotes = emptyList())
    }

}

data class ForexServiceInput(
    val sourceCurrency: String,
    val targetCurrencies: List<String>
)

data class ForexServiceOutput(
    val quotes: List<ForexServiceQuote>
)

data class ForexServiceQuote(
    val key: String,
    val value: Double
)