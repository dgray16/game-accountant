package com.avid.web.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.AllArgsConstructor;
import org.springframework.restdocs.hypermedia.Link;
import org.springframework.restdocs.hypermedia.LinkExtractor;
import org.springframework.restdocs.operation.OperationResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Was made due to the reason that
 * {@link org.springframework.restdocs.hypermedia.AbstractJsonLinkExtractor} expects single JSON object to be returned.
 *
 * @see org.springframework.restdocs.hypermedia.AbstractJsonLinkExtractor#extractLinks(OperationResponse)
 */
@Component
@AllArgsConstructor
public class GameLinkExtractor implements LinkExtractor {

    private ObjectMapper objectMapper;

    @Override
    public Map<String, List<Link>> extractLinks(OperationResponse response) throws IOException {
        MultiValueMap<String, Link> extractedLinks = new LinkedMultiValueMap<>();
        Object possibleLinks;

        try {
            Map<String, Object> jsonContent = objectMapper.readValue(response.getContent(), Map.class);
            possibleLinks = jsonContent.get("links");
        } catch (MismatchedInputException e) {
            List<Object> jsonParts = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(response.getContent());
            JsonNode firstChild = jsonNode.get(0);
            JsonNode nodeWithLinks = firstChild.get("links");

            JavaType type = objectMapper.getTypeFactory().constructMapType(
                    Map.class,
                    objectMapper.getTypeFactory().constructType(String.class),
                    objectMapper.getTypeFactory().constructType(String.class)
            );

            nodeWithLinks.forEach(node -> {
                Map<String, String> nodeValues = null;
                try {
                    nodeValues = objectMapper.readValue(node.toString(), type);
                } catch (IOException e1) {
                }
                jsonParts.add(nodeValues);
            });

            possibleLinks = jsonParts;
        }

        if (possibleLinks instanceof Collection) {
            Collection<Object> linksCollection = (Collection<Object>) possibleLinks;
            for (Object linkObject : linksCollection) {
                if (linkObject instanceof Map) {
                    Link link = maybeCreateLink((Map<String, Object>) linkObject);
                    maybeStoreLink(link, extractedLinks);
                }
            }
        }
        return extractedLinks;
    }

    private static Link maybeCreateLink(Map<String, Object> linkMap) {
        Object hrefObject = linkMap.get("href");
        Object relObject = linkMap.get("rel");
        Link result = null;

        if (relObject instanceof String && hrefObject instanceof String) {
            Object titleObject = linkMap.get("title");
            String title = titleObject instanceof String ? (String) titleObject : null;
            result = new Link((String) relObject, (String) hrefObject, title);
        }

        return result;
    }

    private static void maybeStoreLink(Link link, MultiValueMap<String, Link> extractedLinks) {
        if (Objects.nonNull(link)) {
            extractedLinks.add(link.getRel(), link);
        }
    }
}
