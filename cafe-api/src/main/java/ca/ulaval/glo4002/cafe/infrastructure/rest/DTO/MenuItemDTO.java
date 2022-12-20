package ca.ulaval.glo4002.cafe.infrastructure.rest.DTO;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MenuItemDTO {
    private String name;
    @JsonDeserialize(keyUsing = IngredientKeyDeserializer.class)
    @JsonSerialize(keyUsing = IngredientKeySerializer.class)
    private Map<String, Integer> ingredients;
    private float cost;

    public MenuItemDTO() {}

    @JsonCreator
    public MenuItemDTO(String name, Map<String, Integer> ingredients, float cost) {
        this.name = name;
        this.ingredients = ingredients;
        this.cost = cost;
    }

    public Map<String, Integer> getIngredients() {
        return this.ingredients;
    }

    public String getName() {
        return this.name;
    }

    public float getCost() {
        return this.cost;
    }

    public static class IngredientKeyDeserializer extends KeyDeserializer {
        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
            return key;
        }
    }

    public static class IngredientKeySerializer extends JsonSerializer<String> {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, value);
            gen.writeFieldName(writer.toString());
        }
    }
}

