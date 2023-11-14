package serializers;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardDS implements JsonDeserializer<ChessBoard> {
    /**
     * @param jsonElement
     * @param type
     * @param ctx
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessBoard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(jsonElement, ChessBoardImpl.class);
    }
}
