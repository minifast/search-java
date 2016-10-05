package com.ministryofvelocity.service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by diyahm-pl on 10/10/16.
 */
public class ElasticSearchCredentials {
    private static JestClient client=null;
    private String uri;

    public static JestClient getClient(){
        if (client == null){
            client = new ElasticSearchCredentials().build();
        }
        return client;
    }

    public ElasticSearchCredentials(){
        try {
            JSONObject vcapJson = new JSONObject(System.getenv("VCAP_SERVICES"));
            JSONArray elasticsearch = (JSONArray) vcapJson.get("compose-for-elasticsearch");
            JSONObject elasticsearchInstance = (JSONObject) elasticsearch.get(0);
            JSONObject elasticsearchInstanceCredentials = (JSONObject) elasticsearchInstance.get("credentials");
            this.uri = (String) elasticsearchInstanceCredentials.get("uri");
        } catch (NullPointerException e) {
            this.uri = "http://localhost:9200";
        }
    }

    public String getUri(){
        return this.uri;
    }

    public JestClient build() {
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(this.getUri())
                .multiThreaded(true)
                .build());
        return factory.getObject();
    }
}
