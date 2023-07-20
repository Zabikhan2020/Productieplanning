package rideellio.com.productieplanning.model;

public class Main {
    public static void main(String[] args) {
        ProductionPlanning planning = new ProductionPlanning();
        planning.loadBestellingen(ProductionPlanning.BESTELLINGEN_FILE_PATH);
        planning.loadProductTypes(ProductionPlanning.PRODUCTTYPES_FILE_PATH);
        planning.loadWerkBezetting(ProductionPlanning.WERKBEZETTING_FILE_PATH);

        String productionPlanning = planning.calculateProductionPlanning();
        System.out.println("Productieplanning:\n" + productionPlanning);
    }
}
