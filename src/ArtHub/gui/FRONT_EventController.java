/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArtHub.gui;

import ArtHub.entities.Evenement;
import ArtHub.entities.Post;
import ArtHub.services.EvenementCRUD;
import ArtHub.services.postCRUD;
import ArtHub.services.postCRUD;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.swing.JFileChooser;

import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.filechooser.FileNameExtensionFilter;
//import javafx.scene.control.Tab;

/**
 * FXML Controller class
 *
 * @author Adam Khalfaoui
 */
public class FRONT_EventController implements Initializable {

    @FXML
    private StackPane parentContainer1;

    @FXML
    private AnchorPane anchorRoot1;

    @FXML
    private Button feed_button;

    private JFXTextField p_name;
    private JFXTextArea p_desc;
    private JFXTabPane pan1;

    EvenementCRUD ps;
    public static String s;

    public static int tindex;
    @FXML
    private JFXButton Btn_AddEvent;
    @FXML
    private HBox event_layout;
    @FXML
    private HBox event_mostPop;
    @FXML
    private ScrollPane scroll1;
    @FXML
    private ScrollPane scroll2;
    @FXML
    private Text refresh;
    
    private ResourceBundle b;
    private URL url;
    private ActionEvent event1;
    
    @FXML
    private JFXTextField input;
    @FXML
    private ComboBox<String> comboDate = new ComboBox<>();
    @FXML
    private ComboBox<String> comboTrend = new ComboBox<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        comboDate.getItems().addAll("This Week", "This Month", "Today");
        comboDate.setValue("This Week");
        comboTrend.getItems().addAll("Default","Most Popular", "Most Recent", "Alphabetical");
        comboTrend.setPromptText("Sort By..");
         comboTrend.setValue("Default");
        scroll1.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll1.setVbarPolicy(ScrollBarPolicy.NEVER);
        scroll2.setHbarPolicy(ScrollBarPolicy.NEVER);
        scroll2.setVbarPolicy(ScrollBarPolicy.NEVER);

        EvenementCRUD ps = new EvenementCRUD();
        List<Evenement> myLst;
        if (comboDate.getValue()=="This Week") {
        myLst = ps.ThisWeekEvenement();
        event_layout.getChildren().clear();
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
         
        myLst = ps.consulterEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                    event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       

    }

    @FXML
    private void load_feed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("feed gui.fxml"));
        Scene scene = feed_button.getScene();

        root.translateXProperty().set(scene.getHeight());
        parentContainer1.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
        timeline.getKeyFrames().add(kf);

        timeline.play();
        //parentContainer.getChildren().remove(anchorRoot);

    }

    private void image_file(ActionEvent event) {

        JFileChooser fileChooser = new JFileChooser();

        tindex = pan1.getSelectionModel().getSelectedIndex();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        if (tindex == 0) {

            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "gif", "png");
            fileChooser.addChoosableFileFilter(filter);
        } else if (tindex == 1) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.VIDEO", "mp4", "mov");
            fileChooser.addChoosableFileFilter(filter);
        } else {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.AUDIO", "mp3", "wav");
            fileChooser.addChoosableFileFilter(filter);
        }

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            s = path;

        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No Data");
        }

        tindex = pan1.getSelectionModel().getSelectedIndex();

    }

    void create_image(ActionEvent event) throws Exception {

        String rNom_post = p_name.getText();
        String rdesc = p_desc.getText();
        Post p = new Post(22, rNom_post, rdesc);
        postCRUD prc = new postCRUD();
        //prc.ajouterPost(p);

    }

    @FXML
    private void AddEvent(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ADD-EVENT.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
   }

    @FXML
    private void refresh(MouseEvent event) {
        initialize(url, b);
    }

  

 

   

    @FXML
    private void filterEvent(KeyEvent event) {
       
            
       if (input.getText() == null || input.getText().trim().equals("")) {
           
           SortByTime(event1);
           SortByTrend(event1);
       }
       else {
      EvenementCRUD ps = new EvenementCRUD();
        List<Evenement> myLst;
       event_layout.getChildren().clear();
        event_mostPop.getChildren().clear();
        if (comboTrend.getValue()=="Most Recent") {
        myLst = ps.MostRecentFiltered(input.getText());
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
         if (comboTrend.getValue()=="Most Popular") {
        myLst = ps.MostPopularFiltered(input.getText());
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
          if (comboTrend.getValue()=="Alphabetical") {
        myLst = ps.AlphabeticalFiltered(input.getText());
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
          
           if (comboTrend.getValue()=="Default") {
        myLst = ps.consulterFiltered(input.getText());
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboDate.getValue()=="Today") {
        myLst = ps.TodayEvenementFiltered(input.getText());
        event_layout.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboDate.getValue()=="This Month") {
        myLst = ps.ThisMonthEvenementFiltered(input.getText());
        event_layout.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
        if (comboDate.getValue()=="This Week") {
        myLst = ps.ThisWeekEvenementFiltered(input.getText());
        event_layout.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
       }
    }

    @FXML
    private void SortByTime(ActionEvent event) {
         EvenementCRUD ps = new EvenementCRUD();
        event_layout.getChildren().clear();
        List<Evenement> myLst;
        if (comboDate.getValue()=="Today") {
        myLst = ps.TodayEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboDate.getValue()=="This Month") {
        myLst = ps.ThisMonthEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboDate.getValue()=="This Week") {
        myLst = ps.ThisWeekEvenement();
        event_layout.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_layout.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }

    @FXML
    private void SortByTrend(ActionEvent event) {
        EvenementCRUD ps = new EvenementCRUD();
        event_mostPop.getChildren().clear();
        List<Evenement> myLst;
        if (comboTrend.getValue()=="Default") {
        myLst = ps.consulterEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboTrend.getValue()=="Most Popular") {
        myLst = ps.MostPopularEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        if (comboTrend.getValue()=="Most Recent") {
        myLst = ps.MostRecentEvenement();
        event_mostPop.getChildren().clear();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
        
        if (comboTrend.getValue()=="Alphabetical") {
        myLst = ps.AlphabeticalEvenement();
        try {
            for (int i = 0; i < myLst.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ItemBox.fxml"));
                HBox EventBox = fxmlLoader.load();
                ItemBoxController eventController = fxmlLoader.getController();
                eventController.setData(myLst.get(i));
                event_mostPop.getChildren().add(EventBox);

            }
        } catch (IOException ex) {
            Logger.getLogger(FRONT_EventController.class.getName()).log(Level.SEVERE, null, ex);
        }}
    }
    
    
    

    



}
