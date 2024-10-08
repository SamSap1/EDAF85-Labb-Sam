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
        ActorThread<WashingMessage> currProg = null;

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            switch (n)
            {
                case 0:
                    if (currProg.isAlive()) 
                    { 
                        currProg.interrupt(); 
                        currProg = null; 
                    }
                    break;
                    
                case 1:
                    temp.start();
                    water.start();
                    spin.start();

                    currProg = new WashingProgram1(io, temp, water, spin);
                    currProg.start();
                    break;

                case 2:
                    temp.start();
                    water.start();
                    spin.start();

                    currProg = new WashingProgram2(io, temp, water, spin);
                    currProg.start();
                    break;

                case 3:
                    temp.start();
                    water.start();
                    spin.start();

                    currProg = new WashingProgram3(io, temp, water, spin);
                    currProg.start();
                    break;
            
                default:
                    break;
            }                    
        }
    }
};
