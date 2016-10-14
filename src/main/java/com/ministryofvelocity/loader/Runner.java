package com.ministryofvelocity.loader;

import com.ministryofvelocity.domain.KaiserDatasetLoader;
import com.ministryofvelocity.service.ElasticSearchCredentials;
import io.searchbox.client.JestClient;

/**
 * Created by minifast on 10/14/16.
 */
public class Runner {
    private KaiserDatasetLoader loader;
    private JestClient client;

    public Runner(KaiserDatasetLoader loader, JestClient client) {
        this.loader = loader;
        this.client = client;
    }

    public void run() throws Exception {
        loader.load(this.client);
    }

    public static void main(String[] args) throws Exception {
        new Runner(new KaiserDatasetLoader("kaiser-doctors"), ElasticSearchCredentials.getClient()).run();
    }
}
