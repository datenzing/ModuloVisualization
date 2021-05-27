package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.util.Random;


public class Main extends Application {

    final int points1 = 360;
    final int multiples = 360;
    boolean start = true;
    int radius = 280;
    int n = 2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox buttonGrid = new VBox();
        HBox firstRow = new HBox(10);

        Button btn1 = new Button("Start");
        Button btn2 = new Button("Pause");
        Button btn3 = new Button("Jump To");

        Label jumpTo = new Label ("Jump to Parameter Section: ");
        Label points = new Label ("Number of Points: ");
        Label multiplier = new Label("Times Table Number: ");
        TextField pointNum = new TextField();
        TextField multiplierNum = new TextField();

        Slider slider = new Slider(0, 100, 0.5);
        //  Slider slider2 = new Slider(0,5,0.5);
        slider.setShowTickMarks(true);
        //  slider2.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        // slider2.setShowTickLabels(true);


        firstRow.getChildren().addAll(btn1, btn2, slider);
        buttonGrid.getChildren().addAll(firstRow);

        GridPane gridPane = new GridPane();
        gridPane.add(btn3,1,16);
        gridPane.add(jumpTo,1, 13);
        gridPane.add(multiplier,1,14);
        gridPane.add(multiplierNum,2,14);
        gridPane.add(points,1,15);
        gridPane.add(pointNum,2,15);

        gridPane.setVgap(5);
        gridPane.setHgap(5);
        multiplierNum.setPrefColumnCount(5);
        pointNum.setPrefColumnCount(5);

        Pane pane = new Pane();
        pane.setMouseTransparent(true);

        BorderPane borderPane= new BorderPane();
        borderPane.setTop(gridPane);
        borderPane.setCenter(pane);

        Pane pane2 = new Pane(pane, borderPane, buttonGrid);

        btn1.setOnAction(event ->
        {

            AnimationTimer timer = new AnimationTimer(){
                private long update = 0;

                public void handle(long now)
                {

                    btn1.setOnAction(value -> start = true);
                    btn2.setOnAction(value -> start = false);

                    if(start)
                    {
                        double [] x = new double[points1];
                        double [] y = new double[points1];
                        for (int i = 0; i < points1; i++){
                            x[i] = 500 + radius * Math.sin(Math.PI * 2 * i / points1);
                            y[i] = 500 + radius * Math.cos(Math.PI * 2 * i / points1);
                            Circle circle = new Circle (x[i], y[i], 1.0);
                            circle.setFill(Color.BLACK);
                            pane.getChildren().add(circle);
                        }

                        if (now - update + slider.getValue()*10_000_000 >= 1_000_000_000) {
                            pane.getChildren().clear();
                            double r, g, b;
                            Random rand = new Random();
                            r = rand.nextDouble();
                            g = rand.nextDouble();
                            b = rand.nextDouble();
                            Color color = new Color(r, g, b, 1);
                            for (int i = 0; i < multiples; i++) {
                                int j = n * i;
                                while (j > 359) {
                                    j = j - 360;
                                }
                                Line line = new Line();
                                line.setStartX(x[i]);
                                line.setStartY(y[i]);
                                line.setEndX(x[j]);
                                line.setEndY(y[j]);
                                line.setStroke(color);
                                pane.getChildren().add(line);
                            }
                            n = n + 1;
                            update = now;


                        }
                    }

                }
            };
            timer.start();
        });

        btn3.setOnAction(event -> {
            pane.getChildren().clear();
            double centerx = primaryStage.getWidth()/2;
            double centery = primaryStage.getHeight()/2;
            double inputPoints= Double.parseDouble(pointNum.getText());
            double inputMultiples =Double.parseDouble(multiplierNum.getText());
            for (int i = 0; i < inputPoints; i++) {
                Line line=new Line((centerx + radius * Math.cos(Math.PI * 2 * i / inputPoints)),
                        (centery + radius * Math.sin(Math.PI * 2 * i / inputPoints)),
                        (centerx + radius * Math.cos(Math.PI * 2 * inputMultiples * i / inputPoints)),
                        (centery + radius * Math.sin(Math.PI * 2 * inputMultiples * i / inputPoints))
                );
                line.setStroke(Color.rgb(2,100,100));
                pane.getChildren().addAll(line);
            }
            Circle c = new Circle(centerx,centery,radius);
            c.setStroke(Color.BLACK);
            c.setStrokeWidth(2);
            c.setFill(null);
            pane.getChildren().addAll(c);
        });

        Scene scene = new Scene(pane2,900,800);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
