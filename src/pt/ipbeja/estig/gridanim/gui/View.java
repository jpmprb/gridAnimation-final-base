package pt.ipbeja.estig.gridanim.gui;

import pt.ipbeja.estig.gridanim.model.Mobile;
import pt.ipbeja.estig.gridanim.model.Position;

/**
 * The anim view
 *
 * @author João Paulo Barros
 * @version 2021/05/31
 */
public interface View {
    void updateMove(Mobile mobile, Position endPos);
}
