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

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
}
