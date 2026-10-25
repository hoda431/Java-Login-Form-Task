import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class User {

    private String username;
    private String password;
    private String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getRole() {
        return role;
    }
}

class WelcomePage extends Frame {

    WelcomePage(User user) {
        super("Welcome");
        add(new Label("Hello," + user.getUsername()));
        add(new Label("Role:" + user.getRole()));

        setSize(300, 150);
    }
}

public class LoginFormTask extends Frame {

    private ArrayList<User> users = new ArrayList<>();
    private int attempts = 0;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        Button login = new Button("Login");
        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                System.out.println("All Fields Should be filled");
                return;
            }
            String enteredUsername = username.getText();
            User user = findUser(enteredUsername);

            if (user == null) {
                attempts++;
                System.out.println("User not found!");

                if (attempts == 3) {
                    System.out.println("You have reached the maximum no.of attempts");
                    dispose();
                }
                return;
            }
            if (!user.getPassword().equals(password.getText())
                    ||
                    !user.getRole().equals(role.getSelectedItem())) {

                attempts++;
                System.out.println("Invalid Passowrd or role !");

                if (attempts == 3) {
                    System.out.println("You have reached the maximum no.of attempts");
                    dispose();
                }
                return;
            }
            new WelcomePage(user).setVisible(true);
        });

        cancel.addActionListener(e -> {
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setSize(420, 340);
        setLocationRelativeTo(null);

    }

    private void loadUsers() {
        users.add(new User("Hoda", "1234", "Student"));
        users.add(new User("Alaa", "a208", "TA"));
        users.add(new User("Mariam", "3120", "Professor"));

    }

    private User findUser(String name) {
        for (User user : users) {
            if (name.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);

    }
}
