/*
*This class shows the characteristics of the object. Each dead body will be the object
*
* @param victimId, bodyTemp, ambientTemp, livorColor, isLivorFixed
* return the object (makes it)
*/
// version 2.0 at shape (columbia University)

package com.example.demo;

public class deceasedBodyVersion2{ 
private String victimId;
private double bodyTemp;
private double bodyMinTemp; // for range
private double bodyMaxTemp; // for range
private double ambientTemp;
private double minAmbientTemp; // for range
private double maxAmbientTemp; // for range
private String livorColor;
private boolean isLivorFixed;
private int[] rigorStage;
private boolean isBodyTempRange; // to see if its a range or single value, making it easier for user to enter either a range or a single value
private boolean isAmbientTempRange;
  

public deceasedBodyVersion2(String victimId, double bodyTemp, double bodyMinTemp, double bodyMaxTemp,double ambientTemp, 
double minAmbientTemp, double maxAmbientTemp, String livorColor, boolean isLivorFixed, int[] rigorStage,
    boolean isBodyTempRange, boolean isAmbientTempRange )
  {
if(rigorStage == null || rigorStage.length!=3){
throw new IllegalArgumentException("The values should contain exactly three values.");
}

for(int stage: rigorStage){
    if(stage<0||stage>3){
        throw new IllegalArgumentException("Rigor stage value must be between 0 to 3 values");
    }
}

if(isBodyTempRange && bodyMinTemp>bodyMaxTemp){
    throw new IllegalArgumentException("Minimum body Temperature cannot be greater than maximum body Temp");
}

if(isAmbientTempRange && minAmbientTemp > maxAmbientTemp){
    throw new IllegalArgumentException("Minimum ambient Temperature cannot be greater than minimum ambient temp");
}

this.victimId= victimId;
this.bodyTemp= bodyTemp;
this.bodyMinTemp= bodyMinTemp;
this.bodyMaxTemp= bodyMaxTemp;
this.ambientTemp= ambientTemp;
this.minAmbientTemp= minAmbientTemp;
this.maxAmbientTemp= maxAmbientTemp;
this.livorColor= livorColor;
this.isLivorFixed= isLivorFixed;
this.rigorStage= rigorStage;
this.isBodyTempRange= isBodyTempRange;
this.isAmbientTempRange= isAmbientTempRange;
  }

// ↑ created constructors for both single and range 
  
  
  // This is for a single individual value
    public double calculateAlgorMortisFor27(double targetTemp) {
        // Standard Glaister formula estimation framework
        double hours= (37.0 - targetTemp) / 1.5; 
        if(hours<0){
            hours=0;
        }
        return hours;
    }

// --- Handles Range for algor mortis ---

// Algor Mortis: Returns a range string
public String calculateAlgorMortisRange(double minTemp, double maxTemp) {
double lowTime = (37.0 - maxTemp) / 1.5;
double highTime = (37.0 - minTemp) / 1.5;

if (lowTime < 0) lowTime = 0;
if (highTime < 0) highTime = 0;

if (lowTime == highTime) {
    return lowTime + " hours";
}

return lowTime + " to " + highTime + " hours";
}

// this is for single values
  public String calculateLivorMortis(boolean fixed) {
        if (fixed) {
            return "At least 6 to 8 hours ago (lividity is permanent)";
        } else {
            return "less than 6 hours ago(lividity is temporary)";
        }
    }

public String getLivorMortisReport() {
    return calculateLivorMortis(this.isLivorFixed);
}

// Rigor Mortis Estimation
public String calculateRigorMortis(int minStage, int maxStage) {
if (minStage == 3 && maxStage == 3) {
return "Approximately 12 hours ago (Peak rigor is exhibited)";
}
if (minStage == 0 && maxStage == 0) {
return "Either very recent (0-2 hours) OR completely resolved (36-48 hours)";
}
if (minStage == 1 && maxStage == 1) {return "Rigor is beginning (2 to 6 hours ago)";}
if (minStage == 2 && maxStage == 3){ return "Rigor is advancing (6 to 12 hours ago)";}
if(minStage == 2 && maxStage == 2){
    return "Rigor is advancing (6 to 12 hours ago)";
}
return "Rigor timeline estimation varies within the standard 6 to 15 hour window";
}

// Getters
public String getVictimId() { return victimId; }
public double getBodyTemp() { return bodyTemp; }
public double getBodyMinTemp()  { return bodyMinTemp; }
public double getBodyMaxTemp()  { return bodyMaxTemp; }
public double getAmbientTemp() { return ambientTemp; }
public double getMinAmbientTemp() { return minAmbientTemp; }
public double getMaxAmbientTemp() {return maxAmbientTemp; }
public String getLivorColor() { return livorColor; }
public boolean getIsLivorFixed() { return isLivorFixed; }
public int[] getRigorStage() { return rigorStage; }

// creating a getter to get algor mortis calc based on if its single/range (router method)

