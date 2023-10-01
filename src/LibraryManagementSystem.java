import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class LibraryManagementSystem extends Application {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Book> books = new HashMap<>();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kütüphane Yönetim Sistemi");

        showLoginPanel();

        primaryStage.show();
    }

    private void showRegistrationPanel() {
        // Kayıt paneli
        GridPane registrationPane = new GridPane();
        registrationPane.setAlignment(Pos.CENTER);
        registrationPane.setHgap(10);
        registrationPane.setVgap(10);
        registrationPane.setPadding(new Insets(10));

        Label nameLabel = new Label("Ad:");
        TextField nameField = new TextField();
        registrationPane.add(nameLabel, 0, 0);
        registrationPane.add(nameField, 1, 0);

        Label surnameLabel = new Label("Soyad:");
        TextField surnameField = new TextField();
        registrationPane.add(surnameLabel, 0, 1);
        registrationPane.add(surnameField, 1, 1);

        Label usernameLabel = new Label("Kullanıcı Adı:");
        TextField usernameField = new TextField();
        registrationPane.add(usernameLabel, 0, 2);
        registrationPane.add(usernameField, 1, 2);

        Label passwordLabel = new Label("Şifre:");
        PasswordField passwordField = new PasswordField();
        registrationPane.add(passwordLabel, 0, 3);
        registrationPane.add(passwordField, 1, 3);

        Button registerButton = new Button("Kayıt Ol");
        registerButton.setOnAction(event -> {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Tüm alanları doldurun.");
            } else if (getUser(username) != null) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Bu kullanıcı adı zaten alınmış.");
            } else {
                User newUser = new User(username, password, 3);
                addUser(newUser);
                showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Kayıt işlemi tamamlandı. Giriş yapabilirsiniz.");
                showLoginPanel();
            }
        });

        Button backButton = new Button("Geri");
        backButton.setOnAction(event -> showLoginPanel());

        registrationPane.add(registerButton, 1, 4);
        registrationPane.add(backButton, 0, 4);

        Scene registrationScene = new Scene(registrationPane, 400, 250);
        primaryStage.setScene(registrationScene);
    }

    private void showLoginPanel() {
        // Giriş paneli
        GridPane loginPane = new GridPane();
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setHgap(10);
        loginPane.setVgap(10);
        loginPane.setPadding(new Insets(10));

        Label usernameLabel = new Label("Kullanıcı Adı:");
        TextField usernameField = new TextField();
        loginPane.add(usernameLabel, 0, 0);
        loginPane.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Şifre:");
        PasswordField passwordField = new PasswordField();
        loginPane.add(passwordLabel, 0, 1);
        loginPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Giriş Yap");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = getUser(username);
            if (user != null && user.authenticate(password)) {
                primaryStage.setScene(createLibraryScene(primaryStage, user));
            } else {
                showAlert(Alert.AlertType.ERROR, "Giriş Hatası", "Geçersiz kullanıcı adı veya şifre.");
            }
        });

        Button registerButton = new Button("Üye Ol");
        registerButton.setOnAction(event -> showRegistrationPanel());

        loginPane.add(loginButton, 1, 2);
        loginPane.add(registerButton, 1, 3);

        Scene loginScene = new Scene(loginPane, 400, 200);
        primaryStage.setScene(loginScene);
    }

    private User getUser(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void addUser(User user) {
        if (getUser(user.getUsername()) == null) {
            users.put(user.getUsername(), user);
        } else {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Bu kullanıcı adı zaten alınmış.");
        }
    }

    private void addBook(Book book) {
        books.put(book.getTitle(), book);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Scene createLibraryScene(Stage primaryStage, User user) {
        // Kütüphane paneli
        GridPane libraryPane = new GridPane();
        libraryPane.setAlignment(Pos.CENTER);
        libraryPane.setHgap(10);
        libraryPane.setVgap(10);
        libraryPane.setPadding(new Insets(10));

        Label welcomeLabel = new Label("Hoş Geldiniz, " + user.getUsername());
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        libraryPane.add(welcomeLabel, 0, 0, 2, 1);

        Label nameLabel = new Label("Ad:");
        TextField nameField = new TextField();
        libraryPane.add(nameLabel, 0, 1);
        libraryPane.add(nameField, 1, 1);

        Label authorLabel = new Label("Yazar:");
        TextField authorField = new TextField();
        libraryPane.add(authorLabel, 0, 2);
        libraryPane.add(authorField, 1, 2);

        Label categoryLabel = new Label("Kategori:");
        TextField categoryField = new TextField();
        libraryPane.add(categoryLabel, 0, 3);
        libraryPane.add(categoryField, 1, 3);

        Label startsWithLabel = new Label("Baş harf:");
        TextField startsWithField = new TextField();
        libraryPane.add(startsWithLabel, 0, 4);
        libraryPane.add(startsWithField, 1, 4);

        Button searchButton = new Button("Ara");
        searchButton.setOnAction(event -> {
            String name = nameField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            String startsWith = startsWithField.getText();

            searchBooks(name, author, category, startsWith);
        });

        Button borrowButton = new Button("Kitap Al");
        borrowButton.setOnAction(event -> {
            String name = nameField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            String startsWith = startsWithField.getText();

            borrowBook(user, name, author, category, startsWith);
        });

        Button logoutButton = new Button("Çıkış Yap");
        logoutButton.setOnAction(event -> showLoginPanel());

        libraryPane.add(searchButton, 1, 5);
        libraryPane.add(borrowButton, 1, 6);
        libraryPane.add(logoutButton, 0, 5);

        Scene libraryScene = new Scene(libraryPane, 400, 300);
        return libraryScene;
    }

    private void searchBooks(String name, String author, String category, String startsWith) {
        // Kitap arama işlemleri burada yapılır
        // Sonuçları kullanıcıya göstermek için gerekli kodu ekleyebilirsiniz
        // Örnek bir arama işlevi:
        for (Book book : books.values()) {
            if (bookMatchesCriteria(book, name, author, category, startsWith)) {
                // Kitap kriterlere uyan bir kitaptır, sonuçları kullanıcıya gösterin
                System.out.println("Kitap Bulundu: " + book.getTitle());
            }
        }
    }

    private boolean bookMatchesCriteria(Book book, String name, String author, String category, String startsWith) {
        // Kitap, verilen kriterlere uyan bir kitap mı kontrolün
        // Eğer kitap kriterlere uyanıyorsa true, aksi takdirde false döndürün
        // Örneğin, kitap adı veya yazarı verilen değerlere uyuyorsa true dönebilirsiniz
        if (name.isEmpty() && author.isEmpty() && category.isEmpty() && startsWith.isEmpty()) {
            return true; // Tüm kriterler boşsa, her kitap uyuyor gibi kabul edin
        }

        boolean nameMatch = book.getTitle().toLowerCase().contains(name.toLowerCase());
        boolean authorMatch = book.getAuthor().toLowerCase().contains(author.toLowerCase());
        boolean categoryMatch = book.getCategory().toLowerCase().contains(category.toLowerCase());
        boolean startsWithMatch = book.getTitle().toLowerCase().startsWith(startsWith.toLowerCase());

        return nameMatch || authorMatch || categoryMatch || startsWithMatch;
    }

    private void borrowBook(User user, String name, String author, String category, String startsWith) {
        // Kitap alma işlemi burada yapılır
        // Gerekli kontroller ve işlemleri gerçekleştirebilirsiniz
        // Örnek bir kitap alma işlemi:
        for (Book book : books.values()) {
            if (bookMatchesCriteria(book, name, author, category, startsWith) && book.getQuantity() > 0) {
                // Kitap kriterlere uyan ve stokta varsa
                user.addBook(book);
                book.setQuantity(book.getQuantity() - 1);
                System.out.println(user.getUsername() + " adlı kullanıcı " + book.getTitle() + " adlı kitabı aldı.");
                return;
            }
        }
        System.out.println("Kitap alınamadı. Belirtilen kriterlere uygun kitap bulunamadı veya stokta yok.");
    }

    private class User {
        private String username;
        private String password;
        private IntegerProperty maxBooks;
        private Map<String, Book> borrowedBooks;

        public User(String username, String password, int maxBooks) {
            this.username = username;
            this.password = password;
            this.maxBooks = new SimpleIntegerProperty(maxBooks);
            this.borrowedBooks = new HashMap<>();
        }

        public String getUsername() {
            return username;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        public int getMaxBooks() {
            return maxBooks.get();
        }

        public IntegerProperty maxBooksProperty() {
            return maxBooks;
        }

        public Map<String, Book> getBooks() {
            return borrowedBooks;
        }

        public void addBook(Book book) {
            borrowedBooks.put(book.getTitle(), book);
        }

        public boolean removeBook(Book book) {
            return borrowedBooks.remove(book.getTitle()) != null;
        }
    }

    private class Book {
        private StringProperty title;
        private IntegerProperty quantity;
        private StringProperty category;
        private StringProperty author;
        private IntegerProperty year;

        public Book(String title, int quantity, String category, String author, int year) {
            this.title = new SimpleStringProperty(title);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.category = new SimpleStringProperty(category);
            this.author = new SimpleStringProperty(author);
            this.year = new SimpleIntegerProperty(year);
        }

        public String getTitle() {
            return title.get();
        }

        public StringProperty titleProperty() {
            return title;
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public IntegerProperty quantityProperty() {
            return quantity;
        }

        public String getCategory() {
            return category.get();
        }

        public StringProperty categoryProperty() {
            return category;
        }

        public String getAuthor() {
            return author.get();
        }

        public StringProperty authorProperty() {
            return author;
        }

        public int getYear() {
            return year.get();
        }

        public IntegerProperty yearProperty() {
            return year;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
