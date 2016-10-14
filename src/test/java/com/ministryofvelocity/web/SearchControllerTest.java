package com.ministryofvelocity.web;

import com.ministryofvelocity.domain.KaiserDatasetLoader;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.Refresh;
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
    protected final JestClientFactory factory = new JestClientFactory();
    private final String ES_TEST_INDEX = "test-kaiser-doctors";
    protected JestHttpClient client;

    @Before
    public void setUp() throws Exception {
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder("http://localhost:9200")
                        .multiThreaded(true).build()
        );
        client = (JestHttpClient) factory.getObject();
        client.execute(new CreateIndex.Builder(ES_TEST_INDEX).build());
        new KaiserDatasetLoader(ES_TEST_INDEX).load(client);
        client.execute(new Refresh.Builder().build());
    }

    @After
    public void tearDown() throws Exception {
        client.execute(new DeleteIndex.Builder(ES_TEST_INDEX).build());
        client.shutdownClient();
        client = null;
    }

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
