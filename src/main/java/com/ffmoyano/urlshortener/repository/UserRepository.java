package com.ffmoyano.urlshortener.repository;

import com.ffmoyano.urlshortener.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);
    AppUser findByEmailAndActive(String email, boolean active);

    boolean existsAppUserByEmail(String email);

}
