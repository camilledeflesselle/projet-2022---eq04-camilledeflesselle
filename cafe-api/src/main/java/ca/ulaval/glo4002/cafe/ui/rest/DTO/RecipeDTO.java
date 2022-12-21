package ca.ulaval.glo4002.cafe.ui.rest.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class RecipeDTO {
    @JsonDeserialize(keyUsing = IngredientKeyDeserializer.class)
    @JsonSerialize(keyUsing = IngredientKeySerializer.class)
    private Map<String, Integer> ingredients;

    public RecipeDTO() {}

    @JsonCreator
    public RecipeDTO(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public static class IngredientKeyDeserializer extends KeyDeserializer {
        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
            return key;
        }
    }

    public static class IngredientKeySerializer extends JsonSerializer<String> {
        private ObjectMapper mapper = new ObjectMapper();

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, value);
            gen.writeFieldName(writer.toString());
        }
    }
}
