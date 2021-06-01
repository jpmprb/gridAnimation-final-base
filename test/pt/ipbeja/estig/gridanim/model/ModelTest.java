package pt.ipbeja.estig.gridanim.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipbeja.estig.gridanim.gui.View;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private View testView;

    @BeforeEach
    void setUp() {
        this.testView = new View() {
            @Override
            public void updateMove(Mobile mobile, Position endPos) {
                mobile.moveTo(endPos);
            }
        };
    }

    @Test
    void moveHero() {
//        Model model = new Model(testView);
//        Position beginPos = model.getHero().getPos();
//        model.moveHeroInDirection(Direction.RIGHT);
//        Position expectedPos = new Position(beginPos.getLine(), beginPos.getCol() + 1);
//        assertEquals(expectedPos, model.getHero().getPos());
//
//        beginPos = model.getHero().getPos();
//        model.moveHeroInDirection(Direction.DOWN);
//        expectedPos = new Position(beginPos.getLine() + 1, beginPos.getCol());
//        assertEquals(expectedPos, model.getHero().getPos());
//
//        beginPos = model.getHero().getPos();
//        model.moveHeroInDirection(Direction.LEFT);
//        expectedPos = new Position(beginPos.getLine(), beginPos.getCol() - 1);
//        assertEquals(expectedPos, model.getHero().getPos());
//
//        beginPos = model.getHero().getPos();
//        model.moveHeroInDirection(Direction.UP);
//        expectedPos = new Position(beginPos.getLine() - 1, beginPos.getCol());
//        assertEquals(expectedPos, model.getHero().getPos());
    }

    @Test
    void moveMonsters() {
        Model model = new Model(testView);

        for (int i = 1; i <= 5; i++) {
            System.out.println(model.addNewMonster(i + ""));
        }
        System.out.println("------------------------");

        Thread t = model.moveMonsters(5);
        try {
            t.join(); // current thread waits for thread t to end
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}