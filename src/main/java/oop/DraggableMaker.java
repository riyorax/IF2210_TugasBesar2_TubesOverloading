package oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class DraggableMaker {
    private ImageView lastGlowingCell = null;
    private List<Pane> glowingCells = new ArrayList<>();
    private Timeline timer;

    private void setGlow(Node node, boolean glow) {
        if (glow) {
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.YELLOW); // Glow color
            dropShadow.setRadius(20);
            dropShadow.setSpread(0.5);
            dropShadow.setBlurType(javafx.scene.effect.BlurType.GAUSSIAN);
            node.setEffect(dropShadow);
        } else {
            node.setEffect(null);
        }
    }

    public void makeDraggable(Node node, ImageView[][] grid) {
        final Delta dragDelta = new Delta();
        final Delta initialPosition = new Delta();
        initialPosition.x = node.getLayoutX();
        initialPosition.y = node.getLayoutY();
    
        node.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = node.getTranslateX() - mouseEvent.getSceneX();
            dragDelta.y = node.getTranslateY() - mouseEvent.getSceneY();
        });
    
        node.setOnMouseDragged(mouseEvent -> {
            int col = ((int)(mouseEvent.getSceneX() - 34.4) / 100) + 1;
            int row = ((int)(mouseEvent.getSceneY() - 70) / 110) + 1;

            if (col > 0 && col <= grid[0].length && row > 0 && row <= grid.length) {
                ImageView currentCell = grid[row - 1][col - 1];

                // Only change the glow if the cell has changed
                if (lastGlowingCell != currentCell) {
                    if (lastGlowingCell != null) {
                        setGlow(lastGlowingCell, false);
                    }
                    setGlow(currentCell, true);
                    lastGlowingCell = currentCell;
                }
            } else {
                if (lastGlowingCell != null) {
                    // previousRow = (int) ((lastGlowingCell.getLayoutY() - 70) / 110) + 1;
                    // previousCol = (int) ((lastGlowingCell.getLayoutX() - 34.4) / 100) + 1;

                    // if (previousRow >= 0 && previousRow < redMatrix.length && previousCol >= 0 && previousCol < redMatrix[0].length) {
                    //     if (redMatrix[previousRow][previousCol]) {
                    //         System.out.println("masukk2");
                    //         setRedGlow(grid[previousRow][previousCol], true);
                    //     }
                    // }

                    setGlow(lastGlowingCell, false);
                    lastGlowingCell = null;
                }
            }

            node.setTranslateX(mouseEvent.getSceneX() + dragDelta.x);
            node.setTranslateY(mouseEvent.getSceneY() + dragDelta.y);
        });

        node.setOnMouseReleased(mouseEvent -> {
            if (lastGlowingCell != null) {
                setGlow(lastGlowingCell, false);
                lastGlowingCell = null;
            }
            node.setTranslateX(0);
            node.setTranslateY(0);
        });
    }

    class Delta {
        double x, y;
    }

    public void setRedGlow(Node node, boolean glow) {
        if (glow) {
            // DropShadow dropShadow = new DropShadow();
            // dropShadow.setColor(Color.RED); // Glow color
            // dropShadow.setRadius(20);
            // dropShadow.setSpread(0.5);
            // dropShadow.setBlurType(javafx.scene.effect.BlurType.GAUSSIAN);
            // node.setEffect(dropShadow);
            node.setStyle("-fx-border-color: red; -fx-border-width: 5;");
        } else {
            node.setStyle(null);
            // node.setEffect(null);
        }
    }

    public void setRedGlowOnRandomGroup(Pane[][] grid, int rows, int cols) {
        if (timer != null) {
            timer.stop();
            for (Pane cell : glowingCells) {
                setRedGlow(cell, false);
            }
            glowingCells.clear();
        }

        Random random = new Random();

        int gridRows = grid.length;
        int gridCols = grid[0].length;

        // Randomly choose a starting position for the 2x3 area
        int startRow = random.nextInt(gridRows - rows + 1);
        int startCol = random.nextInt(gridCols - cols + 1);

        // Add the cards in the chosen 2x3 area to the list
        for (int i = startRow; i < startRow + rows; i++) {
            for (int j = startCol; j < startCol + cols; j++) {
                Pane cell = grid[i][j];
                glowingCells.add(cell);
                setRedGlow(cell, true);
            }
        }

        timer = new Timeline(new KeyFrame(Duration.seconds(30), event -> {
            for (Pane cell : glowingCells) {
                setRedGlow(cell, false);
            }
            glowingCells.clear();
        }));
        timer.setCycleCount(1);
        timer.play();
    }
}