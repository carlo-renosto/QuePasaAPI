package frgp.utn.edu.ar.quepasa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Entidad que representa un comentario hecho a una publicación.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "comments")
public class Comment implements Ownable {
    private UUID id;
    private String content;
    private User author;
    private Timestamp timestamp;
    private boolean active;

    /**
     * Devuelve el ID del comentario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    /**
     * Devuelve el contenido del comentario.
     */
    @Column(nullable=false)
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    /**
     * Devuelve el autor del comentario.
     */
    @ManyToOne
    @JoinColumn(nullable=false, name = "author")
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    @Override
    @Transient
    @JsonIgnore
    public User getOwner() { return author; }
    @Override
    @Transient
    @JsonIgnore
    public void setOwner(User owner) { author = owner; }


    /**
     * Devuelve la hora de publicación.
     */
    @Column(nullable=false)
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    /**
     * Devuelve el estado lógico del registro.
     */
    @Column(nullable=false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

}
