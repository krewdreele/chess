package serializers;

import chess.ChessBoard;
import chess.ChessBoardImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardSerialManager implements JsonDeserializer<ChessBoard>, JsonSerializer<ChessBoard>{
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

    /**
     * @param chessBoard
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessBoard chessBoard, Type type, JsonSerializationContext ctx) {
       return ctx.serialize(chessBoard, ChessBoardImpl.class);
    }
}
