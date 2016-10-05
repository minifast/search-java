package com.ministryofvelocity.web;

import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import org.assertj.core.api.Assertions;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


/**
 * Created by minifast on 10/11/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SearchControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetHome() {
        String body = this.restTemplate.getForObject("/", String.class);
        Assertions.assertThat(body).contains("Elasticsearch Example");

    }

    @Test
    public void testGetSearch() {
        String body = this.restTemplate.getForObject("/search?q=Marcin", String.class);
        Assertions.assertThat(body).contains("Matuszkiewicz");
    }
}
