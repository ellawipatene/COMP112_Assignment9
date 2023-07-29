// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2021T1, Assignment 9
 * Name: Ella Wipatene
 * Username: wipateella 
 * ID: 300558005
 */

import ecs100.*;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.awt.Color;

/**
 * AirPollutionAnalyser analyses hourly data about PM2.5 concentration of five
 * cities in China in October, November and December in 2015.
 * Each line of the file "pollution.txt" has information about the pollution
 * level and weather in a city at a particular hour and date.
 * Data is from https://archive.ics.uci.edu/ml/datasets.php.
 *
 * Core: two methods:
 *   loadData
 *      Loads the data from a file into a field containing an ArrayList of
 *      AirPollution objects.
 *      Hint: read all the lines from the file, and then take each line
 *            apart into the values to pass to the AirPollution constructor.
 *   findHazardousLevels
 *      Prints out all the records in the ArrayList that have a
 *      PM2.5 concentration 300 and over.
 *      Hint: see the methods in the AirPollution class, especially getPM and toString
 *
 * Completion: one method:
 *   findContrastingCities
 *      Compares each record in the list with every other record and finds
 *      every pair of cities that having a difference of PM2.5 concentration
 *      larger than 400, at the same hour on the same day.
 *      For each pair, it should print cityA, cityB, hour, date, difference
 *
 * Challenge: two methods
 *   findDailyAverage(String city)
 *      -Prints the average daily PM2.5 value for each day for the given city.
 *      -Finds the longest sequence of days there the average PM2.5 is always
 *       above 200 ("Very unhealthy").
 *      Hint: Use an array where the index corresponds to the day of the year.
 *   plotPollutionLevels
 *      Makes a line plot for the daily average PM2.5 concentration data of
 *      the five cities in the same figure. You may choose different colours
 *      to represent different cities.
 *      Hint: Make the findDailyAverage(String city) method return the array
 *      for a given city.
 */

public class AirPollutionAnalyser {

    private ArrayList<AirPollution> pollutions = new ArrayList<AirPollution>(); 

    /**
     * CORE
     *
     * Load data from the data file into the pollutions field:
     * clear the pollutions field.
     * Read lines from file
     * For each line, use Scanner to break up each line and make an AirPollution
     *  adding it to the pollutions field.
     */
    public void loadData() {
        String filename = "pollution.txt";
        pollutions.clear(); 
        try {
            /*# YOUR CODE HERE */
            List<String> allLines = Files.readAllLines(Path.of(filename));
            for (String line: allLines){
                Scanner sc = new Scanner(line); 
                pollutions.add(new AirPollution(sc.next(), sc.next(), sc.nextInt(), sc.nextDouble(),sc.nextDouble(),sc.nextDouble())); 
            }

            UI.printf("Loaded %d pollution information into list\n", this.pollutions.size());
            UI.println("----------------------------");
        } catch(IOException e){UI.println("File reading failed");}  
    }

    /**
     * CORE
     *
     * Prints out all the records in the ArrayList that have a PM2.5 concentration
     * 300 and over
     */
    public void findHazardousLevels(){
        UI.clearText();
        UI.println("PM2.5 Concentration 300 and above:");
        /*# YOUR CODE HERE */
        for (AirPollution p: pollutions){
            double PM = p.getPM(); 
            if (PM >= 300){
                UI.println(p.toString()); 
            }
        }

        UI.println("------------------------");
    }

    /**
     * COMPLETION
     * 
     * Finds every pair of cities that have at the same hour on the same day 
     * a difference of PM2.5 concentration larger than 400.
     * You need to compare each record in the list with every other record
     * For each pair, it should print
     * cityA, cityB, hour, date, difference
     */
    public void findContrastingCities() {
        UI.clearText();
        UI.println("Pairs of cities whose PM2.5 differs by more than 400 at the same time");
        /*# YOUR CODE HERE */
        
        for (AirPollution p: pollutions){
            int hour = p.getHour(); 
            String date = p.getDate();
            double pm = p.getPM(); 
            
            for (AirPollution p2: pollutions){
                if (p != p2){
                    int hour_two = p2.getHour(); 
                    String date_two = p2.getDate();
                    double pm_two = p2.getPM(); 
                    
                    double diff = Math.abs(pm - pm_two); 
                    if (hour == hour_two && date.equals(date_two) && diff > 400){
                        UI.println(p.getCity() + " and " + p2.getCity() + " at " + hour + " on 2015-" + date + ", diff = " + diff); 
                    }
                }
            }
        }
        UI.println("----------------------------");
    }
    
