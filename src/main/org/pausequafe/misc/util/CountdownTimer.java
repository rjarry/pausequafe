package org.pausequafe.misc.util;

public class CountdownTimer {
    
    private int minutes;
    private int seconds;

    public CountdownTimer(int minutes, int seconds) {
        this.setMinutes(minutes);
        this.setSeconds(seconds);
    }

    public boolean decrementOneSecond() {
        if (getSeconds() == 0) {
            if (getMinutes() == 0) {
                return false;
            } else {
                setSeconds(59);
                setMinutes(getMinutes() - 1);
            }
        } else {
            setSeconds(getSeconds() - 1);
        }
        return true;
    }

    public String printTime() {
        String time = "";
        if (getMinutes() < 10) {
            time += "0" + getMinutes();
        } else {
            time += getMinutes();
        }
        if (getSeconds() < 10) {
            time += ":0" + getSeconds();
        } else {
            time += ":" + getSeconds();
        }
        return time;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}


