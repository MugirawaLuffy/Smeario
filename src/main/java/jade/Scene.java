package jade;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    public Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<GameObject>();

    public Scene() {

    }

    public void __init__() {

    }
    public void start() {
        for(GameObject go : gameObjects) {
            go.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(GameObject go) {
        if(!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }
    }

    public abstract void update(float dt);

    public Camera camera() {
        return this.camera;
    }
}
