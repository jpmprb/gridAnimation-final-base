package pt.ipbeja.estig.gridanim.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pt.ipbeja.estig.gridanim.model.Position;

/**
 * Square image for game
 * 
 * @author João Paulo Barros
 * @version 2021/05/31
 * images generated in https://text2image.com/en/
 */
public class GameImage extends ImageView
{
	public final static int SIDE_SIZE = 30;

	private String imageName;
	private Position position;

	public GameImage(String imageName, Position position)
	{
		this.setImage(imageName);
		this.setPositionAndXY(position);
	}

	/**	@return the position */
	public Position getPosition()
	{
		return this.position;
	}

	/** @return the image name */
	public String getImageName() { return this.imageName; }

	/**	@return the line */
	public int getLine()
	{
		return this.position.getLine();
	}

	/**	@return the col */
	public int getCol()
	{
		return this.position.getCol();
	}

	/**
	 * sets the text and image for the button
	 * @param imageName image name to set
	 */
	public void setImage(String imageName)
	{
		this.imageName = imageName;
		String filename = "/resources/images/" + this.imageName + ".png";
		Image img = new Image(filename);
		this.setImage(img);
		this.setFitHeight(GameImage.SIDE_SIZE);
		this.setFitWidth(GameImage.SIDE_SIZE);
	}

	public void setPositionAndXY(Position position) {
		this.position = position;
		this.setX(position.getCol() * SIDE_SIZE);
		this.setY(position.getLine() * SIDE_SIZE);
	}

	public void updatePosition(int dCol, int dLine) {
		int col = this.position.getCol() + dCol;
		int line = this.position.getLine() + dLine;
		this.position = new Position(line, col);
	}

}
