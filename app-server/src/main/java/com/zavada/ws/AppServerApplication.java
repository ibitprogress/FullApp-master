package com.zavada.ws;

import com.zavada.ws.entity.CategoryEntity;
import com.zavada.ws.entity.MovieEntity;
import com.zavada.ws.entity.RoleEntity;
import com.zavada.ws.entity.UserEntity;
import com.zavada.ws.repositories.CategoryRepository;
import com.zavada.ws.repositories.MovieRepository;
import com.zavada.ws.repositories.RoleRepository;
import com.zavada.ws.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@EnableJpaRepositories(basePackages = {"com.zavada.ws.repositories"})
@SpringBootApplication
public class AppServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppServerApplication.class, args);
    }


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            List<RoleEntity> roles = Arrays.asList(
                    RoleEntity.builder().name("ADMIN").build(),
                    RoleEntity.builder().name("USER").build());
            roleRepository.saveAll(roles);
        }

        if (userRepository.count() == 0) {
            RoleEntity roleEntity = roleRepository.findByNameIgnoreCase("admin").get();

            UserEntity userEntity = UserEntity.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("12345"))
                    .createdAt(LocalDateTime.now())
                    .lastLogin(LocalDateTime.now())
                    .roles(new HashSet<>(Arrays.asList(roleEntity)))
                    .build();
            userRepository.save(userEntity);
        }

        if (categoryRepository.count() == 0) {
            List<CategoryEntity> categories =
                    Arrays.asList(
                            CategoryEntity.builder().name("Comedy").build(),
                            CategoryEntity.builder().name("Crime").build(),
                            CategoryEntity.builder().name("Drama").build(),
                            CategoryEntity.builder().name("Fantasy").build(),
                            CategoryEntity.builder().name("Horror").build(),
                            CategoryEntity.builder().name("Western").build()
                    );
            categoryRepository.saveAll(categories);
        }

        if(movieRepository.count() == 0) {
            List<MovieEntity> movies = new ArrayList<>();
            for (int i = 0; i <= 150; i++) {
                movies.add(MovieEntity.builder()
                        .title("My movie #" + i)
                        .description("Movie description #" + i)
                        .image("logo.png")
                        .duration("01:3" + i)
                        .rating(5)
                        .releaseYear("2019")
                        .category(CategoryEntity.builder().id(1L).build())
                        .build());
            }

            movieRepository.saveAll(movies);
        }
    }
}
