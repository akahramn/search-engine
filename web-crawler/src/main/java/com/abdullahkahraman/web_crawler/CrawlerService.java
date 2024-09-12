package com.abdullahkahraman.web_crawler;

import com.abdullahkahraman.web_crawler.model.Page;
import com.abdullahkahraman.web_crawler.service.PageService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

@Service
public class CrawlerService {

    private HashSet<String> urlLink;
    private int MAX_DEPTH = 2;

    private final PageService pageService;

    public CrawlerService(PageService pageService){
        this.pageService = pageService;
        urlLink= new HashSet<String>();
    }

    public void crawl(String url, Integer depth) {
        if(!urlLink.contains(url)){
            if(urlLink.add(url)){
                System.out.println(url);
            }
            try {
                //Parsing HTML object to java Document object
                Document document = Jsoup.connect(url).timeout(5000).get();
                //Get text from document object
                String text = document.text().length()<500?document.text():document.text().substring(0, 499);
                //print text
                System.out.println(text);
                pageService.save(url, text);


                //increase depth
                depth++;
                //if depth is greater than max then return
                if(depth>MAX_DEPTH){
                    return;
                }
                //get hyperlinks available on the current page
                Elements availableLinksOnPage = document.select("a[href]");
                //run method recursively for every link available on current page
                for(Element currentLink: availableLinksOnPage){
                    crawl(currentLink.attr("abs:href"), depth);
                }
            }
            catch (IOException ioException){
                ioException.printStackTrace();
            }
        }
    }
}