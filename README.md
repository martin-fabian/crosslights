# Crossroad
Task related to this https://bitbucket.org/cleverassets/crossroad-task/src/master/

# Start application
clone repository and run main method, application will start simulate cross road with cross lights.

# Description
Firstly we initialize all necessary objects which is done inside initializeCrossLights() method.
Then we create two TimerTask representing two separate actions.
One TimerTask mainTask is running main program logic and second TaskTimer crossLightsTimer is for changing cross lights to oposite colour.
Firstly we generate new car as simulation that some car is arriving to crossroad. 
Then we check if there are any cars inside [] where we save cars that are waiting on cross lights with RED colour, this means that these cars cannot move through crossroad. 
If there are any cars, we need to check which cross lights are green and then iterate throught [] of cars to release those which arrived to actually green cross lights. Cars are ordered by time in []
so we dont need to reorder them. When car is found then it is released and we set timeout to 1s till next car can also release crosslights, this means that we remove these cars from [] where they were saved.
when forEach loop is finished or if no car fullfills this condition we go down and in switch we check to which crosslight new car has arrived. We take North, South as one and East, West as second so for simplicity we dont distiguish it in this switch. Then we check which lights are green and if car is arriving to green lights we dont do any action, object is printed to console and not saved. If car is arriving to red lights, we save it to [] for later release from [] when crosslights are switched [green->red, red->green].
Then we jump to the beginning of run() method and repeat all again untill application is stopped manually or after 30s application is automatically stopped.
