package components;

import jade.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    public SpriteRenderer(Vector4f _color) {
        this.color = _color;
    }
    public SpriteRenderer() {
        this.color = new Vector4f();
    }

    Vector4f color;
    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    public Vector4f getColor() {
        return this.color;
    }
}