 public String getAlgorMortisReport() {
        if (this.isBodyTempRange) {
            return calculateAlgorMortisRange(
                    this.bodyMinTemp,
                    this.bodyMaxTemp
            );
        }

        return String.format(
                "%.2f hours",
                calculateAlgorMortisFor27(this.bodyTemp)
        );
    }

public String getRigorMortisReport() {
    int averageStage = (rigorStage[0] + rigorStage[1] + rigorStage[2]) / 3;
    return calculateRigorMortis(averageStage, averageStage);
}
  
// Final convergence method
public double getFinalEstimateHours() {

    double algorEstimate;

    if (this.isBodyTempRange) {
        algorEstimate = (37.0 - ((this.bodyMinTemp + this.bodyMaxTemp) / 2.0)) / 1.5;
    } 
    else {
        algorEstimate = (37.0 - this.bodyTemp) / 1.5;
    }
    if(algorEstimate < 0){
    algorEstimate = 0;
    }

    double livorEstimate = this.isLivorFixed ? 7.0 : 3.0;


    double rigorEstimate;

    int averageStage = (rigorStage[0] + rigorStage[1] + rigorStage[2]) / 3;

    if(averageStage == 3) {
    rigorEstimate = 12.0;
}
else if (averageStage == 2) {
    rigorEstimate = 9.0;
}
else if (averageStage == 1) {
    rigorEstimate = 4.0;
}
else {
    rigorEstimate = 2.0;
}


    return (algorEstimate + livorEstimate + rigorEstimate) / 3.0;
}


    public String getPostMortemReport() {
        String bodyTemperatureDisplay;

        if (isBodyTempRange) {
            bodyTemperatureDisplay =
                    String.format("%.2f to %.2f", bodyMinTemp, bodyMaxTemp);
        } else {
            bodyTemperatureDisplay =
                    String.format("%.2f", bodyTemp);
        }

        String ambientTemperatureDisplay;

        if (isAmbientTempRange) {
            ambientTemperatureDisplay =
                    String.format("%.2f to %.2f", minAmbientTemp, maxAmbientTemp);
        } else {
            ambientTemperatureDisplay =
                    String.format("%.2f", ambientTemp);
        }

        double finalHours = getFinalEstimateHours();

        return
                "==================================================\n" +
                "          COMPREHENSIVE POST MORTEM REPORT\n" +
                "==================================================\n" +
                "\n" +
                "VICTIM PROFILE\n" +
                "Victim ID: " + victimId + "\n" +
                "\n" +
                "OBSERVED DATA\n" +
                "Body Temperature: " + bodyTemperatureDisplay + "\n" +
                "Ambient Temperature: " + ambientTemperatureDisplay + "\n" +
                "Livor Color: " + livorColor + "\n" +
                "Livor Fixed: " + isLivorFixed + "\n" +
                "Rigor Stages: [" +
                    rigorStage[0] + ", " +
                    rigorStage[1] + ", " +
                    rigorStage[2] + "]\n" +
                "\n" +
                "--------------------------------------------------\n" +
                "ALGOR MORTIS ANALYSIS\n" +
                getAlgorMortisReport() + "\n" +
                "\n" +
                "LIVOR MORTIS ANALYSIS\n" +
                getLivorMortisReport() + "\n" +
                "\n" +
                "RIGOR MORTIS ANALYSIS\n" +
                getRigorMortisReport() + "\n" +
                "\n" +
                "--------------------------------------------------\n" +
                "FINAL CONVERGENCE CONCLUSION\n" +
                "Estimated time since death: " +
                String.format("%.2f", finalHours) +
                " hours ago\n" +
                "\n" +
                "NOTE: This is the PMI model's calculated estimate and\n" +
                "should be interpreted alongside scene and forensic evidence.\n" +
                "==================================================";
    }

// Setters
public void setVictimId(String newVictimId){victimId= newVictimId;}
public void setBodyTemp(double newBodyTemp){
bodyTemp= newBodyTemp; 
this.isBodyTempRange= false; } // the value is single so its not a range
public void setBodyTemp(double bodyMinTemp, double bodyMaxTemp){
this.bodyMinTemp= bodyMinTemp;
this.bodyMaxTemp= bodyMaxTemp;
this.isBodyTempRange=true;
}
public void setAmbientTemp(double newAmbientTemp){
ambientTemp= newAmbientTemp;
 this.isAmbientTempRange= false;} // the value is single so its not a range
public void setAmbientTemp(double minAmbientTemp, double maxAmbientTemp){
this.minAmbientTemp= minAmbientTemp;
this.maxAmbientTemp= maxAmbientTemp;
this.isAmbientTempRange= true;
}

public void setLivorColor(String newLivorColor){livorColor= newLivorColor;}
public void setIsLivorFixed(boolean newIsLivorFixed){isLivorFixed= newIsLivorFixed;}
public void setRigorStage(int[] newRigorStage){
    
     if (newRigorStage == null || newRigorStage.length != 3) {
            throw new IllegalArgumentException(
                    "Rigor data must contain exactly three values."
            );
        }

        for (int stage : newRigorStage) {
            if (stage < 0 || stage > 3) {
                throw new IllegalArgumentException(
                        "Rigor stage values must be between 0 and 3."
                );
            }
        }

        rigorStage = newRigorStage;

}
  
@Override // The compiler reports an error if this doesn't actually override a superclass method.
public String toString() {

String tempDisplay;
String ambientDisplay;

if(isBodyTempRange){
    tempDisplay = bodyMinTemp + " to " + bodyMaxTemp;
}
else{
    tempDisplay = String.valueOf(bodyTemp);
}

if(isAmbientTempRange){
    ambientDisplay = minAmbientTemp + " to " + maxAmbientTemp;
}
else{
    ambientDisplay = String.valueOf(ambientTemp);
}

return "Victim Id : " + victimId+
       "\n| Body Temperature: " + tempDisplay +
       "\n| Ambient Temperature: " + ambientDisplay +
       "\n| Lividity: " + livorColor +
        " (Fixed: " + isLivorFixed + ")" +
       "\n| Rigor Status: [" + 
       rigorStage[0] + ", " + 
       rigorStage[1] + ", " + 
       rigorStage[2] +
        "]" ;
}
}
/*everytime we update the object, we need to switch the state if there */ 
