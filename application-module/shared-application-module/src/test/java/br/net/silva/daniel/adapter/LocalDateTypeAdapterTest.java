package br.net.silva.daniel.adapter;

import br.net.silva.daniel.shared.application.adapter.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateTypeAdapterTest {

    @Test
    public void testSerialization() {
        // Create a LocalDate
        LocalDate localDate = LocalDate.of(2022, 1, 1);

        // Create an instance of the Gson builder with the LocalDateTypeAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();

        // Serialize the LocalDate to JSON
        String json = gson.toJson(localDate);

        // Check if the JSON string is as expected
        assertEquals("\"2022-01-01\"", json);
    }

    @Test
    public void testDeserialization() {
        // JSON representing a LocalDate
        String json = "\"2022-01-01\"";

        // Create an instance of the Gson builder with the LocalDateTypeAdapter
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();

        // Deserialize the JSON to LocalDate
        LocalDate localDate = gson.fromJson(json, LocalDate.class);

        // Check if the LocalDate is as expected
        assertEquals(LocalDate.of(2022, 1, 1), localDate);
    }

}