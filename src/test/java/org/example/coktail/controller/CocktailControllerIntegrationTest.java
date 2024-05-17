package org.example.coktail.controller;

import org.example.coktail.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.server.*;
import org.springframework.core.*;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.concurrent.CompletableFuture.*;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CocktailControllerIntegrationTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private static final long PREISELBEERNEKTAR = 18L;

    private static final String[] EXPECTED_NAMES = new String[]{"Ananassaft", "Apfelsaft", "Barsirup Mandel", "Grenadine", "Kirschnektar", "Mineralwasser mit Kohlensäure", "Orangensaft", "Pfirsichnektar", "Preiselbeernektar", "Roter-Traubensaft", "Schwarzer-Johannisbeer-Nektar", "Zitronensaft"};

    private TimeLogger logger;

    @LocalServerPort
    private int port;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @BeforeEach
    public void setUp() {
        String baseUrl = "http://localhost:" + port;
        webClient = webClientBuilder.baseUrl(baseUrl).build();
        logger = new TimeLogger();
    }

    @Test
    public void testGetAllCocktailsAsList() {
        List<Cocktail> cocktails = doGetRequest("/rest/cocktails")
                .bodyToMono(new ParameterizedTypeReference<List<Cocktail>>() {})
                .block();

        Stream<Cocktail> stream = cocktails.stream()
                .filter(cocktail -> cocktail.getName().toLowerCase().contains("milk"));

        int sum = mapAsync(stream, cocktail -> getInstructions(cocktail.getId()).block())
                .flatMap(List::stream)
                .filter(instruction -> instruction.getIngredient().getName().equals("Milch"))
                .mapToInt(Instruction::getAmountCL)
                .sum();

        assertEquals(38, sum);
    }

    @Test
    public void testGetAllCocktailsAsFlux() {
        Flux<Cocktail> flux = doGetRequest("/flux/cocktails")
                .bodyToFlux(Cocktail.class);

        Integer sum = flux
                .filter(cocktail -> cocktail.getName().toLowerCase().contains("milk"))
                .flatMap(cocktail -> getInstructions(cocktail.getId()).flux().flatMap(Flux::fromIterable))
                .filter(instruction -> instruction.getIngredient().getName().equals("Milch"))
                .map(Instruction::getAmountCL)
                .reduce(Integer::sum)
                .block();

        assertEquals(38, sum);
    }

    @Test
    public void testGetAllCocktailsWithIngredientAsList() {
        List<Cocktail> cocktails = doGetRequest("/rest/ingredients/" + PREISELBEERNEKTAR + "/cocktails")
                .bodyToMono(new ParameterizedTypeReference<List<Cocktail>>() {})
                .block();

        List<String> names = mapAsync(cocktails.stream(), cocktail -> getInstructions(cocktail.getId()).block())
                .flatMap(List::stream)
                .map(Instruction::getIngredient)
                .map(Ingredient::getName)
                .distinct()
                .sorted()
                .collect(toList());

        assertThat(names).containsExactly(EXPECTED_NAMES);
    }

    @Test
    public void testGetAllCocktailsWithIngredientAsFlux() {
        Flux<Cocktail> flux = doGetRequest("/flux/ingredients/" + PREISELBEERNEKTAR + "/cocktails")
                .bodyToFlux(Cocktail.class);

        List<String> names = flux.flatMap(cocktail -> getInstructions(cocktail.getId()).flux().flatMap(Flux::fromIterable))
                .map(Instruction::getIngredient)
                .map(Ingredient::getName)
                .distinct()
                .collectList()
                .block();

        names.sort(Comparator.naturalOrder());

        assertThat(names).containsExactly(EXPECTED_NAMES);
    }

    private Mono<List<Instruction>> getInstructions(long cocktailID) {
        return doGetRequest("/rest/cocktails/" + cocktailID)
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    private WebClient.ResponseSpec doGetRequest(String uri) {
        logger.log("GET " + uri);
        return webClient.get()
                .uri(uri)
                .retrieve();
    }

    private static <U, T> Stream<U> mapAsync(Stream<T> stream, Function<? super T,? extends U> fn) {
        return stream.map(t -> supplyAsync(() -> fn.apply(t), EXECUTOR_SERVICE))
                .collect(toList()).stream()
                .map(CompletableFuture::join);
    }

}
