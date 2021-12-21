package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private ImGuiLayer imguiLayer;

    private long glfwWindow;

    private int width, height;
    private String title;
    public float r,g,b,a;

    private static Window window = null; // Singleton

    private static Scene currentScene = null;

    private Window() {
        this.width = 1920;
        this.height = 1080;

        this.title = "Smeario - Mario on CRACK";
         r = b = g = a = 1.0f;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public static void changeScene(int newScene) {
        switch(newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.__init__();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.__init__();
                currentScene.start();
                break;
            default:
                assert false: "Unknown scene '" + newScene + "'";
        }
    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        __init__();
        __loop__();

        //Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate glfw and free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void __init__() {
        // Setup error callback to console.out
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize glfw.");
        }

        //configure glfw
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        //glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        //glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        //create window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if(glfwWindow == NULL)
            throw new IllegalStateException("Failed to create glfw window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight)-> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        glfwMakeContextCurrent(glfwWindow);

        //enable v-sync buffer swapping
        glfwSwapInterval(1);

        //Make window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();

        Window.changeScene(0);
    }

    public void __loop__() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);



            if(KeyListener.isKeyPressed(GLFW_KEY_SPACE))
                System.out.println("Space is pressed!");

            if(dt >= 0)
                currentScene.update(dt);

            this.imguiLayer.update(dt);

            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public static int getWidth() {
        return get().width;
    }
    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }
    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }
}
