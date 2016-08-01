import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;


/**
 * Pops up a JFrame window with a button grid that allows to play the game Reversi.
 * @author <em>Anna Sedlackova</em>
 */
public class Reversi extends JFrame implements ActionListener {
  
  /** Field for buttons on JFrame grid.*/
  private JButton[][] buttons; 
  
  /** Field that determines whose turn is it, set to player one.*/
  private boolean isPlayerOneTurn = true; 
  
  /**Adds title to the window*/
  TitledBorder title = new TitledBorder("Reversi");
  
  /*Changes the default color of the buttons*/
  private Color khaki = new Color(198,226,255);
  private Color maroon = new Color(159,182,205);
  
  /*Changes the color of the border.*/
  private Border border=BorderFactory.createLineBorder(maroon);
   
  /**
   * First constructor that creates a playing board with default buttons.
   * Creates a default 8*8 grid layout with no parameters. */
  public Reversi() {
    int n=8;
    JFrame grid = new JFrame();
    JPanel panel = new JPanel (new GridLayout(n,n)); //JPanel with grid layout.
    panel.setBorder(title);
    buttons = new JButton[n][n];
    grid.getContentPane().add(panel, "Center"); //Content centered.
    for (int i=0; i<buttons.length; i=i+1){ //Two loops that inititate the all the buttons to new JButtonw().
      for (int j=0; j<buttons[i].length; j=j+1){
        buttons[i][j]= new JButton(); 
        panel.add(buttons[i][j]); //Buttons added to the panel.
        buttons[i][j].addActionListener(this); //Action listener added to all buttons.
        buttons[i][j].setBackground(khaki);
        buttons[i][j].setBorder(border);
      }    
    }
    
    grid.setSize(500,500); //Size of a grid.
    grid.setVisible(true);
    buttons[3][3].setBackground(Color.WHITE); //Default buttons added to the center of the board.
    buttons [4][4].setBackground(Color.WHITE);
    buttons[3][4].setBackground(Color.BLACK);
    buttons[4][3].setBackground(Color.BLACK);
  }
  
  /** 
   * Second constructor that creates a playing board with defualt buttons.
   * @param boardsize.
   * Sets the board to a square with boardsize side.
   */
  public Reversi(int boardsize) {
    JFrame grid = new JFrame();
    JPanel panel = new JPanel (new GridLayout(boardsize,boardsize));
    panel.setBorder(title);
    buttons = new JButton[boardsize][boardsize];
    grid.getContentPane().add(panel, "Center");
    for (int i=0; i<buttons.length; i=i+1){
      for (int j=0; j<buttons[i].length; j=j+1){
        buttons[i][j]= new JButton(); 
        panel.add(buttons[i][j]);
        buttons[i][j].setBackground(khaki);
        buttons[i][j].setBorder(border);
        buttons[i][j].addActionListener(this);
      }    
    }
    grid.setSize(500,500);
    grid.setVisible(true);
    buttons[boardsize/2-1][boardsize/2-1].setBackground(Color.WHITE); //Places default buttons to the center of the board.
    buttons[boardsize/2][boardsize/2].setBackground(Color.WHITE);
    buttons[boardsize/2-1][boardsize/2].setBackground(Color.BLACK);
    buttons[boardsize/2][boardsize/2-1].setBackground(Color.BLACK);
  }
  
  /** 
   * Third constructor that creates a playing board with defualt buttons.
   * @param int height corresponds to the number of JButtons in y direction.
   * @param int width corresponds to the number of JButtons in x direction.
   * Sets the board to a rectangle with height vs width number of buttons.
   */
  public Reversi(int height, int width) {
    JFrame grid = new JFrame();
    JPanel panel = new JPanel (new GridLayout(height,width));
    panel.setBorder(title);
    buttons = new JButton[height][width];
    grid.getContentPane().add(panel, "Center");
    for (int i=0; i<buttons.length; i=i+1){
      for (int j=0; j<buttons[i].length; j=j+1){
        buttons[i][j]= new JButton(); 
        panel.add(buttons[i][j]);
        buttons[i][j].setBackground(khaki);
        buttons[i][j].setBorder(border);
        buttons[i][j].addActionListener(this);
      }    
    }
    grid.setSize(500,500);
    grid.setVisible(true);
    buttons[height/2-1][width/2-1].setBackground(Color.WHITE);
    buttons[height/2][width/2].setBackground(Color.WHITE);
    buttons[height/2-1][width/2].setBackground(Color.BLACK);
    buttons[height/2][width/2-1].setBackground(Color.BLACK);
  }
  
