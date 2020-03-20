package me.legend;

import me.legend.GraphicsManager.GraphicsManager;
import me.legend.Interfaces.KeypressListener;
import me.legend.Interfaces.Renderable;

public class Main {

    public static void main(String... args){
        Application application = new Application(1280, 720);
        application.enableVSync();
        application.setKeypressListener(new keyCallBack(application));
        application.setRenderLoop(new RenderLoop());
        application.init();
    }

}

class keyCallBack implements KeypressListener {

    private Application app;

    public keyCallBack(Application application){
        this.app = application;
    }

    @Override
    public void keyPressed(int keycode) {
        System.out.println("Keycode: " + keycode);
        System.out.println("Mouse Position: " + this.app.getMousePoint());
    }
}

class RenderLoop implements Renderable {
    @Override
    public void render(GraphicsManager graphics) {
        graphics.colour(1, 1, 1);
        graphics.drawLine(0, 400, 1280, 400);
        graphics.colour(0.7f, 0.2f, 0.9f);
        graphics.drawCircle(640, 370, 50f);
    }
}