    public void sortArray(int[][] arr, int colNum){
        Arrays.sort(arr, new Comparator<int[]>(){
            public int compare(int[] val1, int[] val2){
                if(val1[colNum] > val2[colNum]){
                    return 3; 
                }else if (val1[colNum] == val2[colNum]){
                    return 0; 
                } else{
                    return -3;
                }
            }
        }); 
    }
    
    /** Prints the average daily PM2.5 value for each day for the given city, and also
    Finds the longest sequence of days where the average PM2.5 is consistently above 200 ("Very unhealthy").
    Hint: Use an array where the index corresponds to the day of the year. */ 
    public void findDailyAverage(){
        String city_name = UI.askString("What city?");
        
        double av_pm = 0;
        double prev_pm = 0; 
        double next_pm = 0; 
        
        int days_counter = 0; 
        int max_days = 0; 
        
        int start_date = 0; 
        int end_date = 0;
        int max_start_date = 0; 
        int max_end_date = 0; 
        
        int[][] av_pm_and_date = getAverageArray(city_name); 
        
        for (int i = 0; i < av_pm_and_date.length; i++){
            av_pm = av_pm + av_pm_and_date[i][0]; // adds each days average, to calculate the overall average 
            if (i == 0){
                continue; 
            } 
            if (prev_pm > 200 && av_pm_and_date[i][0] <= 200){ // gets the end date
                end_date = av_pm_and_date[i-1][1]; 
                if (days_counter > max_days){ // checks it is the longest amount of time that it has been above 200
                    max_days = days_counter; 
                    max_start_date = start_date; 
                    max_end_date = end_date;
                } 
                days_counter = 0; 
            }
            if (av_pm_and_date[i][0] > 200){
                days_counter++;
                if(prev_pm <= 200){ // gets start date 
                    start_date = av_pm_and_date[i][1]; 
                }
            }
            prev_pm = av_pm_and_date[i][0];
        }

        av_pm = Math.round(av_pm/av_pm_and_date.length); // divide sum by amount of days to get average
        UI.println("Daily average PM concentration of " + city_name + ": " + av_pm); 
        UI.println("The longest amount of days where the average PM2.5 is very unhealthy in " + city_name + " is " + max_days + ", starting from 2015-" + max_start_date + " to 2015-" + max_end_date); 
        
         
    }
    
    public int[][] getAverageArray(String city_name){
        ArrayList<AirPollution> pollutions_city = new ArrayList<AirPollution>(); // makes a new array list with just the values of the certian city in it
        for (int i = 0; i < pollutions.size()-1; i++){ 
            if (pollutions.get(i).getCity().equals(city_name)){
                pollutions_city.add(pollutions.get(i)); 
            }
        }
        
        int[][] pm_and_date = new int[pollutions_city.size()][2]; // makes an array with just the pm and date 
        for (int i = 0; i < pollutions_city.size(); i++){
            int pm = (int)pollutions_city.get(i).getPM(); 
            String date = pollutions_city.get(i).getDate(); 
            String day = date.substring(5,8); 
            pm_and_date[i][0] = pm; 
            pm_and_date[i][1] = Integer.valueOf(day); 
        }
  
        sortArray(pm_and_date, 1); // sorts the array according to the date 
        
        int first_date = pm_and_date[0][1]; // the first day that the records start at
        int last_date = pm_and_date[pollutions_city.size()-1][1]; // the last day that the PM is recorded 
        int array_size = last_date - first_date + 1; // the size of the array, i.e. how many days there are
        
        int av_day = 0; // the avergae for each day
        int[][] av_pm_and_date = new int[array_size][2]; // the array that will hold the average values
        int hour_counter = 0; // counts up to 24
        int day_counter = 0; // amount of days the loop has gone through
        for (int i = 0; i < pollutions_city.size(); i++){
            if (hour_counter < 24){
                av_day = av_day + pm_and_date[i][0]; // sums all of the days values together
                hour_counter++;
                if (hour_counter == 24){
                    av_day = av_day/24; // divides the sum by 24 hours to get the average
                    av_pm_and_date[day_counter][0] = av_day;
                    av_pm_and_date[day_counter][1] = pm_and_date[i][1];
                    hour_counter = 0; 
                    day_counter++; 
                }
            }
        }
        return av_pm_and_date;
    }
    

