package jade;

import org.joml.Vector2f;

public class Transform {
    public Vector2f position;
    public Vector2f scale;

    public Transform() {
        __init__(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f _position) {
        __init__(_position, new Vector2f());
    }

    public Transform(Vector2f _position, Vector2f _scale) {
        __init__(_position, _scale);
    }

    public void __init__(Vector2f _position, Vector2f _scale) {
        this.position = _position;
        this.scale = _scale;
    }
}
