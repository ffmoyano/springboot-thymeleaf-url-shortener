package com.ffmoyano.urlshortener.repository;


import com.ffmoyano.urlshortener.entity.AppUser;
import com.ffmoyano.urlshortener.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByUser(AppUser user);
    Link findByShortUrl(String shortUrl);

    List<Link> findByShortUrlIn(List<String> shortUrls);
}
