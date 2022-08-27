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

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension.class)
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository repository;

    @Test
    public void newIngredientRepository() {
        assertNotNull(repository);
    }

    @Test
    public void findAll() {
        Collection<Ingredient> ingredients = repository.findAll();
        assertEquals(55, ingredients.size());
    }

    @Test
    public void findByNameContains() {
        Collection<Ingredient> ingredients = repository.findByNameContains("sirup");
        assertEquals(20, ingredients.size());
    }

}
