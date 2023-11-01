package dev.lightdream.lambda;

import java.util.TimerTask;

public abstract class CancelableTimeTask extends TimerTask {

    protected boolean canceled;

    public abstract void execute();

    @Override
    public void run() {
        if (canceled) {
            return;
        }
        execute();
    }

    @Override
    public boolean cancel() {
        this.canceled = true;
        return super.cancel();
    }
}
