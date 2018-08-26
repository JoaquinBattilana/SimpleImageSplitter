import controllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SimpleImageSplitter");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("views/MainView.fxml"));
        Parent root=loader.load();
        MainViewController controller= loader.getController();
        Scene scene= new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
}
