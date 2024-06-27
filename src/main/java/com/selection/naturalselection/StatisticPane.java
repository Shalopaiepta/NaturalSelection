package com.selection.naturalselection;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class StatisticPane extends VBox {
    private Label titleLabel;
    private Label energyDepletionDeathsLabel;
    private Label predationDeathsLabel;

    public StatisticPane() {
        titleLabel = new Label("Статистика");
        energyDepletionDeathsLabel = new Label("Умерло от недостатка энергии: 0");
        predationDeathsLabel = new Label("Умерло от хищничества: 0");

        this.getChildren().addAll(titleLabel, energyDepletionDeathsLabel, predationDeathsLabel);
    }

    public void updateEnergyDepletionDeaths(int count) {
        energyDepletionDeathsLabel.setText("Умерло от недостатка энергии: " + count);
    }

    public void updatePredationDeaths(int count) {
        predationDeathsLabel.setText("Умерло от хищничества: " + count);
    }
}
