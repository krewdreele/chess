package serializers;

import chess.ChessPosition;
import chess.ChessPositionImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPositionSerialManager implements JsonDeserializer<ChessPosition>, JsonSerializer<ChessPosition> {
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

    /**
     * @param position
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessPosition position, Type type, JsonSerializationContext ctx) {
        return ctx.serialize(position, ChessPositionImpl.class);
    }
}