    /** Makes a line plot for the daily average PM2.5 concentration data of the five cities in the same figure.
    You may choose different colours to represent different cities.
    Hint: Make the findDailyAverage(String city) method return the array for a given city.*/ 
    public void plotPollutionLevels(){
        setUpGraph(); 
        graphData("beijing", Color.red); 
        graphData("chengdu", Color.blue); 
        graphData("shanghai", Color.green); 
        graphData("guangzhou", Color.orange); 
        graphData("shenyang", Color.black); 
    }
    
    /** draw the x-axis, y-axis and labels */ 
    public void setUpGraph(){
        UI.clearGraphics(); 
        UI.setColor(Color.black);
        UI.setLineWidth(4); 
        UI.drawLine(50, 20, 50, 545); 
        UI.drawLine(50, 545, 852, 545); 
        UI.setLineWidth(2); 
        
        int vert_counter = 525; 
        for (int i = 0; i < 8; i++){
            UI.drawString(String.valueOf(i * 100), 10, vert_counter + 23);
            UI.drawLine(42, vert_counter + 20, 50, vert_counter + 20); 
            vert_counter = vert_counter - 75; 
        }
        int day_counter = 274; 
        int horz_counter = 0; 
        for (int i = 0; i < 9; i++){
            UI.drawString(String.valueOf(day_counter), 40 + horz_counter, 570); 
            UI.drawLine(50 + horz_counter, 545, 50 + horz_counter, 550);
            day_counter = day_counter + 10; 
            horz_counter = horz_counter + 100; 
        }
        
        UI.setColor(Color.red); 
        UI.drawLine(50, 345, 852, 345);
        
        UI.drawString("Beijing", 50, 600); 
        UI.setColor(Color.blue);
        UI.drawString("Chengdu", 150, 600); 
        UI.setColor(Color.green);
        UI.drawString("Shanghai", 250, 600); 
        UI.setColor(Color.orange);
        UI.drawString("Guangzhou", 350, 600); 
        UI.setColor(Color.black);
        UI.drawString("Shenyang", 450, 600); 
    }
    
    public void graphData(String city, Color colour){
        UI.setColor(colour); 
        UI.setLineWidth(1); 
        int[][] av_array = getAverageArray(city);
        for (int i = 0; i < av_array.length - 1; i++){
            UI.drawLine(50 + i*8.75, 545 - av_array[i][0], 50 + ((i+1)*8.75), 545 - av_array[i+1][0]); 
        }
    }
    // ------------------ Set up the GUI (buttons) ------------------------
    /** Make buttons to let the user run the methods */
    public void setupGUI() {
        UI.initialise();
        UI.addButton("Load", this::loadData);
        UI.addButton("Hazardous Levels", this::findHazardousLevels);
        UI.addButton("Contrasting Cities", this::findContrastingCities);
        UI.addButton("Daily Average", this::findDailyAverage);
        UI.addButton("Graph Pollution", this::plotPollutionLevels);
        UI.addButton("Quit", UI::quit);
        UI.setDivider(1.0); // text pane only
    }

    public static void main(String[] arguments) {
        AirPollutionAnalyser obj = new AirPollutionAnalyser();
        obj.setupGUI();
    }

}
