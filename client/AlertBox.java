import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

	public static void display(String title, String message){
		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(400);
		window.setMinHeight(300);

		Label label = new Label();
		label.setText(message);

		Button closeButton = new Button("close the window");
		closeButton.setOnAction(e -> window.close());

		GridPane layout = new GridPane();
		layout.setMinSize(400, 300);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label, 0, 0);
		layout.add(closeButton, 0, 1);


		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}

}
