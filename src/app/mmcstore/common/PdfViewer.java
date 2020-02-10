/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.mmcstore.common;

import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.PropertiesManager;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

/**
 *
 * @author Learn
 */
public class PdfViewer {

    private static SwingController swingController;
    private static JComponent viewerPanel;

    public static void viewPDFFile(String pPath, String stageTitle) {

        Stage primaryStage = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);

        createViewer(borderPane);
        borderPane.setPrefSize(1200, 700);
        createResizeListeners(scene, viewerPanel);
        primaryStage.setOnCloseRequest(we -> SwingUtilities.invokeLater(() -> swingController.dispose()));
        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
        openDocument(pPath);
    }
    
    public static void viewPDFFile(InputStream is, String stageTitle) {

        Stage primaryStage = new Stage();
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);

        createViewer(borderPane);
        borderPane.setPrefSize(1200, 700);
        createResizeListeners(scene, viewerPanel);
        primaryStage.setOnCloseRequest(we -> SwingUtilities.invokeLater(() -> swingController.dispose()));
        primaryStage.setTitle(stageTitle);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
        openDocument(is);
    }

    private static void createResizeListeners(Scene scene, JComponent viewerPanel) {
        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            SwingUtilities.invokeLater(() -> {
                viewerPanel.setSize(new Dimension(newValue.intValue(), (int) scene.getHeight()));
                viewerPanel.setPreferredSize(new Dimension(newValue.intValue(), (int) scene.getHeight()));
                viewerPanel.repaint();
            });
        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            SwingUtilities.invokeLater(() -> {
                viewerPanel.setSize(new Dimension((int) scene.getWidth(), newValue.intValue()));
                viewerPanel.setPreferredSize(new Dimension((int) scene.getWidth(), newValue.intValue()));
                viewerPanel.repaint();
            });
        });
    }

    private static void createViewer(BorderPane borderPane) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // create the viewer ri components.
                swingController = new SwingController();
                swingController.setIsEmbeddedComponent(true);
                PropertiesManager properties = new PropertiesManager(System.getProperties(),
                        ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));

                // read/store the font cache.
                ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
                new FontPropertiesManager(properties, System.getProperties(), messageBundle);
                properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.50");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_OPEN, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_PRINT, "True");
                // hide the status bar
                properties.set(PropertiesManager.PROPERTY_SHOW_STATUSBAR, "false");
                // hide a few toolbars, just to show how the prefered size of the viewer changes.
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "True");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "True");
                properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FORMS, "false");
                properties.set(PropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, "true");

                swingController.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(swingController.getDocumentViewController()));

                SwingViewBuilder factory = new SwingViewBuilder(swingController, properties);

                viewerPanel = factory.buildViewerPanel();
                viewerPanel.revalidate();

                SwingNode swingNode = new SwingNode();
                swingNode.setContent(viewerPanel);
                borderPane.setCenter(swingNode);
                /*
                // the page view menubar
                FlowPane statusBarFlow = new FlowPane();
                buildButton(statusBarFlow, factory.buildPageViewSinglePageNonConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewSinglePageConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewFacingPageNonConToggleButton());
                buildButton(statusBarFlow, factory.buildPageViewFacingPageConToggleButton());
                borderPane.setBottom(statusBarFlow);
                 */

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void openDocument(String document) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingController.openDocument(document);
                viewerPanel.revalidate();
            }
        });
    }
    
    private static void openDocument(InputStream is) {
       SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingController.openDocument(is, null, null);
                viewerPanel.revalidate();
            }
        });
    }

    private static void buildButton(FlowPane flowPane, AbstractButton jButton) {
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jButton);
        flowPane.getChildren().add(swingNode);
    }

    private static void buildJToolBar(FlowPane flowPane, JToolBar jToolBar) {
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(jToolBar);
        flowPane.getChildren().add(swingNode);
    }

    
}
