package com.ministryofvelocity.domain;

import com.ministryofvelocity.domain.KaiserDatasetLoader;
import com.ministryofvelocity.domain.KaiserDoctorQuery;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.Refresh;
import org.assertj.core.api.Assertions;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.elasticsearch.plugin.deletebyquery.DeleteByQueryPlugin;

import java.io.IOException;
import java.util.List;


/**
 * Created by minifast on 10/11/16.
 */
@ESIntegTestCase.ClusterScope(scope = ESIntegTestCase.Scope.TEST, numDataNodes = 1)
public class KaiserDoctorQueryTest extends ESIntegTestCase {

    protected final JestClientFactory factory = new JestClientFactory();
    private final String ES_TEST_INDEX = "test-kaiser-doctors";
    protected JestHttpClient client;

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        return Settings.settingsBuilder()
                .put(super.nodeSettings(nodeOrdinal))
                .put(IndexMetaData.SETTING_NUMBER_OF_SHARDS, 1)
                .put(IndexMetaData.SETTING_NUMBER_OF_REPLICAS, 1)
                .put(Node.HTTP_ENABLED, true)
                .put("plugin.types", DeleteByQueryPlugin.class.getName())
                .build();
    }

    @Before
    public void setUp() throws Exception {
        assertTrue("There should be at least 1 HTTP endpoint exposed in the test cluster",
                cluster().httpAddresses().length > 0);
        super.setUp();
        int port = cluster().httpAddresses()[0].getPort();
        factory.setHttpClientConfig(
                new HttpClientConfig
                        .Builder("http://localhost:" + port)
                        .multiThreaded(true).build()
        );
        client = (JestHttpClient) factory.getObject();

        CreateIndex createIndex = new CreateIndex.Builder(ES_TEST_INDEX).build();
        client.execute(createIndex);

        new KaiserDatasetLoader(ES_TEST_INDEX).load(client);
        client.execute(new Refresh.Builder().build());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        client.shutdownClient();
        client = null;
    }

    @Test
    public void testGetResults() throws Exception {
        List<Doctor> doctors = new KaiserDoctorQuery("Marcin", client).getResults();
        assertEquals(1, doctors.size());
        assertEquals("Marcin Matuszkiewicz, MD", doctors.get(0).getProviderName());
    }
}
