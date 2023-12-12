package serializers;

import chess.ChessMove;
import chess.ChessMoveImpl;
import chess.ChessPosition;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessMoveSerialManager implements JsonDeserializer<ChessMove>, JsonSerializer<ChessMove> {
    /**
     * @param jsonElement
     * @param type
     * @param ctx
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(jsonElement, ChessMoveImpl.class);
    }

    /**
     * @param position
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessMove position, Type type, JsonSerializationContext ctx) {
        return ctx.serialize(position, ChessMoveImpl.class);
    }
}
