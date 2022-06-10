package com.ffmoyano.jird.service;


import com.ffmoyano.jird.entity.Link;
import com.ffmoyano.jird.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class LinkService {

    private final UserService userService;
    private final LinkRepository linkRepository;
    private final UrlValidator urlValidator;

    private final RandomStringUtils randomStringUtils;

    public LinkService(UserService userService, LinkRepository linkRepository, UrlValidator urlValidator, RandomStringUtils randomStringUtils) {
        this.userService = userService;
        this.linkRepository = linkRepository;
        this.urlValidator = urlValidator;
        this.randomStringUtils = randomStringUtils;
    }

    public boolean checkIsValid(String url) {
        return urlValidator.isValid(url);
    }

    @Transactional(readOnly = true)
    public List<Link> findByUser() {
        return linkRepository.findByUser(userService.getUserFromSession());
    }

    public Link createLinkFromUrl(String url) {
        Link link = new Link();
        link.setUrl(url);
        link.setShortUrl(generateShortUrl(5));
        link.setPermanent(true);
        link.setExpiryDate(null);
        link.setUser(userService.getUserFromSession());
        return linkRepository.save(link);
    }

    @Transactional(readOnly = false)
    public Link save(Link link) {
        return linkRepository.save(link);
    }


    private String generateShortUrl(int length) {
        int stringLength = length;
        List<String> randomStrings = new ArrayList<>();
        IntStream.range(0, 6)
                .forEach(index -> randomStrings.add(randomStringUtils.random(length)));
        for (String randomString : randomStrings) {
            Link link = linkRepository.findByShortUrl(randomString);
            if(link == null) {
                return randomString;
            }
        }
        return generateShortUrl(stringLength++);
    }

}
