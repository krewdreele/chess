package serializers;

import chess.ChessPiece;
import chess.ChessPieceImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceInterfaceManager implements JsonDeserializer<ChessPiece>, JsonSerializer<ChessPiece> {

    /**
     * @param jsonElement
     * @param type
     * @param ctx
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessPiece deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        return ctx.deserialize(jsonElement, ChessPieceImpl.class);
    }

    /**
     * @param chessPiece
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessPiece chessPiece, Type type, JsonSerializationContext ctx) {
        return ctx.serialize(chessPiece, ChessPieceImpl.class);
    }
}
