package com.codexio.devcamp.currencyconvertor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testJsonResponse() throws Exception {
        System.out.println(this.mockMvc
                .perform(get("/fetch/currencies-table"))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }
}
