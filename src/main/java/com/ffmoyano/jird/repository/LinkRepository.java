package com.ffmoyano.jird.repository;


import com.ffmoyano.jird.entity.AppUser;
import com.ffmoyano.jird.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByUser(AppUser user);
    Link findByShortUrl(String shortUrl);
}
