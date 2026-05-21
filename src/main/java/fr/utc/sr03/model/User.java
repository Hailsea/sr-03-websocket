    package fr.utc.sr03.model;

    import jakarta.persistence.*;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Table(name="user_table")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id_user")
        private int id;

        @Column(name="firstname_user", length = 50, nullable = false)
        private String firstname;

        @Column(name="lastname_user", length = 50, nullable = false)
        private String lastname;

        @Column(name="email_user", length = 50, nullable = false, unique = true)
        private String email;

        @Column(name="password_user", length = 50, nullable = false)
        private String password;

        @Column(name="is_admin", nullable = false)
        private Boolean isAdmin = false;

        @Column(name="is_alive", nullable = false)
        private Boolean isAlive = false;

        @OneToMany(mappedBy = "owner")
        private List<Channel> ownerChannel = new ArrayList<>();

        @ManyToMany
        @JoinTable(
                name = "user_in_channel",
                joinColumns = @JoinColumn(name = "id_user"),
                inverseJoinColumns = @JoinColumn(name = "id_channel")
        )
        private List<Channel> channels;

        public User() {
        }

        public User(String firstname, String lastname, String email, String password, Boolean isAdmin, Boolean isAlive, List<Channel> channels) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
            this.password = password;
            this.isAdmin = isAdmin;
            this.isAlive = isAlive;
            this.channels = channels;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Boolean getAdmin() {
            return isAdmin;
        }

        public void setAdmin(Boolean admin) {
            isAdmin = admin;
        }

        public Boolean getAlive() {
            return isAlive;
        }

        public void setAlive(Boolean alive) {
            isAlive = alive;
        }

        public List<Channel> getChannels() {
            return channels;
        }

        public void setChannels(List<Channel> channels) {
            this.channels = channels;
        }

    }