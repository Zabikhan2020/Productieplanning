package rideellio.com.productieplanning;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rideellio.com.productieplanning.model.ProductionPlanning;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ProductionPlanningTest {

    private ProductionPlanning productiePlanning;

    @BeforeEach
    public void setUp() {
        productiePlanning = new ProductionPlanning();
    }

    /**
     * Test het inladen van de producttypes met geldige gegevens.
     * Verwacht wordt dat de producttypes succesvol worden ingeladen en dat de grootte van de map overeenkomt met de verwachte grootte.
     */
    @Test
    public void loadProductTypes_GeldigeData_ProductTypesGeladen() {
        // Arrange
        String productTypesJson = "src/main/resources/Product types.json";
        productiePlanning.loadProductTypes(productTypesJson);

        // Act
        int verwachteProductTypesGrootte = 3;
        int daadwerkelijkeProductTypesGrootte = productiePlanning.getProductTypes().size();

        // Assert
        Assertions.assertEquals(verwachteProductTypesGrootte, daadwerkelijkeProductTypesGrootte);
    }

    /**
     * Test het inladen van de werkbezetting met geldige gegevens.
     * Verwacht wordt dat de werkbezetting succesvol wordt ingeladen en dat de grootte van de map overeenkomt met de verwachte grootte.
     */

    @Test
    public void loadWerkBezetting_GeldigeData_WerkBezettingGeladen() {
        // Arrange
        String werkBezettingData = "src/main/resources/Bezetting 2023-04-03 tot 2023-04-07.txt";

        // Assert
        Assertions.assertTrue(Files.exists(Paths.get(werkBezettingData)), "Het bestand 'Bezetting 2023-04-03 tot 2023-04-07.txt' bestaat niet.");

        // Act
        productiePlanning.loadWerkBezetting(werkBezettingData);
        int verwachteWerkBezettingGrootte = 4;
        int daadwerkelijkeWerkBezettingGrootte = productiePlanning.getWerkBezetting().size();

        // Assert
        Assertions.assertEquals(verwachteWerkBezettingGrootte, daadwerkelijkeWerkBezettingGrootte);
    }

    /**
     * Test het inladen van bestellingen met geldige gegevens.
     * Verwacht wordt dat de bestellingen succesvol worden ingeladen en dat de grootte van de lijst overeenkomt met de verwachte grootte.
     */

    @Test
    public void loadBestellingen_GeldigeData_BestellingenGeladen() {
        // Arrange
        String bestellingenData = "src/main/resources/Bestellingen.txt";

        // Assert
        Assertions.assertTrue(Files.exists(Paths.get(bestellingenData)), "Het bestand 'Bestellingen.txt' bestaat niet.");

        // Act
        productiePlanning.loadBestellingen(bestellingenData);
        int verwachteBestellingenGrootte = 3;
        int daadwerkelijkeBestellingenGrootte = productiePlanning.getBestellingen().size();

        // Assert
        Assertions.assertEquals(verwachteBestellingenGrootte, daadwerkelijkeBestellingenGrootte);
    }

    /**
     * Test het berekenen van de productieplanning met geldige gegevens.
     * Verwacht wordt dat de productieplanning correct wordt berekend.
     */
    @Test
    public void calculateProductionPlanning_GeldigeData_ProductionPlanningBerekend() {
        // Arrange
        String productTypesJson = "src/main/resources/Product types.json";
        String werkBezettingData = "src/main/resources/Bezetting 2023-04-03 tot 2023-04-07.txt";
        String bestellingenData = "src/main/resources/Bestellingen.txt";
        productiePlanning.loadProductTypes(productTypesJson);
        productiePlanning.loadWerkBezetting(werkBezettingData);
        productiePlanning.loadBestellingen(bestellingenData);

        // Act
        String verwachteProductionPlanning = "Bestelling: Elite - Beloofde dag van verzending: 2023-04-03\n" +
                "Bestelling: Enjoy - Beloofde dag van verzending: 2023-04-04\n" +
                "Bestelling: Elite - Beloofde dag van verzending: 2023-04-05\n";
        String daadwerkelijkeProductionPlanning = productiePlanning.calculateProductionPlanning();

        // Assert
        Assertions.assertEquals(verwachteProductionPlanning, daadwerkelijkeProductionPlanning);
    }
}
