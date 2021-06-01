package pt.ipbeja.estig.gridanim.model;

import java.util.Objects;

/**
 * Positions from 0 to number of lines and columns minus 1
 * @author João Paulo Barros
 * @version 2021/05/31
 */ 
final public class Position
{
   private final int line, col;
   
   public Position(int line, int col)
   {
      this.line = line;
      this.col = col;
   }

   public Position neighborPosition(Direction dir) {
      switch (dir) {
         case UP:    return new Position(this.getLine() - 1, this.getCol());
         case DOWN:  return new Position(this.getLine() + 1, this.getCol());
         case LEFT:  return new Position(this.getLine(), this.getCol() - 1);
         case RIGHT: return new Position(this.getLine(), this.getCol() + 1);
         default: assert(false); return null;
      }
   }

   public boolean isInsideAfter(Direction dir) {
      Position pos = this.neighborPosition(dir);
      return pos.isInside();
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      return "(" + line + ", " + col + ")";
   }

   /**
    * @return the line
    */
   public int getLine()
   {
      return this.line;
   }

   /**
    * @return the col
    */
   public int getCol()
   {
      return this.col;
   }

   /**
    * Checks if position is inside the board
    * @return true if inside, false otherwise
    */
   public boolean isInside()
   {
      return Position.isInside(this.getLine(), this.getCol());
   }
   
   /**
    * Checks if line col are inside tha board
    * @param line position line
    * @param col position column
    * @return true if inside, false otherwise
    */
   public static boolean isInside(int line, int col)
   {
      return 0 <= line && line < Model.N_LINES &&
             0 <= col && col < Model.N_COLS;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Position position = (Position) o;
      return line == position.line &&
              col == position.col;
   }

   @Override
   public int hashCode() {
      return Objects.hash(line, col);
   }
}
