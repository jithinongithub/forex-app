package com.binarythinker.forexapp.controller

import com.binarythinker.forexapp.service.ForexService
import com.binarythinker.forexapp.service.ForexServiceInput
import com.binarythinker.forexapp.service.ForexServiceOutput
import com.binarythinker.forexapp.service.ForexServiceQuote
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ForexController::class)
class ForexControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var forexService: ForexService

    @Test
    fun `given valid request get forex respond with valid response`() {

        val request = """
            {
                "sourceCurrency": "GBP",
                "targetCurrencies": ["INR", "USD"]
            }
        """.trimIndent()

        val expectedServiceInput = ForexServiceInput(
            sourceCurrency = "GBP",
            targetCurrencies = listOf("INR", "USD")
        )

        every { forexService.getForex(expectedServiceInput)} returns ForexServiceOutput(
            listOf(
                ForexServiceQuote(
                    key = "GBPINR",
                    value = 105.6
                ),
                ForexServiceQuote(
                    key = "GBPUSD",
                    value = 1.27
                )
            )
        )

        val expectedResponse = """
            {
                "quotes": [
                    {
                        "key": "GBPINR",
                        "value": 105.6

                    },
                    {
                        "key": "GBPUSD",
                        "value": 1.27
                    }
                ]
            }
        """.trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/forex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.content().json(expectedResponse))
            .andReturn()

        verify(exactly = 1) { forexService.getForex(expectedServiceInput) }

    }
}
