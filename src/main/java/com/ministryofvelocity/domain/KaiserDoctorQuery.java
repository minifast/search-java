package com.ministryofvelocity.domain;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by minifast on 10/11/16.
 */
public class KaiserDoctorQuery {
    private final String keyword;
    private final JestClient client;

    public KaiserDoctorQuery(String keyword, JestClient client) throws IOException {
        this.keyword = keyword;
        this.client = client;
    }

    public List<Doctor> getResults() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.multiMatchQuery(keyword, "providerName", "fac.facNm", "address.addr", "spokenLanguages.language"));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addType("doctor")
                .build();

        return client.execute(search).getSourceAsObjectList(Doctor.class);
    }
}

