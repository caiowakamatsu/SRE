package me.legend;


import me.legend.GraphicsManager.GraphicsManager;
import me.legend.GraphicsManager.RenderPoint;
import me.legend.Interfaces.KeypressListener;
import me.legend.Interfaces.Renderable;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Application {

    private String title;
    private int height, width;
    private long window;
    private GraphicsManager graphicsManager;
    private KeypressListener keypressListener;
    private Renderable renderable;


    public Application(int width, int height){
        this(width, height, "SRE Application");
    }

    public Application(int width, int height, String title){
        this.title = title;
        this.width = width;
        this.height = height;
        this.graphicsManager = new GraphicsManager(this);

        this.keypressListener = null;
        this.renderable = null;
    }

    public void init(){
        if(this.keypressListener == null) System.out.println("[WARNING] You have not specified a keypress listener");
        if(this.renderable == null) System.out.println("[WARNING] You have not specified a render loop");
        this.start();
    }

    public int getWidth(){ return this.width; }
    public int getHeight(){ return this.height; }

    public RenderPoint getMousePoint(){
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posX, posY);
        return new RenderPoint((int) posX.get(0), (int) posY.get(0));
    }

    public int getMouseX(){
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, posX, null);
        return (int) posX.get(0);
    }

    public float getMouseY(){
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, null, posY);
        return (int) posY.get(0);
    }

    public void setKeypressListener(KeypressListener listener){
        this.keypressListener = listener;
    }

    public void setRenderLoop(Renderable renderable){
        this.renderable = renderable;
    }

    public void enableVSync(){
        glfwSwapInterval(1);
    }

    public void disableVsync(){
        glfwSwapInterval(0);
    }

    private void start(){
        GLFWErrorCallback.createPrint(System.err).set();
        if ( !glfwInit() ) throw new IllegalStateException("Error starting GLFW");
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if(window == NULL) throw new RuntimeException("Unable to create GLFW window");
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) // Just to make sure :p
                glfwSetWindowShouldClose(window, true);
            if(this.keypressListener != null) this.keypressListener.keyPressed(key);
        });
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        }
        glfwMakeContextCurrent(window);
        glfwShowWindow(window);
        loop();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop(){
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        while(!glfwWindowShouldClose(window)){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Render user stuff
            if(this.renderable != null) this.renderable.render(this.graphicsManager);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
}
