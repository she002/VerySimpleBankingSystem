import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BankingGui extends Application {

	Stage window;
	Scene main_scene, cmain_scene, admin_scene;

	private String cust_id;
	private String password;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		// main menu
		{
			// buttons
			Button main_new = new Button();
			main_new.setMinWidth(150);
			main_new.setText("New Customer");
			main_new.setOnAction(e -> {
				new_customer(main_scene);
				System.out.println("Create new customer");
			});
			Button main_login = new Button();
			main_login.setMinWidth(150);
			main_login.setText("Customer Login");
			main_login.setOnAction(e -> {
				login(main_scene, cmain_scene, admin_scene );
				System.out.println("login");
			});
			Button main_exit = new Button();
			main_exit.setText("Exit");
			main_exit.setMinWidth(150);
			main_exit.setOnAction(e -> closeProgram());

			//Layout

			GridPane main_grid = new GridPane();
			main_grid.setMinSize(800, 600);
			main_grid.setPadding(new Insets(10, 10, 10, 10));
			main_grid.setVgap(8);
			main_grid.setHgap(10);
			main_grid.setAlignment(Pos.CENTER);

			main_grid.add(main_new, 0, 0);
			main_grid.add(main_login, 0, 1);
			main_grid.add(main_exit, 0, 2);

			//Scene
			main_scene = new Scene(main_grid);
		}

		//customer main menu
		{
			// button
			Button cmain_open = new Button();
			cmain_open.setMinWidth(150);
			cmain_open.setText("Open Account");
			cmain_open.setOnAction(e -> {
				open_account(cmain_scene);
				System.out.println("cmain open Account");
			});
			Button cmain_close = new Button();
			cmain_close.setMinWidth(150);
			cmain_close.setText("Close Account");
			cmain_close.setOnAction(e -> {
				close_account(cmain_scene);
				System.out.println("cmain close account");
			});
			Button cmain_deps = new Button();
			cmain_deps.setMinWidth(150);
			cmain_deps.setText("Deposit");
			cmain_deps.setOnAction(e -> {
				deposit(cmain_scene);
				System.out.println("cmain deposit");
			});
			Button cmain_with = new Button();
			cmain_with.setMinWidth(150);
			cmain_with.setText("Withdraw");
			cmain_with.setOnAction(e -> {
				withdraw(cmain_scene);
				System.out.println("cmain withdraw");
			});
			Button cmain_tran = new Button();
			cmain_tran.setMinWidth(150);
			cmain_tran.setText("transfer");
			cmain_tran.setOnAction(e -> {
				transfer(cmain_scene);
				System.out.println("cmain transfer");
			});
			Button cmain_summ = new Button();
			cmain_summ.setMinWidth(150);
			cmain_summ.setText("Account Summary");
			cmain_summ.setOnAction(e -> {
				do_account_summary(cmain_scene);
				System.out.println("cmain account summary");
			});
			Button cmain_exit = new Button();
			cmain_exit.setMinWidth(150);
			cmain_exit.setText("Exit");
			cmain_exit.setOnAction(e -> {
				window.setScene(main_scene);
				System.out.println("cmain exit");
			});

			//Layout
			GridPane cmain_grid = new GridPane();
			cmain_grid.setMinSize(800, 600);
			cmain_grid.setPadding(new Insets(10, 10, 10, 10));
			cmain_grid.setVgap(8);
			cmain_grid.setHgap(10);
			cmain_grid.setAlignment(Pos.CENTER);

			cmain_grid.add(cmain_open, 0, 0);
			cmain_grid.add(cmain_close, 0, 1);
			cmain_grid.add(cmain_deps, 0, 2);
			cmain_grid.add(cmain_with, 0, 3);
			cmain_grid.add(cmain_tran, 0, 4);
			cmain_grid.add(cmain_summ, 0, 5);
			cmain_grid.add(cmain_exit, 0, 6);

			//Scene
			cmain_scene = new Scene(cmain_grid);
		}

		//administrator menu
		{
			// button
			Button admin_acc_summ = new Button();
			admin_acc_summ.setMinWidth(150);
			admin_acc_summ.setText("Check a Account");
			admin_acc_summ.setOnAction(e -> {
				admin_account_summary(admin_scene);
				System.out.println("Check a Account summary");
			});
			Button admin_report_a = new Button();
			admin_report_a.setMinWidth(150);
			admin_report_a.setText("Report A");
			admin_report_a.setOnAction(e -> {
				reportA(admin_scene);
				System.out.println("Show Report A");
			});
			Button admin_report_b = new Button();
			admin_report_b.setMinWidth(150);
			admin_report_b.setText("Report B");
			admin_report_b.setOnAction(e -> {
				reportB(admin_scene);
				System.out.println("Show Report B");
			});
			Button admin_exit = new Button();
			admin_exit.setMinWidth(150);
			admin_exit.setText("Exit");
			admin_exit.setOnAction(e -> {
				window.setScene(main_scene);
				System.out.println("admin exit");
			});

			//Layout
			GridPane admin_grid = new GridPane();
			admin_grid.setMinSize(800, 600);
			admin_grid.setPadding(new Insets(10, 10, 10, 10));
			admin_grid.setVgap(8);
			admin_grid.setHgap(10);
			admin_grid.setAlignment(Pos.CENTER);

			admin_grid.add(admin_acc_summ, 0, 0);
			admin_grid.add(admin_report_a, 0, 1);
			admin_grid.add(admin_report_b, 0, 2);
			admin_grid.add(admin_exit, 0, 3);

			//Scene
			admin_scene = new Scene(admin_grid);


		}

		//Window
		window.setScene(main_scene);
		window.setTitle("Banking System");
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		window.show();
	}

	private void login(Scene lastScene, Scene nextScene, Scene adminScene){

		Label label1 = new Label();
		label1.setText("Customer ID:");
		Label label2 = new Label();
		label2.setText("Password:");

		TextField idInput = new TextField();
		idInput.setPromptText("customer id, must be positive integer");
		TextField psInput = new TextField();
		psInput.setPromptText("password, must be positive integer");

		Button enterButton = new Button("Enter");
		enterButton.setOnAction(e -> {
			checkLogin(idInput, psInput, nextScene, adminScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(lastScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(idInput, 1, 0);
		layout.add(psInput, 1, 1);
		layout.add(enterButton, 1, 2);
		layout.add(closeButton, 0, 2);


		Scene login_scene = new Scene(layout);
		window.setScene(login_scene);
	}

	private void checkLogin(TextField id, TextField ps, Scene nextScene, Scene adminScene){
		String myid = id.getText();
		String myps = ps.getText();
		myid = myid.trim();
		myps = myps.trim();
		if(myid.length() == 0 || myps.length() == 0 ) {
			AlertBox.display("Invalid", "Inputs cannot be empty");
		}
		else if (isOnlyDigits(myid) == false) {
			AlertBox.display("Invalid", "Customer Id must be positive integer");
		}
		else if (isOnlyDigits(myps) == false) {
			AlertBox.display("Invalid", "password must be positive integer");
		}
		else if (myid.equals("0") && myps.equals("0")){
			cust_id = myid;
			password = myps;
			window.setScene(adminScene);
		}
		else {
			boolean success = BankingSystem.customerLogin(myid, myps);
			if (success) {
				cust_id = myid;
				password = myps;
				window.setScene(nextScene);
			}
			else
			{
				AlertBox.display("Invalid", "Wrong ID or Pin" );
			}
		}

	}
	private void new_customer(Scene prevScene){

		Label label1 = new Label();
		label1.setText("Name:");
		Label label2 = new Label();
		label2.setText("Gender:");
		Label label3 = new Label();
		label3.setText("Age:");
		Label label4 = new Label();
		label4.setText("Pin:");

		TextField input1 = new TextField();
		input1.setPromptText("name, less than 15 charaters");
		TextField input2 = new TextField();
		input2.setPromptText("gender, must be M or F");
		TextField input3 = new TextField();
		input3.setPromptText("age, must be integer");
		TextField input4 = new TextField();
		input4.setPromptText("pin, must be integer");

		Button createButton = new Button("Create New Customer");
		createButton.setOnAction(e -> {
			create_customer(input1, input2, input3, input4, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(label3, 0, 2);
		layout.add(label4, 0, 3);
		layout.add(closeButton, 0, 4);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(input3, 1, 2);
		layout.add(input4, 1, 3);
		layout.add(createButton, 1, 4);


		Scene new_customer_scene = new Scene(layout);
		window.setScene(new_customer_scene);
	}
	private void create_customer(TextField in1, TextField in2, TextField in3, TextField in4, Scene prevScene){
		String str1 = in1.getText();
		String str2 = in2.getText();
		String str3 = in3.getText();
		String str4 = in4.getText();
		str1 = str1.trim();
		str2 = str2.trim();
		str3 = str3.trim();
		str4 = str4.trim();
		if(str1.length() == 0 || str2.length() == 0 || str3.length() == 0 || str4.length() == 0) {
			AlertBox.display("Invalid", "Inputs cannot be empty");
		}
		else if (in1.getText().length() > 15) {
			AlertBox.display("Invalid", "Name must be less than 15 characters");
		}
		else if (!in2.getText().equals("M") && !in2.getText().equals("F")) {
			AlertBox.display("Invalid", "Gender must be M or F");
		}
		else if (isOnlyDigits(str3) == false) {
			AlertBox.display("Invalid", "Invalid age, must be integer > 0");
		}
		else if (isOnlyDigits(str4) == false){
			AlertBox.display("Invalid", "Invalid pin, must be integer > 0");
		}
		else
		{
			boolean success = BankingSystem.newCustomer(str1, str2, str3, str4);
			if (success)
			{
				AlertBox.display("SUCCESS", "Create Custommer - Succeed!");
				window.setScene(prevScene);
			}
			else
			{
				AlertBox.display("FAIL", "Create Custommer - Failed");
			}
		}

	}
	private void open_account(Scene prevScene){
		
		Label label1 = new Label();
		label1.setText("Balance:");
		Label label2 = new Label();
		label2.setText("Type:");

		TextField input1 = new TextField();
		input1.setPromptText("balance, must be positive integer");
		TextField input2 = new TextField();
		input2.setPromptText("gender, must be C or S");

		Button createButton = new Button("Open New Account");
		createButton.setOnAction(e -> {
			do_open_account(input1, input2, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(closeButton, 0, 2);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(createButton, 1, 2);


		Scene open_acc_scene = new Scene(layout);
		window.setScene(open_acc_scene);

	}
	private void do_open_account(TextField in1, TextField in2, Scene prevScene) {
		String str1 = in1.getText();
		String str2 = in2.getText();
		str1 = str1.trim();
		str2 = str2.trim();
		if(str1.length() == 0 || str2.length() == 0 ) {
			AlertBox.display("Invalid", "Inputs cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Balance must be positive integer");
		}
		else if (!in2.getText().equals("C") && !in2.getText().equals("S")) {
			AlertBox.display("Invalid", "Type must be C or S");
		}
		else
		{
			if (cust_id.length() == 0) {
				AlertBox.display("FAIL", "Open New Account - Failed");
			}
			else {
				boolean success = BankingSystem.openAccount(cust_id, str2, str1);
				if (success)
				{
					AlertBox.display("SUCCESS", "Open New Account - Succeed!");
					window.setScene(prevScene);
				}
			
				else
				{
					AlertBox.display("FAIL", "Open New Account - Failed");
				}
			}
		}
	}
	private void close_account(Scene prevScene){
		Label label1 = new Label();
		label1.setText("Account ID:");

		TextField input1 = new TextField();
		input1.setPromptText("ID, must be integer > 1000");

		Button createButton = new Button("Close Account");
		createButton.setOnAction(e -> {
			do_close_account(input1, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(closeButton, 0, 1);

		layout.add(input1, 1, 0);
		layout.add(createButton, 1, 1);


		Scene close_acc_scene = new Scene(layout);
		window.setScene(close_acc_scene);
	}
	private void do_close_account(TextField in1, Scene prevScene) {
		String str1 = in1.getText();
		str1 = str1.trim();
		if(str1.length() == 0) {
			AlertBox.display("Invalid", "Inputs cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Account Number must be positive integer");
		}
		else
		{
			boolean verified = BankingSystem.verifyAccount(cust_id, str1);
			if (verified)
			{
				boolean success = BankingSystem.closeAccount(str1);
				if (success)
				{
					AlertBox.display("SUCCESS", "Close Account- Succeed!");
					window.setScene(prevScene);
				}
				else
				{
					AlertBox.display("FAIL", "Close Account - Failed");
				}
			}
			else
			{
				AlertBox.display("FAIL", "Invalid Account Number");
			}
		}
	}
	private void deposit(Scene prevScene) {
		Label label1 = new Label();
		label1.setText("Account Num:");
		Label label2 = new Label();
		label2.setText("How much:");

		TextField input1 = new TextField();
		input1.setPromptText("must be positive integer");
		TextField input2 = new TextField();
		input2.setPromptText("must be positive integer");

		Button createButton = new Button("deposit");
		createButton.setOnAction(e -> {
			do_deposit(input1, input2, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(closeButton, 0, 2);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(createButton, 1, 2);


		Scene deposit_scene = new Scene(layout);
		window.setScene(deposit_scene);
	}
	private void do_deposit(TextField in1, TextField in2, Scene prevScene) {
		String str1 = in1.getText();
		str1 = str1.trim();
		String str2 = in2.getText();
		str2 = str2.trim();
		if(str1.length() == 0 || str2.length() == 0) {
			AlertBox.display("Invalid", "Input cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else if (isOnlyDigits(str2) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else
		{
			boolean verified = BankingSystem.verifyAccount(cust_id, str1);
			if (verified)
			{
				boolean success = BankingSystem.deposit(str1, str2);
				if (success)
				{
					AlertBox.display("SUCCESS", "Deposit - Succeed!");
					window.setScene(prevScene);
				}
				else
				{
					AlertBox.display("FAIL", "Deposit - Failed");
				}
			}
			else
			{
				AlertBox.display("FAIL", "Invalid Account Number");
			}
		}
	}
	private void withdraw(Scene prevScene) {
		Label label1 = new Label();
		label1.setText("Account Number");
		Label label2 = new Label();
		label2.setText("How much:");

		TextField input1 = new TextField();
		input1.setPromptText("must be positive integer");
		TextField input2 = new TextField();
		input2.setPromptText("must be positive integer");

		Button createButton = new Button("Withdraw");
		createButton.setOnAction(e -> {
			do_withdraw(input1, input2, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(closeButton, 0, 2);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(createButton, 1, 2);


		Scene withdraw_scene = new Scene(layout);
		window.setScene(withdraw_scene);
	}
	private void do_withdraw(TextField in1, TextField in2, Scene prevScene){
		String str1 = in1.getText();
		str1 = str1.trim();
		String str2 = in2.getText();
		str2 = str2.trim();
		if(str1.length() == 0 || str1.length() == 0) {
			AlertBox.display("Invalid", "Input cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else if (isOnlyDigits(str2) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else
		{
			boolean verified = BankingSystem.verifyAccount(cust_id, str1);
			if (verified)
			{
				boolean success = BankingSystem.withdraw(str1, str2);
				if (success)
				{
					AlertBox.display("SUCCESS", "Withdraw - Succeed!");
					window.setScene(prevScene);
				}
				else
				{
					AlertBox.display("FAILED", "Withdraw - Failed");
				}
			}
			else
			{
				AlertBox.display("FAIL", "Invalid Account Number");
			}
		}
	}
	private void transfer(Scene prevScene) {
		Label label1 = new Label();
		label1.setText("Account Transfer From:");
		Label label2 = new Label();
		label2.setText("Account Transfer To:");
		Label label3 = new Label();
		label3.setText("How much:");

		TextField input1 = new TextField();
		input1.setPromptText("Account ID, must be positive integer");
		TextField input2 = new TextField();
		input2.setPromptText("Account ID, must be positive integer");
		TextField input3 = new TextField();
		input3.setPromptText("Amount, must be positive integer");

		Button createButton = new Button("Transfer");
		createButton.setOnAction(e -> {
			do_transfer(input1, input2, input3, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(label3, 0, 2);
		layout.add(closeButton, 0, 3);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(input3, 1, 2);
		layout.add(createButton, 1, 3);


		Scene transfer_scene = new Scene(layout);
		window.setScene(transfer_scene);
	}
	private void do_transfer(TextField in1, TextField in2, TextField in3, Scene prevScene){
		String str1 = in1.getText();
		String str2 = in2.getText();
		String str3 = in3.getText();
		str1 = str1.trim();
		str2 = str2.trim();
		str3 = str3.trim();
		if(str1.length() == 0 || str2.length() == 0 || str3.length() == 0) {
			AlertBox.display("Invalid", "Input cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else if (isOnlyDigits(str2) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else if (isOnlyDigits(str3) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else
		{
			boolean verified = BankingSystem.verifyAccount(cust_id, str1);
			if (verified)
			{
				boolean success = BankingSystem.transfer(str1, str2, str3);
				if (success)
				{
					AlertBox.display("SUCCESS", "Transfer - Succeed!");
					window.setScene(prevScene);
				}
				else
				{
					AlertBox.display("FAILED", "Transfer - Failed");
				}
			}
			else
			{
				AlertBox.display("FAIL", "Invalid Account Number");
			}
		}
	}
	private void report(String message, Scene lastScene){

		Label label = new Label();
		label.setText(message);

		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(lastScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label, 0, 0);
		layout.add(closeButton, 0, 1);


		Scene report_scene = new Scene(layout);
		window.setScene(report_scene);
	}
	
	private void admin_account_summary(Scene prevScene){
		Label label1 = new Label();
		label1.setText("Customer ID to Check:");

		TextField input1 = new TextField();
		input1.setPromptText("Customer ID, must be positive integer");

		Button createButton = new Button("Check");
		createButton.setOnAction(e -> {
			do_admin_account_summary(input1, prevScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(prevScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(closeButton, 0, 1);

		layout.add(input1, 1, 0);
		layout.add(createButton, 1, 1);


		Scene acc_summ_scene = new Scene(layout);
		window.setScene(acc_summ_scene);
	}
	private void do_admin_account_summary (TextField in1, Scene prevScene) {
		String str1 = in1.getText();
		str1 = str1.trim();
		if(str1.length() == 0) {
			AlertBox.display("Invalid", "Input cannot be empty");
		}
		else if (isOnlyDigits(str1) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else
		{
			String report_msg = BankingSystem.accountSummary(str1);
			if (report_msg.length() > 0)
			{
				report(report_msg, prevScene);
			}
			else
			{
				AlertBox.display("FAILED", "Account Summary - Failed");
			}
		}
	}
	private void do_account_summary (Scene prevScene) {
		String report_msg = BankingSystem.accountSummary(cust_id);
		if (report_msg.length() > 0)
		{
			report(report_msg, prevScene);
		}
		else
		{
			AlertBox.display("FAILED", "Account Summary - Failed");
		}
	}

	private void reportA(Scene lastScene){

		Label label = new Label();
		String msg = BankingSystem.reportA();
		label.setText(msg);

		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(lastScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label, 0, 0);
		layout.add(closeButton, 0, 1);


		Scene report_A_scene = new Scene(layout);
		window.setScene(report_A_scene);
	}
	private void reportB( Scene lastScene){

		Label label1 = new Label();
		label1.setText("Min Age:");
		Label label2 = new Label();
		label2.setText("Max Age:");

		TextField input1 = new TextField();
		input1.setPromptText("must be positive integer");
		TextField input2 = new TextField();
		input2.setPromptText("must be positive integer");

		Button startButton = new Button("View");
		startButton.setOnAction(e -> {
			do_reportB(input1, input2,lastScene);
		});
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(e -> window.setScene(lastScene));

		GridPane layout = new GridPane();
		layout.setMinSize(800, 600);
		layout.setPadding(new Insets(10, 10, 10, 10));
		layout.setVgap(8);
		layout.setHgap(10);
		layout.setAlignment(Pos.CENTER);
		layout.add(label1, 0, 0);
		layout.add(label2, 0, 1);
		layout.add(closeButton, 0, 2);

		layout.add(input1, 1, 0);
		layout.add(input2, 1, 1);
		layout.add(startButton, 1, 2);

		Scene report_B_scene = new Scene(layout);
		window.setScene(report_B_scene);
	}

	private void do_reportB(TextField in1, TextField in2, Scene prevScene) {
		String min = in1.getText();
		String max = in2.getText();
		min = min.trim();
		max = max.trim();
		if(min.length() == 0 || max.length() == 0) {
			AlertBox.display("Invalid", "Input cannot be empty");
		}
		else if (isOnlyDigits(min) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else if (isOnlyDigits(max) == false) {
			AlertBox.display("Invalid", "Input must be positive integer");
		}
		else
		{
		
			String msg = BankingSystem.reportB(min, max);
			if (msg.length() > 0)
			{
				report(msg, prevScene);
			}
			else
			{
				AlertBox.display("FAILED", "Report B - Failed");
			}
		}

	}


	private void closeProgram() {
		window.close();
	}

	private boolean isOnlyDigits(String number)
	{
		for(int i = 0; i < number.length(); i++)
		{
			if(number.charAt(i) < '0'|| number.charAt(i) > '9')
			{
				return false;
			}
		}
		return true;
	}


}
