package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

    public static void main(String[] args) throws InterruptedException {
        WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);

        WashingIO io = sim.startSimulation();

        ActorThread<WashingMessage> temp = new TemperatureController(io);
        ActorThread<WashingMessage> water = new WaterController(io);
        ActorThread<WashingMessage> spin = new SpinController(io);
        //WashingProgram3 wp3;

        temp.start();
        water.start();
        spin.start();

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            switch (n) {
                case 1:
                WashingProgram1 wp1 = new WashingProgram1(io, temp, water, spin);
                   wp1.start();
                break;

                case 2:
              //  WashingProgram2 wp2 = new WashingProgram2(io, temp, water, spin);
                 //  wp2.start();
                break;

                case 3:
                   WashingProgram3 wp3 = new WashingProgram3(io, temp, water, spin);
                   wp3.start();
                    break;
            
                default:
                    break;
            }                    

            
            // TODO:
            // if the user presses buttons 1-3, start a washing program
            // if the user presses button 0, and a program has been started, stop it
        }
    }
};
