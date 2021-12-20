package jade;

public abstract class Scene {
    protected Camera camera;

    public Scene() {

    }

    public void __init__() {

    }

    public abstract void update(float dt);
}
