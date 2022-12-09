package com.ffmoyano.urlshortener.service;


import com.ffmoyano.urlshortener.entity.Link;
import com.ffmoyano.urlshortener.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LinkService {

    private final UserService userService;
    private final LinkRepository linkRepository;
    private final UrlValidator urlValidator;


    public LinkService(UserService userService, LinkRepository linkRepository, UrlValidator urlValidator) {
        this.userService = userService;
        this.linkRepository = linkRepository;
        this.urlValidator = urlValidator;
    }

    public boolean checkIsValid(String url) {
        return urlValidator.isValid(url);
    }

    @Transactional(readOnly = true)
    public List<Link> findByUser() {
        return linkRepository.findByUser(userService.getUserFromSession());
    }

    @Transactional(readOnly = false)
    public void deleteById(long id) {
        linkRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Link findByShortUrl(String shortUrl) {
        return linkRepository.findByShortUrl(shortUrl);
    }

    public Link createLinkFromUrl(String url) {
        Link link = new Link();
        link.setUrl(url);
        link.setShortUrl(generateShortUrl(5));
        link.setUser(userService.getUserFromSession());
        return link;
    }

    @Transactional(readOnly = false)
    public Link save(Link link) {
        return linkRepository.save(link);
    }


    /**
     * <b>Produces and return a string for short url to create the link before saving in the database.</b>
     * <p>
     * Generates ten different random strings to act as shortUrl, starting at 5 characters.
     * If all random strings are already present in database, the method executes itself again with
     * one more character. If one or more strings are still not used in database, it returns the first available.
     * </p>
     *
     * @param shortUrlLength - the number of characters we want for the new short url
     * @return -
     */
    private String generateShortUrl(int shortUrlLength) {
        int length = shortUrlLength;
        List<String> randomStrings = new ArrayList<>();
        IntStream.range(0, 6)
                .forEach(index -> randomStrings.add(RandomStringUtils.random(length, true, true)));

        List<String> existingShortUrls =
                linkRepository
                        .findByShortUrlIn(randomStrings)
                        .stream()
                        .map(Link::getShortUrl).toList();

        randomStrings.removeAll(existingShortUrls);
        if (randomStrings.size() > 0) {
            return randomStrings.get(0);
        } else {
            return generateShortUrl(shortUrlLength++);
        }

    }
}
