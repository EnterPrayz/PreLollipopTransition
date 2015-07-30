package com.kogitune.activity_transition.views;

import com.kogitune.activity_transition.core.MoveData;
import com.kogitune.activity_transition.core.TransitionAnimation;

/**
 * Created by EnterPrayz on 30.07.15 urecki22@gmailcom.
 */
public class ExitViewTransition {
    private MoveData moveData;

    public ExitViewTransition(MoveData moveData) {
        this.moveData = moveData;
    }

    public void exit() {
        TransitionAnimation.startExitAnimation(moveData, true, null);
    }
}
