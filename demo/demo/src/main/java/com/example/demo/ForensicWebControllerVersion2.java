package com.example.demo;

import org.springframework.stereotype.Controller; // looks for web traffic
import org.springframework.ui.Model; //passes data directly from java code to html webpage template
import org.springframework.web.bind.annotation.GetMapping;//listens to webpage request, loads the page when url visited
import org.springframework.web.bind.annotation.PostMapping;//listens to data submission like when clicking submit
import org.springframework.web.context.request.WebRequest;//checks hidden data device type, time and security

@Controller
public class ForensicWebControllerVersion2{
    @GetMapping("/")/*when somebody clicks the link or types in this and then create a method which can be
     accessed by springboot and return type is string (name of html frontend file)
    */
    public String homePage(){
        return "report2"; // name of the html file here you create and would return like normal return value
    }

    @PostMapping("/calculate") //this needs to be on html so that we can connect it together
    public String processCalculation(WebRequest request, Model model){
    try{
        String victimId= request.getParameter("victimName");
        
        boolean isBodyTempRange= request.getParameter("isBodyTempRange")!=null;

        double bodyTemp=0.0;
        double bodyMinTemp;
        double bodyMaxTemp;

        if(isBodyTempRange){
            bodyMinTemp=Double.parseDouble(request.getParameter("minBodyTemp"));
            bodyMaxTemp=Double.parseDouble(request.getParameter("maxBodyTemp"));
        }else{
            bodyTemp=Double.parseDouble(request.getParameter("singleBodyTemp"));
            bodyMinTemp=bodyTemp;//apply the same terminal logic here
            bodyMaxTemp=bodyTemp;//apply the same terminal logic here
        }

        boolean isAmbientTempRange=request.getParameter("isAmbientTempRange")!=null;

            double ambientTemp=0.0;
            double minAmbientTemp;
            double maxAmbientTemp;

        if(isAmbientTempRange){
            minAmbientTemp= Double.parseDouble(request.getParameter("minAmbientTemp"));
            maxAmbientTemp=Double.parseDouble(request.getParameter("maxAmbientTemp"));
        }else{
            ambientTemp=Double.parseDouble(request.getParameter("ambientTemp"));
            minAmbientTemp=ambientTemp;
            maxAmbientTemp= ambientTemp;
        }

        String livorColor= request.getParameter("livorColor");
        boolean isLivorFixed= Boolean.parseBoolean(request.getParameter("isLivorFixed"));

        int[] rigor= new int[]{
            Integer.parseInt(request.getParameter("rigor0")),//this is the name that should be used because web controller will get this
            Integer.parseInt(request.getParameter("rigor1")),//same for this
            Integer.parseInt(request.getParameter("rigor2")),//same for this
        };
        //we instantiated the object here based on what user provided
        deceasedBodyVersion2 body= new deceasedBodyVersion2(
            victimId,bodyTemp,bodyMinTemp, bodyMaxTemp, ambientTemp, minAmbientTemp, maxAmbientTemp, livorColor,
            isLivorFixed, rigor, isBodyTempRange, isAmbientTempRange
        );
        model.addAttribute("BodyDetails", body.toString());
        model.addAttribute("algorReport", body.getAlgorMortisReport());
        model.addAttribute("livorReport", body.getLivorMortisReport());
        model.addAttribute("rigorReport", body.getRigorMortisReport());
        model.addAttribute("finalEstimate", body.getFinalEstimateHours());

        /* pass the report back because my html uses thymleaf th:"${report}" */
        model.addAttribute("report", body.getPostMortemReport());

        return "report2";/*thymeleaf will the five labelled attribute created above and inject them in corresponing
        placeholders inside the html file before showing it to the user
        */
    
    }catch(Exception e){
        model.addAttribute("error", "Error processing forensic data: " + e.getMessage());
        return "error"; //create an friendly error page to show here
    }
}
}


/*
web controller does these things
1)it receives the form from html
2)converts the values to var
3)creates an object, each dead body is an object
4)then call the report on the object
5)Send the report back to html through model and thymleaf displays it
*/