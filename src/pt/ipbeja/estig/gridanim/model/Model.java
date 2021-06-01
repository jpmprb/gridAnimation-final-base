package pt.ipbeja.estig.gridanim.model;

import pt.ipbeja.estig.gridanim.gui.View;

import java.util.*;

/**
 * Grid anim model
 *
 * @author João Paulo Barros
 * @version 2021/06/01
 */
final public class Model {
    private static final int TIME_IN_MILLS_BETWEEN_MOVES = 1;

    public static final int N_LINES = 20;
    public static final int N_COLS = 20;

    private final static Random RAND = new Random();
    private int[][] board;
    private Set<Position> freePositions;

    private List<Monster> monsters;

    private View view;

    /**
     * Creates board in winning position
     */
    public Model(View view) {

        this.view = view;
        this.monsters = Collections.synchronizedList(new ArrayList<>());
        this.freePositions = new HashSet<>();
        this.resetBoard();
    }

    /**
     * Creates a random mixed board starting from a winning position
     *
     * @param minIter        minimum number of iterations to mix board
     * @param additionalIter maximum number of additional iterations to mix board
     */
    public Model(View view, int minIter, int additionalIter) {
        this(view); // call default constructor Fifteen()
    }

    public Monster addNewMonster(String name) {
        Position freePos = this.getRandomFreePos();
        Monster monster = new Monster(name, freePos);
        this.monsters.add(monster);
        return monster;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }

    /**
     * Move monsters
     * Moving monsters are removed from this.monsters list
     * @param nMovements number of movements to try
     * @return the thread doing the movement
     */
    public Thread moveMonsters(long nMovements) {
        Runnable r = () -> {
            for (long i = 0; i < nMovements; i++) {
                synchronized (this.monsters) {
                    if (this.monsters.size() > 0) {
                        int randMonsterPosition = RAND.nextInt(this.monsters.size());
                        Monster randMonster = this.monsters.get(randMonsterPosition);
                        this.monsters.remove(randMonsterPosition);
                        Position beginPos = randMonster.getPos();
                        Direction randomDirection = Direction.values()[RAND.nextInt(Direction.values().length)];
                        if (beginPos.isInsideAfter(randomDirection)) {
                            System.out.println(randMonster + " is moving");
                            Position endPos = beginPos.neighborPosition(randomDirection);
                            this.view.updateMove(randMonster, endPos);
                            Model.sleep(TIME_IN_MILLS_BETWEEN_MOVES);
                        } else {
                            this.monsters.add(randMonster);
                        }
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
        return t;
    }

    public void moveMobile(Mobile mobile, Position endPos) {
        mobile.moveTo(endPos);
        this.monsters.add((Monster) mobile);
    }

    /**
     * Move hero in specified direction
     *
     * @param direction movement direction
     */
    public void moveHeroInDirection(Direction direction) {

        // TODO
        this.view.updateMove(null, null);
    }

    /**
     * @return fifteen board content in text form
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int line = 0; line < N_LINES; line++) {
            for (int col = 0; col < N_COLS; col++) {
                s.append(String.format("%2d, ", this.board[line][col]));
            }
            s.setLength(s.length() - 2);
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Get a random posiiton that is free from monsters
     *
     * @return the random free position
     */
    private Position getRandomFreePos() {
        Position[] positions = new Position[this.freePositions.size()];
        this.freePositions.toArray(positions);
        return positions[RAND.nextInt(this.freePositions.size())];
    }

    /**
     * Puts the board in the starting position
     */
    private void resetBoard() {
        this.board = new int[Model.N_LINES][Model.N_COLS];
        int pos = 1;
        for (int line = 0; line < Model.N_LINES; line++) {
            for (int col = 0; col < Model.N_COLS; col++) {
                this.board[line][col] = pos++;
                this.freePositions.add(new Position(line, col)); // all are free
            }
        }
    }

    /**
     * Wait the specified time in milliseconds
     * More info at  https://stackoverflow.com/questions/26703324/why-do-i-need-to-handle-an-exception-for-thread-sleep
     *
     * @param sleepTime time in miliseconds
     */
    public static void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("Thread " + Thread.currentThread() + " was interrupted!");
            e.printStackTrace();
        }
    }

}
