package com.ministryofvelocity;

import com.ministryofvelocity.domain.KaiserDatasetLoader;
import com.ministryofvelocity.service.ElasticSearchCredentials;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

import java.io.IOException;

@SpringBootApplication
public class SearchApplication {
	public static void main(String[] args) throws Exception {
        SpringApplication.run(SearchApplication.class, args);
	}
}
