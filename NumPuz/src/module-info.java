module NumPuz {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	
	opens game to javafx.graphics, javafx.fxml;
}
