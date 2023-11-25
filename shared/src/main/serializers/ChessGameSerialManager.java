package serializers;

import chess.ChessGame;
import chess.ChessGameImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessGameSerialManager implements JsonDeserializer<ChessGame>, JsonSerializer<ChessGame>{
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

    /**
     * @param chessGame
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessGame chessGame, Type type, JsonSerializationContext ctx) {
       return ctx.serialize(chessGame, ChessGameImpl.class);
    }
}
