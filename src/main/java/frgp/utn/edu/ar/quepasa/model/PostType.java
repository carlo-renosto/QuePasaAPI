package frgp.utn.edu.ar.quepasa.model;

import jakarta.persistence.*;
import quepasa.api.entities.Activatable;

/**
 * Entidad que representa el tipo de una publicación.
 */
@Entity
@Table(name = "postTypes")
public class PostType implements Activatable {
    private Integer id;
    private String description;
    private boolean active = true;

    public PostType() {}

    /**
     * Devuelve el ID único del tipo de publicación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    /**
     * Devuelve la descripción del tipo de publicación.
     */
    @Column(nullable = false)
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * Devuelve el estado lógico del tipo de publicación.
     */
    @Column(nullable = false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
