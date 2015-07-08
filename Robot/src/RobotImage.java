import java.awt.Color;
import java.awt.Graphics2D;

public class RobotImage {
	
	Vector2D[] pixels;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public RobotImage(int ix, int iy)
	{
		width = 50;
		height = 50;
		x = ix;
		y = iy;
		
		pixels = new Vector2D[width * height];
		
		int ctr = 0;
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				pixels[ctr++] = new Vector2D(i, j);
			}
		}
	}
	
	public void draw(Graphics2D g)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			int dx = ((int)pixels[i].x + x) - (width / 2);
			int dy = ((int)pixels[i].y + y) - (height / 2);
			
			g.setColor(Color.RED);
			g.drawRect(dx, dy, 1, 1);
		}
	}
	
	public void rotate(int angle)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i].rotateAroundPoint(angle, width / 2, height / 2);
		}
	}
	
	public void rotateAroundPoint(int angle, int px, int py)
	{
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i].rotateAroundPoint(angle, px, py);
		}
	}
}