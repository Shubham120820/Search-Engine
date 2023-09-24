package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    // HashSet to keep track of visited URLs
    HashSet<String> visitedUrls;
    // Maximum depth to limit the crawling depth
    int MAX_DEPTH = 2;

    public Crawler() {
        visitedUrls = new HashSet<>();
    }

    // Method to recursively crawl web pages
    public void getPageTextsAndLinks(String url, int depth) {
        // Skip if the URL has already been visited
        if (visitedUrls.contains(url)) {
            return;
        }

        // Stop crawling if the maximum depth is reached
        if (depth >= MAX_DEPTH) {
            return;
        }

        // Mark the URL as visited
        visitedUrls.add(url);

        // Print the current URL
        System.out.println(url);

        // Increment the depth
        depth++;

        try {
            // Connect to the URL and set a timeout of 5000 milliseconds
            Document document = Jsoup.connect(url).timeout(5000).get();

            // Create an Indexer to process the document and URL
            Indexer indexer = new Indexer(document, url);

            // Print the title of the current page
            System.out.println(document.title());

            // Select all the links on the current page
            Elements availableLinksOnPage = document.select("a[href]");

            // Recursively crawl each linked page
            for (Element currentLink : availableLinksOnPage) {
                getPageTextsAndLinks(currentLink.attr("abs:href"), depth);
            }
        } catch (IOException ioException) {
            // Handle any IO exceptions that occur during crawling
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of the Crawler class
        Crawler crawler = new Crawler();

        // Start crawling from the specified URL with an initial depth of 0
        crawler.getPageTextsAndLinks("https://www.javatpoint.com",0);
}
}