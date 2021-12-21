package jade;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;


public class LoadingScene extends Scene{
    Spritesheet sprites;
    private GameObject obj1;
    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0F;
    SpriteRenderer obj1Sprite;
    public LoadingScene() {

    }

    @Override
    public void __init__() {
        loadResources();
        this.activeGameObject = obj1;

        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(400,50), new Vector2f(256,256)));
        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(0, 50), new Vector2f(256, 256)));
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite = sprites.getSprite(0);
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16,16,26,0));
    }

    @Override
    public void update(float dt) {
        obj1.transform.position.x += 40*dt;
        spriteFlipTimeLeft -= dt;
        if(spriteFlipTimeLeft <= 0){
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if(spriteIndex > 3) spriteIndex = 0;
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }
}
