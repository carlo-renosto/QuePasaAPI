package frgp.utn.edu.ar.quepasa.model;

import jakarta.persistence.*;
import quepasa.api.entities.Activatable;

/**
 * Entidad que representa el subtipo de una publicación.
 */
@Entity
@Table(name = "postSubtypes")
public class PostSubtype implements Activatable {
    private Integer id;
    private PostType type;
    private String description;
    private boolean active = true;

    public PostSubtype() {}

    /**
     * Devuelve el ID único del subtipo de publicación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    /**
     * Devuelve el tipo de publicación asociado al subtipo de publicación.
     */
    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    public PostType getType() { return type; }
    public void setType(PostType type) { this.type = type; }

    /**
     * Devuelve la descripción del subtipo de publicación.
     */
    @Column(nullable = false)
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * Devuelve el estado lógico del subtipo de publicación.
     */
    @Column(nullable = false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
