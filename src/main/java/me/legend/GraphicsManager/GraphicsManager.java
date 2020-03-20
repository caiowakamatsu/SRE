package me.legend.GraphicsManager;

import me.legend.Application;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsManager {
    private float r, g, b;
    private Application application;

    public GraphicsManager(Application app){
        this.application = app;
    }

    private float getFloatWidth(int x){
        return (float) x / this.application.getWidth() * 2 - 1;
    }

    private float getFloatHeight(int y){
        return (float) y / this.application.getHeight() * 2 - 1;
    }

    public void colour(float r, float g, float b){
        glColor3f(r, g, b);
    }

    public void drawPoint(RenderPoint point){ drawPoint(point.x, point.y); }
    public void drawPoint(int x, int y){
        glBegin(GL_POINT);
        glVertex2f(getFloatWidth(x), getFloatHeight(y));
        glEnd();
    }

    public void drawLine(RenderPoint a, RenderPoint b){ drawLine(a.x, a.y, b.x, b.y); }
    public void drawLine(int x1, int y1, int x2, int y2){
        glBegin(GL_LINES);
        glVertex2f(getFloatWidth(x1), getFloatHeight(y1));
        glVertex2f(getFloatWidth(x2), getFloatHeight(y2));
        glEnd();
    }

    public void drawCircle(RenderPoint point, float radius){ drawCircle(point.x, point.y, radius); }
    public void drawCircle(int x, int y, float radius){
        glBegin(GL_TRIANGLE_FAN); //BEGIN CIRCLE
        glVertex2f(getFloatWidth(x), getFloatHeight(y)); // center of circle
        for (int i=0; i<=20; i++)   {
            glVertex2f (
                    getFloatWidth((int) (x + (radius * Math.cos(i * Math.PI * 2 / 20)))),
                    getFloatHeight((int) (y + (radius * Math.sin(i * Math.PI * 2 / 20))))
            );
        }
        glEnd();
    }

    // For the polygon drawing we need to swap which one leads into the other
    public void drawPolygon(int... coordinates){
        if(coordinates.length % 2 != 0) throw new IllegalArgumentException("Invalid amount of coordinates specified, must be even!");
        RenderPoint[] points = new RenderPoint[coordinates.length / 2];
        for(int i=0; i<points.length; i++){
            points[i] = new RenderPoint(coordinates[i * 2], coordinates[i * 2 + 1]);
        }
        drawPolygon(points);
    }
    public void drawPolygon(RenderPoint... points){
        for(int i=0; i + 1<points.length; i++){
            drawLine(points[i * 2], points[i * 2 + 1]);
        }
        drawLine(points[points.length - 1], points[0]);
    }

}
