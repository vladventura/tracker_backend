package com.merrymen.shopbackend.part;

import java.io.IOException;
import java.util.ListIterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class PartUtilities {
    public static void updateAllPartsCurrentPrice() {
        throw new UnsupportedOperationException("Operation not yet implemented");
    }

    public static void fetchCurrentPartPrice(Part part) {
        String url = "https://www.amazon.com/s?k=" + part.getName();
        Document doc = getDocument(url);
        Element containingElement = getElementWithTitle(part.getName(), doc);
        part.setCurrentPrice(getPrice(containingElement));
    }

    private static Float getPrice(Element element) {
        Element pricesContainer = element.getElementsByClass("s-price-instructions-style").first();
        String price = pricesContainer != null ? pricesContainer.getElementsByClass("a-price")
                .first().getElementsByClass("a-price").first().text() : "0.00";

        String[] pricesTogether = price.split("[$]", 0);
        price = pricesTogether[pricesTogether.length - 1];
        String priceParsed = price.startsWith("$") ? price.substring(1) : price;
        Float priceAsFloat = Float.parseFloat(priceParsed);
        return priceAsFloat;
    }

    private static Element getElementWithTitle(String name, Document doc) {
        Element result = new Element("<div></div>");
        ListIterator<Element> l = doc.getElementsByClass("a-spacing-top-small").listIterator();
        while (l.hasNext()) {
            Element e = l.next();
            Element titleElement = e.getElementsByClass("a-text-normal").first();
            String title = titleElement != null ? titleElement.text() : null;
            if (title != null && title.toLowerCase().contains(name.toLowerCase())) {
                result = e;
                break;
            }
        }
        return result;
    }

    private static Document getDocument(String url) {
        Connection conn = Jsoup.connect(url);
        Document document = null;
        try {
            document = conn.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}