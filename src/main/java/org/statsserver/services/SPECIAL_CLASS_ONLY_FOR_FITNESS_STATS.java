package org.statsserver.services;

import java.text.DecimalFormat;
import java.util.HashMap;

public class SPECIAL_CLASS_ONLY_FOR_FITNESS_STATS {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public static HashMap setFitnessStatsKgFields(HashMap data){
        Double gewichtInKg = getDoubleValueFromHashGetter(data.get("Gewicht in kg"));
        String metingDoor = getStringValueFromHashGetter(data.get("Meting door:"));
        if(gewichtInKg != null && metingDoor != null){

            if(metingDoor.equals("BasicFit apparaat")){
                Double spierMassaPercentage = getDoubleValueFromHashGetter(data.get("Spiermassa in PERCENTAGE (nu met kommagetal!)"));
                Double currentSpierMassaInKg = getDoubleValueFromHashGetter(data.get("Spiermassa in KG"));

                Double lichaamsVochtPercentage = getDoubleValueFromHashGetter(data.get("Lichaamsvocht in PERCENTAGE (nu met kommagetal)"));
                Double currentLichaamsVochtInKg = getDoubleValueFromHashGetter(data.get("Lichaamsvocht in KG"));

                Double botMassaPercentage = getDoubleValueFromHashGetter(data.get("Botmassa in PERCENTAGE (nu met kommagetal)"));
                Double currentBotMassaInKg = getDoubleValueFromHashGetter(data.get("Botmassa in KG"));


                String spierMassaInKg = getDoubleValueOfKgByFormula(spierMassaPercentage, gewichtInKg, currentSpierMassaInKg);
                String lichaamsVochtInKg = getDoubleValueOfKgByFormula(lichaamsVochtPercentage, gewichtInKg, currentLichaamsVochtInKg);
                String botMassaInKg = getDoubleValueOfKgByFormula(botMassaPercentage, gewichtInKg, currentBotMassaInKg);
                System.out.println("=============================");
                data.put("Spiermassa in KG", (spierMassaInKg != null ? spierMassaInKg : ""));
                System.out.println("Spiermasssa KG: "+spierMassaInKg+ " van spierpercentage: "+spierMassaPercentage+" van original: "+data.get("Spiermassa in PERCENTAGE (nu met kommagetal!)"));
                data.put("Lichaamsvocht in KG", (lichaamsVochtInKg != null ? lichaamsVochtInKg : ""));
                System.out.println("Lichaamsvocht in KG: "+lichaamsVochtInKg+" van lichaamsvochtpercentage: "+lichaamsVochtPercentage+" van original: "+data.get("Lichaamsvocht in PERCENTAGE (nu met kommagetal)"));
                data.put("Botmassa in KG", (botMassaInKg != null ? botMassaInKg : ""));
                System.out.println("Botmassa in KG: "+botMassaInKg+" van botmassa percentage: "+botMassaPercentage+" van original: "+data.get("Botmassa in PERCENTAGE (nu met kommagetal)"));
                System.out.println("=============================");
            }
        }
        return data;
    }

    private static Double getDoubleValueFromHashGetter(Object hashValue){
        if(hashValue != null && !hashValue.toString().isBlank()){
            return Double.parseDouble(hashValue.toString().replace(",", "."));
        }
        return null;
    }
    private static String getStringValueFromHashGetter(Object hashValue){
        if(hashValue != null && !hashValue.toString().isBlank()){
            return hashValue.toString();
        }
        return null;
    }

    private static String getDoubleValueOfKgByFormula(Double percentage, Double gewichtInKg, Double currentValue) {
        if(percentage == null){
            return null;
        }
        if(currentValue == null){
            Double kgs = percentage / 100 * gewichtInKg;
            return decimalFormat.format(kgs);
        }
        return currentValue.toString();
    }
}
