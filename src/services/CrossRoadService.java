package services;

import dtos.Car;
import dtos.CrossLight;
import enums.CrossLightEnum;
import enums.LightColourEnum;

import java.util.*;

/**
 *  Application for simulation cross lights on simple one cross road
 *  more info about task viz. <a href="https://bitbucket.org/cleverassets/crossroad-task/src/master/">...</a>
 */
public class CrossRoadService {
    private final Random random = new Random();
    private final CrossLightEnum[] CROSS_LIGHTS = CrossLightEnum.values();
    private CrossLight crossLightNorth;
    private CrossLight crossLightSouth;
    private CrossLight crossLightEast;
    private CrossLight crossLightWest;


    /**
     * startCrossRoadProcess main method that is being executed
     * Contains all logic
     */
    public void startCrossRoadProcess() {
        initializeCrossLights();
        Timer timer = new Timer();

        TimerTask mainTask = new TimerTask() {
            final List<Car> carsList = new ArrayList<>();
            final List<Car> carsToRemove = new ArrayList<>();

            @Override
            public void run() {
                carsToRemove.clear();
                Car car = carGenerator();
                if (!carsList.isEmpty()) {
                    carsList.forEach(c -> {
                        if (areNorthSouthCrossLightsGreen()) {
                            if (c.getCrossLight().name().equals(CrossLightEnum.NORTH.name()) || c.getCrossLight().name().equals(CrossLightEnum.SOUTH.name())) {
                                System.out.printf("Car %s is releasing %s traffic light %n", c.getId(), c.getCrossLight());
                                carsToRemove.add(c);
                                nextCarIsWaiting();
                            }
                        } else {
                            if (c.getCrossLight().name().equals(CrossLightEnum.EAST.name()) || c.getCrossLight().name().equals(CrossLightEnum.WEST.name())) {
                                System.out.printf("Car %s is releasing from previously %s traffic lights %n", c.getId(), c.getCrossLight());
                                carsToRemove.add(c);
                                nextCarIsWaiting();
                            }
                        }
                    });
                    carsList.removeAll(carsToRemove);
                }

                // check to which lights car arrives
                switch (car.getCrossLight()) {
                    case NORTH, SOUTH -> {
                        System.out.printf("*Car %s arriving to > %s%n", car.getId(), car.getCrossLight());
                        if (areNorthSouthCrossLightsGreen()) {
                            System.out.printf("Cross lights of %s and %s are %s%n",
                                    CrossLightEnum.NORTH.name(),
                                    CrossLightEnum.SOUTH.name(),
                                    LightColourEnum.GREEN.name());
                            System.out.printf("Car %s is passing through cross road %n", car.getId());
                        } else {
                            System.out.printf("Saving car %s to list, cause cross lights are %s%n", car.getId(), LightColourEnum.RED.name());
                            carsList.add(car);
                        }
                    }
                    case EAST, WEST -> {
                        System.out.printf("*Car %s arriving to > %s%n", car.getId(), car.getCrossLight());
                        if (!areNorthSouthCrossLightsGreen()) {
                            System.out.printf("Cross lights of %s and %s are %s%n",
                                    CrossLightEnum.EAST.name(),
                                    CrossLightEnum.WEST.name(),
                                    LightColourEnum.GREEN.name());
                            System.out.printf("Car %s is passing through cross road %n", car.getId());
                        } else {
                            System.out.printf("Saving car %s to list, cause cross lights are %s%n", car.getId(), LightColourEnum.RED.name());
                            carsList.add(car);
                        }

                    }
                }
            }
        };

        TimerTask crossLightsTimer = new TimerTask() {
            @Override
            public void run() {
                changeCrossLightColour();
            }
        };

        timer.scheduleAtFixedRate(mainTask, 0, 1000);
        timer.scheduleAtFixedRate(crossLightsTimer, 3000, 5000);
    }

    /**
     * nextCarIsWaiting method used for sleep 1s before another car is releasing from cross road/cross light
     */

    private void nextCarIsWaiting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * initializeCrossLight method used for initialize cross lights objects with default starting values
     */
    private void initializeCrossLights() {
        System.out.println("**Setting initial cross light state**");
        crossLightNorth = new CrossLight(CrossLightEnum.NORTH, LightColourEnum.GREEN);
        crossLightSouth = new CrossLight(CrossLightEnum.SOUTH, LightColourEnum.GREEN);
        crossLightEast = new CrossLight(CrossLightEnum.EAST, LightColourEnum.RED);
        crossLightWest = new CrossLight(CrossLightEnum.WEST, LightColourEnum.RED);
        printCrossLightStatus();
    }

    /**
     *  changeCrossLightColour method used for changing cross lights colour from red to green and opposite
     */
    private void changeCrossLightColour() {
        System.out.println("**Changing cross lights colour**");
        if (crossLightNorth.getLightColour().equals(LightColourEnum.GREEN)) {
            crossLightNorth.setLightColour(LightColourEnum.RED);
            crossLightSouth.setLightColour(LightColourEnum.RED);
        } else {
            crossLightNorth.setLightColour(LightColourEnum.GREEN);
            crossLightSouth.setLightColour(LightColourEnum.GREEN);
        }
        if (crossLightEast.getLightColour().equals(LightColourEnum.GREEN)) {
            crossLightEast.setLightColour(LightColourEnum.RED);
            crossLightWest.setLightColour(LightColourEnum.RED);
        } else {
            crossLightEast.setLightColour(LightColourEnum.GREEN);
            crossLightWest.setLightColour(LightColourEnum.GREEN);
        }
        printCrossLightStatus();
    }

    /**
     * printCrossLightStatus method used for just printing cross lights status
     */
    private void printCrossLightStatus() {
        System.out.printf("%s > %s%n", crossLightNorth.getName().name(), crossLightNorth.getLightColour().name());
        System.out.printf("%s > %s%n", crossLightSouth.getName().name(), crossLightSouth.getLightColour().name());
        System.out.printf("%s > %s%n", crossLightEast.getName().name(), crossLightEast.getLightColour().name());
        System.out.printf("%s > %s%n", crossLightWest.getName().name(), crossLightWest.getLightColour().name());
    }

    /**
     * areNorthSouthCrossLightsGreen method used for returning true/false whether cross lights for north and south are green
     * @return boolean
     */

    private boolean areNorthSouthCrossLightsGreen() {
        return crossLightNorth.getLightColour().equals(LightColourEnum.GREEN) && crossLightSouth.getLightColour().equals(LightColourEnum.GREEN);
    }

    /**
     * carGenerator method for generating new car.
     * @return Car object
     */
    private Car carGenerator() {
        String uniqueString = UUID.randomUUID().toString().substring(0, 3);
        return new Car(uniqueString.toUpperCase(), new Date(), randomSelectCrossLight());
    }


    /**
     * randomSelectCrossLight method used for randomly generate to which cross light new car is arriving
     * @return CrossLightEnum
     */
    private CrossLightEnum randomSelectCrossLight() {
        int randomCrossLightIndex = random.nextInt(CROSS_LIGHTS.length);
        return CROSS_LIGHTS[randomCrossLightIndex];
    }

}

