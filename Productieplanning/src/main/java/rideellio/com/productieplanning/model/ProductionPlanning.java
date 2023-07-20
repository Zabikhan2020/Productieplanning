package rideellio.com.productieplanning.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initialiseert een nieuwe ProductionPlanning met lege lijsten voor bestellingen, producttypes en werkbezetting.
 * De paden naar de bestanden voor bestellingen, werkbezetting en producttypes worden ingesteld op de standaardlocaties.
 */
public class ProductionPlanning {
    private List<Bestelling> bestellingen;
    private Map<String, ProductType> productTypes;
    private Map<String, List<String>> werkBezetting;

    public static final String BESTELLINGEN_FILE_PATH = "src/main/resources/Bestellingen.txt";
    public static final String WERKBEZETTING_FILE_PATH = "src/main/resources/Bezetting 2023-04-03 tot 2023-04-07 .txt";
    public static final String PRODUCTTYPES_FILE_PATH = "src/main/resources/Product types.json";

    /**
     * Initialiseert een nieuwe ProductionPlanning met lege lijsten voor bestellingen, producttypes en werkbezetting.
     */
    public ProductionPlanning() {
        bestellingen = new ArrayList<>();
        productTypes = new HashMap<>();
        werkBezetting = new HashMap<>();
    }

    /**
     * Berekent de productieplanning op basis van de beschikbare voorraad en de bestellingen.
     * Het controleert of er voldoende voorraad is voor elke bestelling en geeft de planning terug als een String.
     * Als er onvoldoende voorraad is, wordt dit vermeld in de planning.
     * Als het producttype van een bestelling niet bekend is, wordt dit ook vermeld.
     *
     * @return de berekende productieplanning als een String
     */
    public String calculateProductionPlanning() {
        StringBuilder planning = new StringBuilder();

        // Loop door elke bestelling
        for (Bestelling bestelling : bestellingen) {
            String productType = bestelling.getProductType();

            // Controleer of het producttype bekend is
            if (productTypes.containsKey(productType)) {
                ProductType type = productTypes.get(productType);
                int productieTijd = type.getProductieTijd();

                // Controleer of er voldoende productietijd is voor de bestelling
                if (productieTijd > 0) {
                    // Verminder de productietijd met 1
                    type.setProductieTijd(productieTijd - 1);

                    // Voeg de bestelling toe aan de planning
                    planning.append("Bestelling: ")
                            .append(bestelling.getProductType())
                            .append(" - Beloofde dag van verzending: ")
                            .append(bestelling.getBeloofdeDagVanVerzending())
                            .append("\n");
                } else {
                    // Voeg de bestelling toe aan de planning met een melding van onvoldoende productietijd
                    planning.append("Bestelling: ")
                            .append(bestelling.getProductType())
                            .append(" - Onvoldoende productietijd beschikbaar")
                            .append("\n");
                }
            } else {
                // Voeg de bestelling toe aan de planning met een melding van onbekend producttype
                planning.append("Bestelling: ")
                        .append(bestelling.getProductType())
                        .append(" - Onbekend producttype")
                        .append("\n");
            }
        }

        // Return de planning als een string
        return planning.toString();
    }

    /**
     * Leest bestellingen in vanuit het opgegeven bestand en voegt ze toe aan de lijst met bestellingen.
     * Het verwacht een tekstbestand waarin elke regel een bestelling vertegenwoordigt, gescheiden door spaties.
     * Elke bestelling moet bestaan uit het producttype en de beloofde dag van verzending.
     * Alleen regels met precies twee delen worden geaccepteerd als geldige bestellingen.
     *
     * @param filePath het pad naar het bestand met bestellingen
     */
    public void loadBestellingen(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String productType = parts[0];
                    String beloofdeDagVanVerzending = parts[1];
                    Bestelling bestelling = new Bestelling(productType, beloofdeDagVanVerzending);
                    bestellingen.add(bestelling);
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * Leest de werkbezetting in vanuit het opgegeven bestand en slaat deze op in de werkBezetting-map.
     * Het verwacht een tekstbestand waarin elke regel een bezetting vertegenwoordigt, gescheiden door spaties.
     * Elke regel moet beginnen met een datum gevolgd door een of meer producttypes, gescheiden door spaties.
     * Alleen regels met ten minste twee delen (datum en minstens één producttype) worden geaccepteerd als geldige bezettingen.
     *
     * @param filePath het pad naar het bestand met werkbezetting
     */
    public void loadWerkBezetting(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String datum = parts[0];
                    List<String> productTypes = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        productTypes.add(parts[i]);
                    }
                    werkBezetting.put(datum, productTypes);
                }
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    /**
     * Leest de producttypes in vanuit het opgegeven JSON-bestand en slaat ze op in de productTypes-map.
     *
     * @param filePath het pad naar het JSON-bestand met producttypes
     */

    public void loadProductTypes(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            List<ProductType> productTypes = objectMapper.readValue(file, new TypeReference<List<ProductType>>() {
            });
            for (ProductType productType : productTypes) {
                this.productTypes.put(productType.getType(), productType);
            }
        } catch (IOException e) {
            handleException(e);
        }
    }


    /**
     * Behandelt een IOException door een foutmelding naar de standaardfoutstroom te sturen.
     *
     * @param e de IOException die wordt afgehandeld
     */
    private void handleException(IOException e) {
        System.err.println("Er is een fout opgetreden bij het verwerken van het bestand:");
        System.err.println(e.getMessage());
    }

    // Getters en setters

    public List<Bestelling> getBestellingen() {
        return bestellingen;
    }

    public void setBestellingen(List<Bestelling> bestellingen) {
        this.bestellingen = bestellingen;
    }

    public Map<String, ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(Map<String, ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public Map<String, List<String>> getWerkBezetting() {
        return werkBezetting;
    }

    public void setWerkBezetting(Map<String, List<String>> werkBezetting) {
        this.werkBezetting = werkBezetting;
    }

    @Override
    public String toString() {
        return "ProductionPlanning{" +
                "bestellingen=" + bestellingen +
                ", productTypes=" + productTypes +
                ", werkBezetting=" + werkBezetting +
                '}';
    }
}
