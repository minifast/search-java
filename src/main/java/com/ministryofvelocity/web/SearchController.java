package com.ministryofvelocity.web;


import com.ministryofvelocity.domain.KaiserDoctorQuery;
import com.ministryofvelocity.service.ElasticSearchCredentials;
import io.searchbox.client.JestClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by minifast on 10/11/16.
 */
@Controller
public class SearchController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value="q") String keyword, Model model) throws Exception {
        JestClient client = ElasticSearchCredentials.getClient();
        KaiserDoctorQuery query = new KaiserDoctorQuery(keyword, client);
        model.addAttribute("results", query.getResults());
        return "index";
    }
}
