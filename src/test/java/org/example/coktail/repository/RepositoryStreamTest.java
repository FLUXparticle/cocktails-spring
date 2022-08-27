package org.example.coktail.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit.jupiter.*;

import javax.transaction.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = NONE)
@ExtendWith(SpringExtension.class)
public class RepositoryStreamTest {

    @Autowired
    private CocktailRepository cocktailRepository;

    private Stream<Cocktail> getCocktails(Supplier<Collection<Cocktail>> collectionSupplier) {
        return collectionSupplier.get().stream()
                .map(cocktail -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return cocktail;
                });
    }

    @Test
    void countMilk() {
        getCocktails(() -> cocktailRepository.findByNameContains("Milk"))
                .mapToInt(cocktail -> {
                    return cocktail.getInstructions().stream()
                            .filter(instruction -> "Milch".equals(instruction.getIngredient().getName()))
                            .mapToInt(Instruction::getAmountCL)
                            .sum();
                });
    }

}
