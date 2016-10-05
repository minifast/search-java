package com.ministryofvelocity.domain;


import com.ministryofvelocity.service.ElasticSearchCredentials;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by minifast on 10/12/16.
 */
public class KaiserDatasetLoader {
    private String indexName;
    private JSONArray providers;

    public KaiserDatasetLoader(String indexName){
        this.indexName = indexName;
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("data/peds.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(obj.toString());
        JSONObject jsonResponse = (JSONObject) jsonObject.getJSONArray("jsonResponse").get(0);
        this.providers = jsonResponse.getJSONArray("availableProviderSlots");

    }

    public BulkResult load(JestClient client) throws Exception {
        Bulk.Builder builder = new Bulk.Builder();

        for (int i=0; i < providers.length(); i++) {
            builder.addAction(new Index.Builder(providers.get(i).toString()).index(indexName).type("doctor").build());
        }

        return client.execute(builder.build());
    }
}
