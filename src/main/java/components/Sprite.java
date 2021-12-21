package components;

import org.joml.Vector2f;
import renderer.Texture;

public class Sprite {
    private Vector2f[] texCoords;
    private Texture texture;

    public Sprite(Texture _texture) {
        this.texture = _texture;

        Vector2f[] _texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
        this.texCoords = _texCoords;
    }

    public Sprite(Texture _texture, Vector2f[] _texCoords) {
        this.texture = _texture;
        this.texCoords = _texCoords;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }
}