  /** 
   * Overrides actionPerformed
   * @param e, information about the button click event that occurred.
   * Uses method click only when a button is not colored.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton b = (JButton) e.getSource(); // this points to what b points to
    if (b.getBackground() != Color.WHITE && b.getBackground() != Color.BLACK)
      click(b);
  }
  
  /**
   * Switches the player's turns when a move is valid.
   * If move is not valid, a statement is printed in the interactions pane.
   * @param JButton n.
   */
  public void click(JButton n){
    if(hasValidMove(n, false, buttons)){ //Flips the color of the buttons, based on whose turn is it.
      if (isPlayerOneTurn)
        n.setBackground(Color.WHITE);
      else 
        n.setBackground(Color.BLACK);   
    }
    else{
      isPlayerOneTurn = !(isPlayerOneTurn);
      System.out.println("Move is not valid, press a different button."); //Brings up a warning if unallowed button is played.
    }
    isPlayerOneTurn = !(isPlayerOneTurn);
    System.out.println((isPlayerOneTurn)? "It's White's Turn" : "It's Black's Turn"); //Anonnounces whose turn is it.
    
    if(movesLeft(buttons) == false){ //Reference to a method movesLeft() that is called to announce if one of the players cant make a turn.
      System.out.println("You cannot make any more moves.");
      isPlayerOneTurn = !(isPlayerOneTurn);
      
      if(movesLeft(buttons)==false){
        System.out.println("You cannot make any more turns either, the games is over.");
        countScore(buttons); //If neither players can go, end of the game is announced.
      }
    }
  }
  
  /**
   * Gets the width of the board.
   * @return buttons.length, returns the number of buttons in a row.
   * @param JButton[][]board.
   */
  public int getBoardWidth(JButton[][]board){
    return board.length; 
  }
  
  /**
   * Gets the height of the board.
   * @param JButton[][]board.
   * @return buttons[0].length, return the number of buttons in a column.
   */
  public int getBoardHeight(JButton[][]board){
    return board[0].length;
  }
  
