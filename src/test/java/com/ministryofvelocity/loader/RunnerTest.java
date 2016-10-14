package com.ministryofvelocity.loader;

import com.google.gson.Gson;
import com.ministryofvelocity.domain.KaiserDatasetLoader;
import io.searchbox.client.JestClient;
import io.searchbox.client.http.JestHttpClient;
import io.searchbox.core.BulkResult;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by minifast on 10/14/16.
 */
public class RunnerTest {
    @Test
    public void testRunnerLoadsData() throws Exception {
        BulkResult result = new BulkResult(new Gson());
        JestClient client = new JestHttpClient();

        KaiserDatasetLoader loader = mock(KaiserDatasetLoader.class);
        when(loader.load(client)).thenReturn(result);

        Runner runner = new Runner(loader, client);
        runner.run();

        verify(loader).load(client);
    }
}
