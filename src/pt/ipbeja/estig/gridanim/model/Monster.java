package pt.ipbeja.estig.gridanim.model;

/**
 * The Monster class
 *
 * @author João Paulo Barros
 * @version 2021/05/31
 */
public class Monster extends Mobile {

    public Monster(String name, Position pos) {
        super(name, pos);
    }

    @Override
    public String toString() {
        return "Monster{ " + getName() + ", " +
                "pos=" + this.getPos() +
                " }";
    }
}
