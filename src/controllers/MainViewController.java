package controllers;

import Popups.ExceptionPopup;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private ObservableList<File> files;
    @FXML
    private CheckBox VerticalCheckBox;
    @FXML
    private CheckBox HorizontalCheckBox;
    @FXML
    private Button SplitButton;
    @FXML
    private Button SelectButton;
    @FXML
    private TextArea VerticalTextArea;
    @FXML
    private TextArea HorizontalTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        files=FXCollections.emptyObservableList();
        VerticalTextArea.setDisable(true);
        HorizontalTextArea.setDisable(true);
        SplitButton.disableProperty().bind(Bindings.size(files).greaterThan(0) );
    }

    @FXML
    protected void VerticalCheckBoxAction(){
        if(VerticalTextArea.isDisable())
            VerticalTextArea.setDisable(false);
        else
            VerticalTextArea.setDisable(true);
    }
    @FXML
    protected void HorizontalCheckBoxAction(){
        if(HorizontalTextArea.isDisable())
            HorizontalTextArea.setDisable(false);
        else
            HorizontalTextArea.setDisable(true);
    }
    @FXML
    protected void SplitButtonAction(){
        try{
            DirectoryChooser dChooser = new DirectoryChooser();
            Stage stage = new Stage();
            File auxFile = dChooser.showDialog(stage);
            String directory = auxFile.getAbsolutePath();
            for(File file:files) {
                BufferedImage source = ImageIO.read(file);
                int verticalExcess = source.getWidth()%Integer.parseInt(VerticalTextArea.getText());
                int horizontalExcess = source.getHeight()%Integer.parseInt(HorizontalTextArea.getText());
                int parseSizeVertical = source.getWidth()/Integer.parseInt(VerticalTextArea.getText());
                int parseSizeHorizontal = source.getHeight()/Integer.parseInt(HorizontalTextArea.getText());
                int cols = Integer.parseInt(VerticalTextArea.getText());
                int rows = Integer.parseInt(HorizontalTextArea.getText());
                int slices = cols + rows;
                for (int i = 0; i < cols;i++) {
                    for(int j= 0; j < rows;j++){
                        File aux = new File(directory+"/"+file.getName().split("\\.")[0]+"_"+slices+".png");
                        if(horizontalExcess==0 && verticalExcess==0)
                            ImageIO.write(source.getSubimage(i * parseSizeVertical, j * parseSizeHorizontal, parseSizeVertical, parseSizeHorizontal), "png", aux);
                        else if (i==0 || j==0)
                            ImageIO.write(source.getSubimage(i * parseSizeVertical, j * parseSizeHorizontal, parseSizeVertical + verticalExcess, parseSizeHorizontal + horizontalExcess), "png", aux);
                        else
                            ImageIO.write(source.getSubimage(i * parseSizeVertical + verticalExcess, j * parseSizeHorizontal + horizontalExcess , parseSizeVertical, parseSizeHorizontal), "png", aux);
                        slices--;
                    }
                }
            }
        }
        catch(Exception e){
        }

    }
    @FXML
    protected void SelectButtonAction(){
        try {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image extensions", "*.png","*.jpg","*.jpeg")
        );
        Stage stage = new Stage();
        this.files = FXCollections.observableList(fileChooser.showOpenMultipleDialog(stage));
    }
    catch(Exception e){
        ExceptionPopup alert = new ExceptionPopup(e);
        alert.showAndWait();
    }
    }
    public void initModel(ObservableList<File> files){
        this.files=files;
    }
}