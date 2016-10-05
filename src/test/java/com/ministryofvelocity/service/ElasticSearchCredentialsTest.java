package com.ministryofvelocity.service;

import com.ministryofvelocity.service.ElasticSearchCredentials;
import io.searchbox.client.JestClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Created by diyahm-pl on 10/8/16.
 */
public class ElasticSearchCredentialsTest {
    private ElasticSearchCredentials esCrendentials;

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Before
    public void setUp(){
        environmentVariables.set("VCAP_SERVICES", "{ 'compose-for-elasticsearch': " +
                "[" +
                    "{" +
                        "'credentials': {" +
                            "'db_type': 'elastic_search'," +
                            "'name': 'preacherman'," +
                            "'uri_direct_1': 'https://admin:password321@localhost:12345/'," +
                            "'ca_certificate_base64': 'secret',"+
                            "'uri': 'https://admin:password321@localhost:12346/'}," +
                        "'label': 'compose-for-elasticsearch',"+
                        "'name': 'Compose for Elasticsearch-z6'," +
                    "}"+
                "]}");
        esCrendentials = new ElasticSearchCredentials();
    }

    @Test
    public void testGetUri(){
        assertEquals(esCrendentials.getUri(), "https://admin:password321@localhost:12346/");
    }

    @Test
    public void testBuild() {
        assertEquals(esCrendentials.build().getClass(), io.searchbox.client.http.JestHttpClient.class);
    }
}