  /**
   * Checks whether a move is valid for eight different directions: up, down, right, left and four diagonal directions.
   * @param JButton n, button to be clicked.
   * @param boolean check.
   * @param JButton[][] board.
   * @return boolean, whether there is or is not a valid move.
   */
  public boolean hasValidMove(JButton n, boolean check, JButton[][] board){
    Color color;
    Color oppositeColor;
    
    if(isPlayerOneTurn){ //Initiates color and opposite color based on whose turn is it.
      color=Color.WHITE;
      oppositeColor=Color.BLACK;
    }
    else{
      color=Color.BLACK;
      oppositeColor=Color.WHITE;
    }
    
    boolean hasHorizontalLeft = false; //initiates all the direction to false
    boolean hasHorizontalRight=false;
    boolean hasVerticalDown = false;
    boolean hasVerticalUp = false;
    boolean hasDiagonalRightUp = false;
    boolean hasDiagonalRightDown = false;
    boolean hasDiagonalLeftUp = false;
    boolean hasDiagonalLeftDown = false;
    
    ArrayList<Integer>xCoordinates=new ArrayList<Integer>(); //creates two ArrayLists for x and y coordinates
    ArrayList<Integer>yCoordinates=new ArrayList<Integer>();
    int x = findButtonX(n, board);
    int y = findButtonY(n, board);
    
    //Checks if an opponent's piece is horizontal.
    if(x<=0)
      hasHorizontalLeft=false;
    else{
      hasHorizontalLeft = (board[y][x-1].getBackground().equals(oppositeColor)); //Horizontal true only if its next to opponents buttons.
    }
    
    if(x >= (getBoardWidth(board) - 1)) {
      hasHorizontalRight=false;
    }
    else{
      hasHorizontalRight = (board[y][x+1].getBackground().equals(oppositeColor));
    }
    
    //Left side horizontal.
    int a=1;
    if (hasHorizontalLeft){
      boolean anotherColor=false;
      boolean flag=true;
      
      ArrayList<Integer> currentY=new ArrayList<Integer>();
      ArrayList<Integer> currentX=new ArrayList<Integer>();
      
      while((x-a)>=0 && flag){ //Goes through a loop to check what are the neighbouring pieces and if they are the same color as current piece. 
        if (oppositeColor.equals(board[y][x-a].getBackground())){
          anotherColor=true;
          
          
          currentY.add(y); //Stores coordinates for flipping.
          currentX.add(x-a);
        }
        
        else if(color.equals(board[y][x-a].getBackground()) && anotherColor){
          hasHorizontalLeft=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
          
        }
        else {
          hasHorizontalLeft=false;
          flag=false;
        }
        a++;
      }
    }
    else {
      hasHorizontalLeft=false;
    }
    
    //Righ side horizontal.
    int b=1;
    if (hasHorizontalRight){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((x+b)<getBoardWidth(board) && flag){ //Goes through a loop to check what are the neighbouring pieces and if they are the same color as current piece.
        if (oppositeColor.equals(board[y][x+b].getBackground())){
          anotherColor=true;
          currentY.add(new Integer(y)); //Stores coordinates for flipping.
          currentX.add(new Integer(x+b));
        }
        
        else if(color.equals(board[y][x+b].getBackground()) && anotherColor){
          hasHorizontalRight=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
          
        }
        else {
          hasHorizontalRight=false;
          flag=false;
        }
        b++;
      }
    }
    else {
      hasHorizontalRight=false;
    }
    
    //Down side vertical.
    if(y >= getBoardHeight(board) - 1)
      hasVerticalDown=false;
    else{
      hasVerticalDown = (board[y+1][x].getBackground().equals(oppositeColor));
    }
    
    //Up side vertical.
    if(y<=0)
      hasVerticalUp=false;
    else{
      hasVerticalUp=(board[y-1][x].getBackground().equals(oppositeColor));
    }
    
    //Down side vertical.
    int c = 1;
    if (hasVerticalDown){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y+c)< getBoardHeight(board) && flag){
        if (oppositeColor.equals(board[y+c][x].getBackground())){
          anotherColor=true;
          currentY.add(y+c);
          currentX.add(x);
        }
        else if(color.equals(board[y+c][x].getBackground()) && anotherColor){
          hasVerticalDown=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasVerticalDown=false;
          flag=false;
        }
        c++;
      }
    }
    else
      hasVerticalDown=false;
    
    //Up side vertical.
    int d=1;
    if (hasVerticalUp){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y-d)>=0 && flag){
        if (oppositeColor.equals(board[y-d][x].getBackground())){
          anotherColor=true;
          currentY.add(y-d);
          currentX.add(x);
          hasVerticalUp=false;
        }
        else if(color.equals(board[y-d][x].getBackground()) && anotherColor){
          hasVerticalUp=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasVerticalUp=false;
          flag=false;
        }
        d++;
      }
    }
    else
      hasVerticalUp=false;
    
    //Checks for four directions of diagonals.
    if(x<=0 || y<=0)
      hasDiagonalLeftUp=false;
    else{
      hasDiagonalLeftUp = (board[y-1][x-1].getBackground().equals(oppositeColor));
    }
    
    if(x>= (getBoardWidth(board)-1) || y<=0)
      hasDiagonalRightUp=false;
    else{
      hasDiagonalRightUp=(board[y-1][x+1].getBackground().equals(oppositeColor));
    }
    
    if(x<=0 || y>=(getBoardHeight(board)-1))
      hasDiagonalLeftDown=false;
    else{
      hasDiagonalLeftDown=(board[y+1][x-1].getBackground().equals(oppositeColor));
    }
    
    if(x >= (getBoardWidth(board) - 1) || y>= (getBoardHeight(board) - 1))
      hasDiagonalRightDown=false;
    else{
      hasDiagonalRightDown=(board[y+1][x+1].getBackground().equals(oppositeColor));
    }
    
    //Diagonal left up.
    int e=1;
    if (hasDiagonalLeftUp){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y-e)>=0 && (x-e)>=0 && flag){
        
        if (oppositeColor.equals(board[y-e][x-e].getBackground())){
          anotherColor=true;
          currentY.add(y-e);
          currentX.add(x-e);
        }
        else if(color.equals(board[y-e][x-e].getBackground()) && anotherColor){
          hasDiagonalLeftUp=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasDiagonalLeftUp=false;
          flag=false;
        }
        e++;
      }
    }
    else
      hasDiagonalLeftUp=false;
    
    //Diagonal right up.
    int f=1;
    if (hasDiagonalRightUp){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y-f)>=0 && (x+f)< getBoardWidth(board) && flag){
        
        if (oppositeColor.equals(board[y-f][x+f].getBackground())){
          anotherColor=true;
          currentY.add(y-f);
          currentX.add(x+f);
        }
        else if(color.equals(board[y-f][x+f].getBackground()) && anotherColor){
          hasDiagonalRightUp=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasDiagonalRightUp=false;
          flag=false;
        }
        f++;
      }
    }
    else
      hasDiagonalRightUp=false;
    
    //Diagonal left down.
    int g=1;
    if (hasDiagonalLeftDown){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y+g)< getBoardHeight(board) && (x-g)>=0 && flag){
        
        if (oppositeColor.equals(board[y+g][x-g].getBackground())){
          anotherColor=true;
          currentY.add(y+g);
          currentX.add(x-g);
        }
        else if(color.equals(board[y+g][x-g].getBackground()) && anotherColor){
          hasDiagonalLeftDown=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasDiagonalLeftDown=false;
          flag=false;
        }
        g++;
      }
    }
    else
      hasDiagonalLeftDown=false;
    
    //Diagonal right down.
    int h=1;
    if (hasDiagonalRightDown){
      boolean anotherColor=false;
      boolean flag=true;
      ArrayList<Integer>currentY=new ArrayList<Integer>();
      ArrayList<Integer>currentX=new ArrayList<Integer>();
      
      while((y+h)< getBoardHeight(board) && (x+h)< getBoardWidth(board) && flag){
        if (oppositeColor.equals(board[y+h][x+h].getBackground())){
          anotherColor=true;
          currentY.add(y+h);
          currentX.add(x+h);
        }
        
        else if(color.equals(board[y+h][x+h].getBackground()) && anotherColor){
          hasDiagonalRightDown=true;
          flag=false;
          xCoordinates.addAll(currentX);
          yCoordinates.addAll(currentY);
        }
        else {
          hasDiagonalRightDown=false;
          flag=false;
        }
        h++;
      }
    }
    else
      hasDiagonalRightDown=false;
    
    if(hasHorizontalLeft || hasHorizontalRight ||  hasVerticalUp || hasVerticalDown || hasDiagonalRightUp || hasDiagonalRightDown ||
       hasDiagonalLeftUp || hasDiagonalLeftDown){ //If all buttons are true, it allows the coordinates stored in listArray to be flipped and a button is placed.
      
      if(!check)
        flippingButtons(yCoordinates, xCoordinates);
      
      return true;
    }
    else{
      return false;
    }
  }
  
  
  /**
   * Finds the x coordinate of the button
   *@param JButton button, button currently played.
   *@param JButton[][] board. 
   *@return int, i coordinate. 
   */
  public int findButtonX(JButton button, JButton[][] board){
    for (int i = 0; i < board.length ; i++){
      for (int j = 0; j < board[i].length ; j++){
        if (board[i][j] == button)
          return j;
      }
    }
    return -1;
  }
  
  /**
   * Finds the y coordinate of the button
   *@param JButton button, button currently played.
   *@param JButton[][] board.
   *@return int, i coordinate.
   */
  public int findButtonY(JButton button, JButton[][] board){
    for (int i = 0; i < board.length ; i++){
      for (int j = 0; j < board[i].length ; j++){
        if (board[i][j] == button)
          return i;
      }
    }
    return -1;
  }
  
  /**
   *Allows flipping of the buttons.
   *@param ArrayList<Integer> xCoordinates, list of x coordinates.
   *@param ArrayList<Integer> yCoordinates, list of y coordinates.
   */
  public void flippingButtons(ArrayList<Integer> xCoordinates, ArrayList<Integer> yCoordinates){
    for (int i=0; i < xCoordinates.size(); i++){
      if(buttons[xCoordinates.get(i)][yCoordinates.get(i)].getBackground().equals(Color.BLACK)) //Changes the color of stored x and y coordinates to the opposite color than original.
        buttons[xCoordinates.get(i)][yCoordinates.get(i)].setBackground(Color.WHITE);
      else
        buttons[xCoordinates.get(i)][yCoordinates.get(i)].setBackground(Color.BLACK);
    }
  }
  
  /**
   *Checks whether there are any legal moves left.
   *@return true or false, boolean value of whether there are any legal moves left.
   *@param JButton[][] board.
   *@return boolean, used for JUnit to confirm who is winning.
   */
  public boolean movesLeft(JButton[][]board){
    for (int i = 0; i < getBoardWidth(board); i++){
      for (int j = 0; j < getBoardHeight(board); j++){
        if (!(board[i][j].getBackground().equals(Color.BLACK)) && !(board[i][j].getBackground().equals(Color.WHITE)) ){
          if (hasValidMove(buttons[i][j], true, board)){
            return true;
          }
        }
      }
    }
    return false;
  }
  
  /**
   *Counts the final score. 
   *Prints out the result of the game in the interactions pane.
   * @param JButton[][] board.
   * @return String, used for JUnit to confirm who is winning.
   */
  public String countScore(JButton[][] board){
    int numberofBlack=0;
    int numberofWhite=0;
    
    for (int i = 0; i < getBoardWidth(board); i++){
      for (int j = 0; j < getBoardHeight(board); j++){
        if(board[i][j].getBackground().equals(Color.BLACK)){
          numberofBlack++;
        } 
        if(board[i][j].getBackground().equals(Color.WHITE)){
          numberofWhite++;
        }
      }
    }
    if (numberofBlack>numberofWhite){
      System.out.println("Black one wins!");
      return ("BLACK");
    }
    else if (numberofWhite>numberofBlack){
      System.out.println("White one wins!");
      return "WHITE";
    }
    else {
      System.out.println("Its a tie :(");
      return "TIE";
    }
  }
  
  /**
   * Main method that creates three different game boards:
   *java Reversi should start a game with a 8x8 grid.
   *java Reversi int should start a game with a nxn grid, min of n=2 and maximum of n=100. 
   *java Reversi int1 int2 should start a game with a 14x10 grid.
   */
  public static void main(String[] args){
    if(args.length==0){
      Reversi a = new Reversi();
    }
    else if(args.length==1){
      if (Integer.parseInt(args[0]) >0 && Integer.parseInt(args[0])<100){
        Reversi b=new Reversi(Integer.parseInt(args[0]));
      }
      else {
        System.out.println("You cannot make this board.");
      }
    }
    else {
      if (Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 100 && Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) < 100 ){
        Reversi c=new Reversi (Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      }
      else {
        System.out.println("You cannot make this board.");
      }
    }
  }
}

