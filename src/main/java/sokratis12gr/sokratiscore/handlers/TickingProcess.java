package sokratis12gr.sokratiscore.handlers;

/**
 * sokratis12gr.sokratiscore.handlers
 * SokratisCore created by sokratis12GR on 7/26/2016 1:18 PM.
 */
public class TickingProcess implements IProcess {
    public int tick = 0;

    @Override
    public void updateProcess() {
        tick++;
    }

    @Override
    public boolean isDead() {
        return false;
    }
}