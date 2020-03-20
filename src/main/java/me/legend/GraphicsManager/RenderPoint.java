package me.legend.GraphicsManager;

public class RenderPoint {
    public int x, y;

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }

    public RenderPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public RenderPoint(float x, float y){
        this((int) x, (int) y);
    }

    public RenderPoint(double x, double y){
        this((int) x, (int) y);
    }
}
