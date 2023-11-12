package serializers;

import chess.ChessPieceImpl;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceSerializationManager implements JsonSerializer<ChessPieceImpl>, JsonDeserializer<ChessPieceImpl> {
    private static final String CLASS_META_KEY = "CLASS_META_KEY";
    /**
     * @param chessPiece
     * @param type
     * @param ctx
     * @return
     */
    @Override
    public JsonElement serialize(ChessPieceImpl chessPiece, Type type, JsonSerializationContext ctx) {
        JsonElement jsonEle = ctx.serialize(chessPiece, chessPiece.getClass());
        jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY,
                chessPiece.getClass().getCanonicalName());
        return jsonEle;
    }

    /**
     * @param jsonElement
     * @param type
     * @param ctx
     * @return
     * @throws JsonParseException
     */
    @Override
    public ChessPieceImpl deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        String className = jsonObj.get(CLASS_META_KEY).getAsString();
        try {
            Class<?> clz = Class.forName(className);
            return ctx.deserialize(jsonElement, clz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}
