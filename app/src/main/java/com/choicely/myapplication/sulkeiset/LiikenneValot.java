package com.choicely.myapplication.sulkeiset;

public class LiikenneValot {

    private int status;
    static final int punainen = 0, keltainen = 1, vihreä = 2;
    private TrafficLightInterface trafficLightInterface;

    public LiikenneValot(int status, TrafficLightInterface lightInterface) {
        this.status = status;
        trafficLightInterface = lightInterface;
    }

    public void nextStatus() {
        switch (status) {
            default:
            case 0:
                status = 1;
                break;
            case 1:
                status = 2;
                break;
            case 2:
                status = 0;
                break;
        }
        trafficLightInterface.lightCallback(status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        trafficLightInterface.lightCallback(status);
    }

    interface TrafficLightInterface {
        public void lightCallback(int status);
    }
}
