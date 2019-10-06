package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import java.util.*;

/**
 * Main Program that generate the application
 */
public class Main extends Application {

    private static HashMap<String, HeapTree> searchList = new HashMap<>();
    private Scene HomePage,DisplayResults, DisplayTop10Search, updateScene;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Display the Top 10 Unique Keyword Search
     * @return the array list of 10 elements
     */
    private static ArrayList<Node> displayTopTenSearch() {
        ArrayList<Node> result = new ArrayList<>();
        ArrayList<HeapTree> temp = new ArrayList<>();
        temp.addAll(searchList.values());
        Collections.sort(temp);
        int count = 0;
        for(HeapTree element: temp) {
            if (count < 10) {
                Node newNode = new Node(element.getSearchName(), element.getTotalSearch());
                result.add(newNode);
                count++;
            }
        }
        return result;
    }

    /**
     * Add the search keyword and results related to its Keyword in a Map if we haven't searched,
     * else increase the total search of the Keyword by 1
     * @param name: Search Name
     */
    private static void search(String name) {
        if (searchList.containsKey(name)) {
            searchList.get(name).increaseTotalSearch();
        } else {
            searchList.put(name, new HeapTree(name));
        }
    }

    /**
     * Update the new score for the link so the link can appear in top 10 Unique Search
     * @param link: the URL
     * @param num: new score
     * @return
     */
    private static String updateNewPageRank(String link, int num) {
        String result = "";
        for (HeapTree element: searchList.values()) {
            ArrayList<Link> list = element.getPageList();
            for(int i = 1; i < list.size(); i++) {
                if (list.get(i).getURL().equals(link)) {
                    element.heapIncreasePageRank(i, num);
                    searchList.put(element.getSearchName(), element);
                    return element.getSearchName();
                }
            }
        }
        if (result.isEmpty()) {
            System.out.println("Could not find the link");
        }
        return result;
    }

    /**
     * A class that store the Search Name and its total search
     */
    public static class Node  {
        private String searchName;
        private int totalSearch;

        public Node(String name, int totalSearch) {
            searchName = name;
            this.totalSearch = totalSearch;
        }

        @Override
        public String toString() {
            return searchName + " - " + totalSearch + " search";
        }
    }

    /**
     * Build the User Interface for the app
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        VBox layout1 = new VBox(10);
        VBox layout2 = new VBox(10);
        VBox layout3 = new VBox(10);
        VBox layout4 = new VBox(10);

        // HomePage
        Label label1 = new Label("Welcome to SERP Simulator!");
        Label label2 = new Label("Display top 10 Search Results");
        Label label3 = new Label("Display top 10 Unique Search");
        Label label4 = new Label("Update Your Own Factor");
        
        
        label1.setTextFill(Color.DARKBLUE);
        label1.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

        label2.setTextFill(Color.DARKBLUE);
        label2.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

        label3.setTextFill(Color.DARKBLUE);
        label3.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

        label4.setTextFill(Color.DARKBLUE);
        label4.setFont(Font.font("Calibri", FontWeight.BOLD, 15));

        TextField textField = new TextField();
        TextField linkName  = new TextField();
        TextField factor1   = new TextField();
        TextField factor2   = new TextField();
        TextField factor3   = new TextField();
        TextField factor4   = new TextField();

        linkName.setPromptText("Enter your FULL link:");
        factor1.setPromptText("Enter your Factor 1:");
        factor2.setPromptText("Enter your Factor 2:");
        factor3.setPromptText("Enter your Factor 3:");
        factor4.setPromptText("Enter your Factor 4:");

        textField.setPromptText("Enter your keyword:");

        Button btn  = new Button();
        Button btn2 = new Button();
        Button btn3 = new Button();
        Button btn4 = new Button();
        Button btn5 = new Button();

        btn.setText("Search");
        btn2.setText("Go Back to HomePage");
        btn3.setText("Display top 10 Unique Searches");
        btn4.setText("Open Update the Page Rank");
        btn5.setText("Update");

        btn2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout1.getChildren().clear();
                layout1.getChildren().addAll(label1, textField, btn, btn3, btn4);
                primaryStage.setScene(HomePage);
            }
        });

        btn4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                layout4.getChildren().clear();
                layout4.getChildren().addAll(btn2, btn3, btn5, factor1, factor2, factor3, factor4, linkName);
                primaryStage.setScene(updateScene);
            }
        });

        btn5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int newTotalFactor = Integer.parseInt(factor1.getText()) + Integer.parseInt(factor2.getText()) + Integer.parseInt(factor3.getText()) + Integer.parseInt(factor4.getText());
                String name = updateNewPageRank(linkName.getText(), newTotalFactor);
                ArrayList<Link> list = searchList.get(name).displayTopTenResult();
                ObservableList<Link> resultView = FXCollections.observableArrayList(list);
                ListView<Link> listView = new ListView<>(resultView);
                listView.getFocusModel().focus(1);
                layout2.getChildren().clear();
                layout2.getChildren().addAll(label2, btn2, btn3, listView);
                primaryStage.setScene(DisplayResults);
            }
        });

        /*
         * Feature 2: Show top 10 Unique Search
         */
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Node> list = displayTopTenSearch();
                ObservableList<Node> resultView = FXCollections.observableArrayList(list);
                ListView<Node> listView = new ListView<>(resultView);
                layout3.getChildren().clear();
                layout3.getChildren().addAll(label3, btn2, listView);
                primaryStage.setScene(DisplayTop10Search);
            }
        });

        /*
         * Feature 1: Search the keyword and display Top 10 Results
         */
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                search(textField.getText());
                ArrayList<Link> list = searchList.get(textField.getText()).displayTopTenResult();
                ObservableList<Link> resultView = FXCollections.observableArrayList(list);
                ListView<Link> listView = new ListView<>(resultView);
                listView.getFocusModel().focus(1);
                layout2.getChildren().clear();
                layout2.getChildren().addAll(label2, btn2, btn3, listView);
                primaryStage.setScene(DisplayResults);
            }
        });

        HomePage            = new Scene(layout1, 500, 500);
        DisplayResults      = new Scene(layout2, 500, 500);
        DisplayTop10Search  = new Scene(layout3, 500, 500);
        updateScene         = new Scene(layout4, 500, 500);

        layout1.getChildren().addAll(label1, textField, btn, btn3, btn4);
        primaryStage.setTitle("Google Search Engine Results Page");
        primaryStage.setScene(HomePage);
        primaryStage.show();
    }
}
