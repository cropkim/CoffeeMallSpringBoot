package com.shop.coffee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @GetMapping("/Buy_recommendation")
    public String showForm(Model model) {
        double minAttributeValue = 0;
        double maxAttributeValue = 10.00;

        model.addAttribute("minAttributeValue", minAttributeValue);
        model.addAttribute("maxAttributeValue", maxAttributeValue);
        model.addAttribute("coffeeAttributes", new CoffeeAttributesDto());
        model.addAttribute("recommendedCoffee", null);

        return "coffee/Buy_recommendation";
    }

    @PostMapping("/Buy_result")
    public String recommendCoffee(@ModelAttribute("coffeeAttributes") CoffeeAttributesDto coffeeAttributes, Model model) {
        Coffee recommendedCoffee = recommend(coffeeAttributes);
        if (recommendedCoffee != null) {
            model.addAttribute("recommendedCoffee", recommendedCoffee);
        }

        double minAttributeValue = 0;
        double maxAttributeValue = 10.00;

        model.addAttribute("minAttributeValue", minAttributeValue);
        model.addAttribute("maxAttributeValue", maxAttributeValue);
        model.addAttribute("coffeeAttributes", coffeeAttributes);

        return "coffee/Buy_result";
    }

    @GetMapping("/predict")
    public String showPredictRecommendationForm(Model model) {
        model.addAttribute("coffeeAttributes", new CoffeeAttributeDto_sell());
        model.addAttribute("predictedCoffee", null);

        return "coffee/predict";
    }

    @PostMapping("/predict_result")
    public String predictCoffee(@ModelAttribute("coffeeAttributes") CoffeeAttributeDto_sell coffeeAttributes, Model model) {
        Coffee predictedCoffee = predict(coffeeAttributes);
        if (predictedCoffee != null) {
            model.addAttribute("predictedCoffee", predictedCoffee);
        }

        model.addAttribute("coffeeAttributes", coffeeAttributes);

        return "coffee/predict_result";
    }


    private Coffee recommend(CoffeeAttributesDto coffeeAttributes) {
        try {
            // Convert the user input to a list
            List<String> userInput = coffeeAttributes.toList();

            // Convert the list to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInput = objectMapper.writeValueAsString(userInput);

            // Send the POST request to Flask server
            String prediction = sendPostRequest_reco(jsonInput);

            // Parse the prediction JSON
            Coffee coffee = parseRecommend(prediction);

            return coffee;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON.", e);
        }
    }
    private Coffee predict(CoffeeAttributeDto_sell coffeeAttributes) {
        try {
            // Convert the user input to a list
            List<String> userInput = coffeeAttributes.toList();

            // Convert the list to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonInput = objectMapper.writeValueAsString(userInput);

            // Send the POST request to Flask server
            String prediction = sendPostRequest_pre(jsonInput);

            // Parse the prediction JSON
            Coffee coffee = parsePrediction(prediction);

            return coffee;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON.", e);
        }
    }


    private String sendPostRequest_reco(String jsonInput) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:5000/recommend_coffee");  // Flask server URL

            // Set the request body as JSON
            StringEntity requestEntity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Execute the POST request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                } else {
                    throw new RuntimeException("No response received.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending POST request.", e);
        }
    }
    private String sendPostRequest_pre(String jsonInput) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:5000/predict_coffee");  // Flask server URL

            // Set the request body as JSON
            StringEntity requestEntity = new StringEntity(jsonInput, ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Execute the POST request
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    return EntityUtils.toString(responseEntity);
                } else {
                    throw new RuntimeException("No response received.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending POST request.", e);
        }
    }

    private Coffee parseRecommend(String recommendation) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode predictionNode = objectMapper.readTree(recommendation);

        Coffee coffee = new Coffee();
        coffee.setOrigin(predictionNode.get("Country.of.Origin").textValue());
        coffee.setRegion(predictionNode.get("Region").textValue());
        coffee.setVariety(predictionNode.get("Variety").textValue());
        coffee.setColor(predictionNode.get("Color").textValue());
        coffee.setProcessingMethod(predictionNode.get("Processing.Method").textValue());
        coffee.setMoisture(predictionNode.get("Moisture").doubleValue());
        return coffee;
    }
    private Coffee parsePrediction(String recommendation) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode predictionNode = objectMapper.readTree(recommendation);

        Coffee coffee = new Coffee();
        coffee.setAroma(predictionNode.get("Aroma").doubleValue());
        coffee.setFlavor(predictionNode.get("Flavor").doubleValue());
        coffee.setBody(predictionNode.get("Body").doubleValue());
        coffee.setSweetness(predictionNode.get("Sweetness").doubleValue());
        coffee.setAcidity(predictionNode.get("Acidity").doubleValue());
        coffee.setBalance(predictionNode.get("Balance").doubleValue());
        coffee.setUniformity(predictionNode.get("Uniformity").doubleValue());
        coffee.setAftertaste(predictionNode.get("Aftertaste").doubleValue());
        return coffee;
    }
}