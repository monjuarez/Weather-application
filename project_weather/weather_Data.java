//run 6 added pictures
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.net.*;  
import java.io.*;
public class weather_Data extends JFrame{
    private JPanel panel; 
    private JLabel instructions;
    private JLabel temperature;
    private JLabel errorMessage;
    private JLabel fLike;
    private JLabel humidity;
    private JLabel picLabel;
    private JLabel pressure; //pressure 
    private JTextField cityTextField;
    private JButton startProcess;
    private final int WINDOW_WIDTH=700;
    private final int WINDOW_HEIGHT=800;
    public weather_Data (){
        super("Weather Data");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        add(panel);
        setVisible(true);
    }    
    private void buildPanel(){
        instructions = new JLabel("enter city");
        cityTextField = new JTextField(10);
        startProcess = new JButton("enter");
        temperature = new JLabel("");
        fLike = new JLabel("");
        humidity = new JLabel("");
        errorMessage = new JLabel("check spelling");
        picLabel=new JLabel(new ImageIcon(loadingWeather()));
        startProcess.addActionListener(new update());
        panel = new JPanel();
        panel.add(picLabel);
        panel.add(instructions);        
        panel.add(cityTextField);
        panel.add(startProcess);
        panel.add(temperature);
        panel.add(fLike);
        panel.add(humidity);
        panel.add(errorMessage);
        errorMessage.setVisible(false);
    }
    //automatically checks londons weather to start with
    private String loadingWeather(){
        String currentTemp="";
        String feels="";
        String currentHumidity="";
        String api ="https://api.openweathermap.org/data/2.5/weather?q=london&appid=abd41b0a48d3c90ed014f3841011e455&units=imperial";
        String WeatherImageReturn = "";
        try{
            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() != 200) {
                //if error occurs all labels relating to the weather_Data will clear and will display the error message
                errorMessage.setVisible(true);
                temperature.setText("");
                fLike.setText("");
                humidity.setText("");
            }else{ 
                //sets up the labels 
                //puts the information from the api into a string
                String s1  = getUrlContents(api);
                //the string holding the api information will be duplicated to find the weather_Data description
                String weatherLine = s1;
                System.out.println(s1);//remove at the end to remove terminal----------------------------
                char marks = '"';//used to include quoation marks in strings
                //find the current weather description
                String find = marks +"main"+marks+":"+marks; //find = "main":"
                String [] words = weatherLine.split(find);
                for (int i = 0; i < words.length; i++) {
                    weatherLine = words[i];
                }   
                weatherLine = weatherLine.split(marks+",")[0]; //sets weatherLine to equal the weather_Data description
                //finds the current temperature and sets the text
                find = marks +"temp"+marks+":"; //finds "tem":
                words = s1.split(find);
                for (int i = 0; i < words.length; i++) {
                    currentTemp = words[i];
                }   
                //might have to improve to get values with more than 2 numbers---------------------------
                temperature.setText("current temp in London" + " is "+currentTemp.substring(0,2));//substring used to get only the first two numbers
                //finds what the temperature feels like currently
                find =marks+"feels_like"+marks+":"; //finds "feels_like":
                words = currentTemp.split(find);
                for (int i = 0; i < words.length; i++) {
                    feels = words[i];
                }   
                //might have to improve to get values with more than 2 numbers---------------------------
                fLike.setText("feels like "+feels.substring(0,2));
                //finds current humidity
                find = marks+"humidity"+marks+":"; //finds "humidity";
                words = feels.split(find);
                for (int i = 0; i < words.length; i++) {
                    currentHumidity = words[i];
                } 
                //might have to improve to get value 100 if needed---------------------------
                humidity.setText("humidity: "+currentHumidity.substring(0,2));
                cityTextField.setText("");
                System.out.println(weatherLine);//delete after 
                // finds a match to the current weather description of london
                if ((weatherLine.equals("Thunderstorm"))==true){
                    WeatherImageReturn ="images/thunderstorm.png";
                }else if (((weatherLine.equals("Drizzle")) == true)||((weatherLine.equals("Rain"))==true)){
                    WeatherImageReturn = "images/rain.png";
                }else if ((weatherLine.equals("Snow"))==true){
                    WeatherImageReturn = "images/snow.png";    
                }else if((weatherLine.equals("Clear"))==true){
                    WeatherImageReturn = "images/sun.png";    
                }else if ((weatherLine.equals("Clouds"))==true){
                    WeatherImageReturn = "images/clouds.png";    
                }else{
                    WeatherImageReturn = "images/Drawing.sketchpad.png";
                }
            }    
        } catch (Exception error ) {
            errorMessage.setVisible(true);
        }
        //returns the path of image
        return WeatherImageReturn;
    }
    //will just be used once in the very begining to get londons weather conditions
    private static String getUrlContents(String theUrl){  
            StringBuilder content = new StringBuilder();  
            try  {  
                URL url = new URL(theUrl); 
                URLConnection urlConnection = url.openConnection(); 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
                String line;  
                while ((line = bufferedReader.readLine()) != null)  {  
                    content.append(line + "\n");  
                }  
                bufferedReader.close();  
            }  catch(Exception e) {  
                e.printStackTrace();  
            }  
            return content.toString();  
        }  
    private class update implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String city;
            city=cityTextField.getText();
            errorMessage.setVisible(false);
            if (e.getSource()==startProcess){
                //part of the link to open api
                String begin= "https://api.openweathermap.org/data/2.5/weather?q=";
                String end = "&appid=abd41b0a48d3c90ed014f3841011e455&units=imperial";
                //current weather_Data varibles
                String currentTemp="";
                String feels="";
                String currentHumidity="";
                try {
                    //starts the api
                    URL url = new URL(begin+city+end);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    if (conn.getResponseCode() != 200) {
                        //if error occurs all labels relating to the weather_Data will clear and will display the error message
                        errorMessage.setVisible(true);
                        temperature.setText("");
                        fLike.setText("");
                        humidity.setText("");
                    }else{ 
                        //sets up the labels 
                        //puts the information from the api into a string
                        String s1  = getUrlContents(begin+city+end);
                        //the string holding the api information will be duplicated to find the weather_Data description
                        String weatherLine = s1;
                        System.out.println(s1);//remove at the end to remove terminal----------------------------
                        char marks = '"';//used to include quoation marks in strings
                        //find the current weather_Data description
                        String find = marks +"main"+marks+":"+marks; //find = "main":"
                        String [] words = weatherLine.split(find);
                        for (int i = 0; i < words.length; i++) {
                            weatherLine = words[i];
                        }   
                        weatherLine = weatherLine.split(marks+",")[0]; //sets weatherLine to equal the weather_Data description
                        //finds the current temperature and sets the text
                        find = marks +"temp"+marks+":"; //finds "tem":
                        words = s1.split(find);
                        for (int i = 0; i < words.length; i++) {
                            currentTemp = words[i];
                        }   
                        //might have to improve to get values with more than 2 numbers---------------------------
                        temperature.setText("current temp in "+city + " is "+currentTemp.substring(0,2));//substring used to get only the first two numbers
                        //finds what the temperature feels like currently
                        find =marks+"feels_like"+marks+":"; //finds "feels_like":
                        words = currentTemp.split(find);
                        for (int i = 0; i < words.length; i++) {
                            feels = words[i];
                        }   
                        //might have to improve to get values with more than 2 numbers---------------------------
                        fLike.setText("feels like "+feels.substring(0,2));
                        //finds current humidity
                        find = marks+"humidity"+marks+":"; //finds "humidity";
                        words = feels.split(find);
                        for (int i = 0; i < words.length; i++) {
                            currentHumidity = words[i];
                        } 
                        //might have to improve to get value 100 if needed---------------------------
                        humidity.setText("humidity: "+currentHumidity.substring(0,2));
                        cityTextField.setText("");
                        System.out.println(weatherLine);
                        if ((weatherLine.equals("Thunderstorm"))==true){
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/thunderstorm.png"))));
                        }else if (((weatherLine.equals("Drizzle")) == true)||((weatherLine.equals("Rain"))==true)){
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/rain.png"))));
                        }else if ((weatherLine.equals("Snow"))==true){
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/snow.png"))));    
                        }else if((weatherLine.equals("Clear"))==true){
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/sun.png"))));    
                        }else if ((weatherLine.equals("Clouds"))==true){
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/clouds.png"))));    
                        }else{
                            picLabel.setIcon(new ImageIcon(getClass().getResource(("images/Drawing.sketchpad.png"))));
                        }
                    }
                  } catch (Exception error ) {
                    errorMessage.setVisible(true);
                 }
            }
        }
        private static String getUrlContents(String theUrl){  
            StringBuilder content = new StringBuilder();  
            try  {  
                URL url = new URL(theUrl); 
                URLConnection urlConnection = url.openConnection(); 
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
                String line;  
                while ((line = bufferedReader.readLine()) != null)  {  
                    content.append(line + "\n");  
                }  
                bufferedReader.close();  
            }  catch(Exception e) {  
                e.printStackTrace();  
            }  
            return content.toString();  
        }  
        }
    public static void main(String [] args){
        new weather_Data ();
    }
}
