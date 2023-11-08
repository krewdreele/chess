package Deserializers;

import chess.ChessPosition;
import chess.ChessPositionImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPositionDS implements JsonDeserializer<ChessPosition> {
    /**
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
       return new Gson().fromJson(jsonElement, ChessPositionImpl.class);
    }
}
