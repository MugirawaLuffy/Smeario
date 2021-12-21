package components;

import jade.Component;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

import java.awt.*;

public class SpriteRenderer extends Component {
    boolean isDirty = false; // dirty flag

    private Vector4f color;

    private Sprite sprite;
    private Transform lastTransform;


    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public SpriteRenderer(Sprite spr) {
        this.sprite = spr;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if(!this.lastTransform.equals(this.gameObject.transform)) { //Transformation has changed
            this.gameObject.transform.copy(this.lastTransform);
            this.isDirty = true;
        }
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite _sprite) {
        this.sprite = _sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f _color) {
        if(this.color.equals(color))
            this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
