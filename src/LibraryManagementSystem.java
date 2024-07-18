import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LibraryManagementSystem extends JFrame {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Book> books = new HashMap<>();

    public LibraryManagementSystem() {
        setTitle("Kütüphane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        showLoginPanel();
    }

    private void showLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton loginButton = new JButton("Giriş Yap");
        JButton registerButton = new JButton("Üye Ol");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        loginPanel.add(registerButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = getUser(username);
                if (user != null && user.authenticate(password)) {
                    showLibraryPanel(user);
                } else {
                    showMessage("Giriş Hatası", "Geçersiz kullanıcı adı veya şifre.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegistrationPanel();
            }
        });

        setContentPane(loginPanel);
        revalidate();
    }

    private void showRegistrationPanel() {
        JPanel registrationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField(15);
        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = new JTextField(15);
        JLabel usernameLabel = new JLabel("Kullanıcı Adı:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField(15);

        JButton registerButton = new JButton("Kayıt Ol");
        JButton backButton = new JButton("Geri");

        gbc.gridx = 0;
        gbc.gridy = 0;
        registrationPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        registrationPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registrationPanel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        registrationPanel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        registrationPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        registrationPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        registrationPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        registrationPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        registrationPanel.add(registerButton, gbc);

        gbc.gridx = 0;
        registrationPanel.add(backButton, gbc);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    showMessage("Uyarı", "Tüm alanları doldurun.");
                } else if (getUser(username) != null) {
                    showMessage("Uyarı", "Bu kullanıcı adı zaten alınmış.");
                } else {
                    User newUser = new User(username, password, 3);
                    addUser(newUser);
                    showMessage("Başarılı", "Kayıt işlemi tamamlandı. Giriş yapabilirsiniz.");
                    showLoginPanel();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel();
            }
        });

        setContentPane(registrationPanel);
        revalidate();
    }

    private void showLibraryPanel(User user) {
        JPanel libraryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel welcomeLabel = new JLabel("Hoş Geldiniz, " + user.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField(15);
        JLabel authorLabel = new JLabel("Yazar:");
        JTextField authorField = new JTextField(15);
        JLabel categoryLabel = new JLabel("Kategori:");
        JTextField categoryField = new JTextField(15);
        JLabel startsWithLabel = new JLabel("Baş harf:");
        JTextField startsWithField = new JTextField(15);

        JButton searchButton = new JButton("Ara");
        JButton borrowButton = new JButton("Kitap Al");
        JButton logoutButton = new JButton("Çıkış Yap");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        libraryPanel.add(welcomeLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        libraryPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        libraryPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        libraryPanel.add(authorLabel, gbc);

        gbc.gridx = 1;
        libraryPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        libraryPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        libraryPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        libraryPanel.add(startsWithLabel, gbc);

        gbc.gridx = 1;
        libraryPanel.add(startsWithField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        libraryPanel.add(searchButton, gbc);

        gbc.gridy = 6;
        libraryPanel.add(borrowButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        libraryPanel.add(logoutButton, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String author = authorField.getText();
                String category = categoryField.getText();
                String startsWith = startsWithField.getText();

                searchBooks(name, author, category, startsWith);
            }
        });

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String author = authorField.getText();
                String category = categoryField.getText();
                String startsWith = startsWithField.getText();

                borrowBook(user, name, author, category, startsWith);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPanel();
            }
        });

        setContentPane(libraryPanel);
        revalidate();
    }

    private User getUser(String username) {
        return users.get(username);
    }

    private void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    private void addBook(Book book) {
        books.put(book.getTitle(), book);
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void searchBooks(String name, String author, String category, String startsWith) {
        for (Book book : books.values()) {
            if (bookMatchesCriteria(book, name, author, category, startsWith)) {
                System.out.println("Kitap Bulundu: " + book.getTitle());
            }
        }
    }

    private boolean bookMatchesCriteria(Book book, String name, String author, String category, String startsWith) {
        if (name.isEmpty() && author.isEmpty() && category.isEmpty() && startsWith.isEmpty()) {
            return true;
        }

        boolean nameMatch = book.getTitle().toLowerCase().contains(name.toLowerCase());
        boolean authorMatch = book.getAuthor().toLowerCase().contains(author.toLowerCase());
        boolean categoryMatch = book.getCategory().toLowerCase().contains(category.toLowerCase());
        boolean startsWithMatch = book.getTitle().toLowerCase().startsWith(startsWith.toLowerCase());

        return nameMatch || authorMatch || categoryMatch || startsWithMatch;
    }

    private void borrowBook(User user, String name, String author, String category, String startsWith) {
        for (Book book : books.values()) {
            if (bookMatchesCriteria(book, name, author, category, startsWith) && book.getQuantity() > 0) {
                user.addBook(book);
                book.setQuantity(book.getQuantity() - 1);
                System.out.println(user.getUsername() + " adlı kullanıcı " + book.getTitle() + " adlı kitabı aldı.");
                return;
            }
        }
        System.out.println("Kitap alınamadı. Belirtilen kriterlere uygun kitap bulunamadı veya stokta yok.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LibraryManagementSystem().setVisible(true);
            }
        });
    }

    private class User {
        private String username;
        private String password;
        private int maxBooks;
        private Map<String, Book> borrowedBooks;

        public User(String username, String password, int maxBooks) {
            this.username = username;
            this.password = password;
            this.maxBooks = maxBooks;
            this.borrowedBooks = new HashMap<>();
        }

        public String getUsername() {
            return username;
        }

        public boolean authenticate(String password) {
            return this.password.equals(password);
        }

        public int getMaxBooks() {
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
        private String title;
        private int quantity;
        private String category;
        private String author;
        private int year;

        public Book(String title, int quantity, String category, String author, int year) {
            this.title = title;
            this.quantity = quantity;
            this.category = category;
            this.author = author;
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCategory() {
            return category;
        }

        public String getAuthor() {
            return author;
        }

        public int getYear() {
            return year;
        }
    }
}
