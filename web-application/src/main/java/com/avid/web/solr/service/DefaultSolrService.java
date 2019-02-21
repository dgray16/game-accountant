package com.avid.web.solr.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.solr.repository.SolrCrudRepository;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class DefaultSolrService<E> {

    SolrCrudRepository<E, String> repository;

    public void createIndex(E document) {
        repository.save(document);
    }

    public void deleteData() {
        repository.findAll().forEach(repository::delete);
    }

}
