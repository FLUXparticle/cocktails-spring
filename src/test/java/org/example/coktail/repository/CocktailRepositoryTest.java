package org.example.coktail.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit.jupiter.*;

import javax.transaction.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension.class)
public class CocktailRepositoryTest {

    @Autowired
    private CocktailRepository repository;

    @Test
    public void newCocktailRepository() {
        assertNotNull(repository);
    }

    @Test
    public void findAll() {
        Collection<Cocktail> cocktails = repository.findAll();
        assertEquals(69, cocktails.size());
    }

    @Test
    public void findByID() {
        Cocktail cocktail = repository.findById(24L).orElse(null);
        assertNotNull(cocktail);
        assertEquals("Milkshake", cocktail.getName());
        assertEquals(4, cocktail.getInstructions().size());
    }

    @Test
    public void findByNameContains() {
        Collection<Cocktail> cocktails = repository.findByNameContains("Milk");
        assertEquals(3, cocktails.size());
    }

}
