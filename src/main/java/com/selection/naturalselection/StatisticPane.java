package com.selection.naturalselection;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class StatisticPane extends VBox {
    private Label titleLabel;
    private Label energyDepletionDeathsLabel;
    private Label predationDeathsLabel;
    private Label birthsLabel;
    private Label currentAnimalsLabel;

    public StatisticPane() {
        titleLabel = new Label("Статистика");
        energyDepletionDeathsLabel = new Label("Умерло от недостатка энергии: 0");
        predationDeathsLabel = new Label("Умерло от хищничества: 0");
        birthsLabel = new Label("Родилось животных: 0");
        currentAnimalsLabel = new Label("Животных в simulationPane: 0");

        this.getChildren().addAll(titleLabel, energyDepletionDeathsLabel, predationDeathsLabel, birthsLabel, currentAnimalsLabel);

        // Устанавливаем серый фон и отступы
        this.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-spacing: 10;");

        // Добавляем тень для более эстетичного вида
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.4, 0.4));

        this.setEffect(dropShadow);
    }

    public void updateEnergyDepletionDeaths(int count) {
        energyDepletionDeathsLabel.setText("Умерли от голода: " + count);
    }

    public void updatePredationDeaths(int count) {
        predationDeathsLabel.setText("Съедено хищниками: " + count);
    }

    public void updateNewAnimals(int count) {
        birthsLabel.setText("Родилось клеток: " + count);
    }

    public void updateCurrentAnimals(int count) {
        currentAnimalsLabel.setText("Клеток в симуляции: " + count);
    }
}
