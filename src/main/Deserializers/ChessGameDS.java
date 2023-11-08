package Deserializers;

import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameDS implements JsonDeserializer<ChessGame> {
    /**
     * @param jsonElement
     * @param type
     * @param ctx
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(jsonElement, ChessGameImpl.class);
    }
}
