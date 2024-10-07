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

        temp.start();
        water.start();
        spin.start();

        WashingProgram1 wp1 = null;
        WashingProgram2 wp2 = null;
        WashingProgram3 wp3 = null;

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            switch (n)
            {
                case 0:
                    if (wp1.isAlive()) { wp1.interrupt(); }
                    if (wp2.isAlive()) { wp2.interrupt(); }
                    if (wp3.isAlive()) { wp3.interrupt(); }
                    
                    temp.interrupt();
                    water.interrupt();
                    spin.interrupt();
                    break;
                case 1:
                    wp1 = new WashingProgram1(io, temp, water, spin);
                    wp1.start();
                    break;

                case 2:
                    wp2 = new WashingProgram2(io, temp, water, spin);
                    wp2.start();
                    break;

                case 3:
                    wp3 = new WashingProgram3(io, temp, water, spin);
                    wp3.start();
                    break;
            
                default:
                    break;
            }                    
        }
    }
};
