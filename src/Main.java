import services.CrossRoadService;

public class Main {
    public static void main(String[] args) {
        System.out.println("***---Starting application---***");
        CrossRoadService crossRoadService = new CrossRoadService();
        crossRoadService.startCrossRoadProcess();
    }
}