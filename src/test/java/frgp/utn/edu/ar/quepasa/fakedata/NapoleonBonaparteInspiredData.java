package frgp.utn.edu.ar.quepasa.fakedata;

import frgp.utn.edu.ar.quepasa.model.Event;
import frgp.utn.edu.ar.quepasa.model.Post;
import frgp.utn.edu.ar.quepasa.model.User;
import frgp.utn.edu.ar.quepasa.model.enums.EventCategory;
import frgp.utn.edu.ar.quepasa.model.enums.Role;
import frgp.utn.edu.ar.quepasa.model.enums.SubnationalDivisionDenomination;
import frgp.utn.edu.ar.quepasa.model.geo.City;
import frgp.utn.edu.ar.quepasa.model.geo.Country;
import frgp.utn.edu.ar.quepasa.model.geo.Neighbourhood;
import frgp.utn.edu.ar.quepasa.model.geo.SubnationalDivision;
import frgp.utn.edu.ar.quepasa.model.media.Picture;
import org.springframework.http.MediaType;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static frgp.utn.edu.ar.quepasa.model.enums.Audience.PUBLIC;

public class NapoleonBonaparteInspiredData {

    private final User napoleon = napoleonBonaparte();
    private final User mariaLuisa = mariaLuisaDeAustria();
    private final Picture n1 = napoleonCruzandoLosAlpes();
    private final Picture n2 = autorretrato();
    private final Picture n3 = autorretratoDeOtraPersona();
    private final UUID random1 = UUID.randomUUID();
    private final UUID random2 = UUID.randomUUID();
    private final UUID random3 = UUID.randomUUID();

    /**
     * <b>Crea un usuario para Napoleón Bonaparte. </b>
     * <p>Usuario activo, con rol de usuario, y vinculado al barrio Villa dei Mulini en Toscana. </p>
     */
    public User napoleonBonaparte() {
        var napo = new User();
        napo.setId(1769);
        napo.setName("Napoleone Buonaparte");
        napo.setPassword("$2a$10$lypPLTdW2Af6F0f6ewBezumf6t0FhRJbfuvF.RvVsAAAIXZaMzpyK");
        napo.setUsername("napoleon.bonaparte");
        napo.setAddress("1815 Waterloo Rd., Sta Helena Island, UK");
        napo.setNeighbourhood(villaDeiMulini());
        napo.setActive(true);
        napo.setProfilePicture(n1);
        napo.setRole(Role.USER);
        return napo;
    }

    public User mariaLuisaDeAustria() {
        var u = new User();
        u.setUsername("mariaLuisaDeAustria");
        u.setName("Marie-Louise d'Autriche");
        u.setAddress("Hofburg 10, Longwood");
        u.setNeighbourhood(villaDeiMulini());
        u.setPassword("$2a$10$lypPLTdW2Af6F0f6ewBezumf6t0FhRJbfuvF.RvVsAAAIXZaMzpyK");
        u.setActive(true);
        u.setProfilePicture(n3);
        u.setRole(Role.USER);
        return u;
    }

    public Country unitedKingdom() {
        Country uk = new Country();
        uk.setIso3("GBR");
        uk.setActive(true);
        uk.setLabel("United Kingdom of Great Britain and Northern Ireland");
        return uk;
    }

    public SubnationalDivision saintHelena() {
        SubnationalDivision stHelena = new SubnationalDivision();
        stHelena.setIso3("UK-SHN");
        stHelena.setLabel("Saint Helena, Ascension and Tristan da Cunha");
        stHelena.setCountry(unitedKingdom());
        stHelena.setActive(true);
        stHelena.setDenomination(SubnationalDivisionDenomination.TERRITORY);
        return stHelena;
    }

    public City jamestown() {
        City jamestown = new City();
        jamestown.setId(1819);
        jamestown.setName("Jamestown");
        jamestown.setActive(true);
        jamestown.setSubdivision(saintHelena());
        return jamestown;
    }

    public Neighbourhood longwood() {
        Neighbourhood longwood = new Neighbourhood();
        longwood.setCity(jamestown());
        longwood.setId(1819001);
        longwood.setActive(true);
        longwood.setName("Longwood");
        return longwood;
    }

    /**
     * <b>Barrio marcado como inactivo</b>
     */
    public Neighbourhood shortwood() {
        Neighbourhood shortwood = new Neighbourhood();
        shortwood.setCity(jamestown());
        shortwood.setId(1819001);
        shortwood.setActive(false);
        shortwood.setName("Shortwood");
        return shortwood;
    }

    public Country italia() {
        Country italia = new Country();
        italia.setIso3("ITA");
        italia.setActive(true);
        italia.setLabel("Repubblica Italiana");
        return italia;
    }

    public SubnationalDivision toscana() {
        SubnationalDivision toscana = new SubnationalDivision();
        toscana.setIso3("IT-52");
        toscana.setLabel("Toscana");
        toscana.setActive(true);
        toscana.setCountry(italia());
        toscana.setDenomination(SubnationalDivisionDenomination.REGION);
        return toscana;
    }

    public City portoferraio() {
        City portoferraio = new City();
        portoferraio.setId(1819);
        portoferraio.setName("Portoferraio");
        portoferraio.setSubdivision(toscana());
        portoferraio.setActive(true);
        return portoferraio;
    }

    public Neighbourhood villaDeiMulini() {
        Neighbourhood villaDeiMulini = new Neighbourhood();
        villaDeiMulini.setCity(portoferraio());
        villaDeiMulini.setId(1819002);
        villaDeiMulini.setActive(true);
        villaDeiMulini.setName("Villa dei Mulini");
        return villaDeiMulini;
    }

    public Picture napoleonCruzandoLosAlpes() {
        Picture art = new Picture();
        art.setOwner(napoleon);
        art.setId(random1);
        art.setMediaType(MediaType.IMAGE_PNG);
        art.setActive(true);
        art.setUploadedAt(new Timestamp(-5238504372L));
        art.setDescription("Napoleón Cruzando los Alpes. Óleo sobre lienzo, por Jacques-Louis David en 1805.");
        return art;
    }

    public Picture autorretrato() {
        Picture art = new Picture();
        art.setOwner(napoleon);
        art.setId(random2);
        art.setMediaType(MediaType.IMAGE_PNG);
        art.setActive(true);
        art.setUploadedAt(new Timestamp(-4986043572L));
        art.setDescription("Retrato de Napoleón en su gabinete de trabajo. Óleo sobre lienzo, por Jacques-Louis David en 1812.");
        return art;
    }

    public Picture autorretratoDeOtraPersona() {
        Picture art = new Picture();
        art.setOwner(mariaLuisa);
        art.setId(random3);
        art.setActive(true);
        art.setMediaType(MediaType.IMAGE_PNG);
        art.setUploadedAt(new Timestamp(-4986049972L));
        art.setDescription("Retrato del Dr. Fulano de Tal.");
        return art;
    }

    public Post post_A() {
        var post = new Post();
        post.setActive(true);
        post.setId(404);
        post.setNeighbourhood(longwood());
        post.setTitle("Un destino que no comprenden");
        post.setDescription(
                "Me acusarán de mil cosas, pero ¿Quién puede realmente juzgarme? Exiliado en esta isla donde el viento trae recuerdos de mi gloria. Europa tiembla todavía ante mi sombra, mientras los pequeños hombres discuten mi legado en sus salones. ¡Qué fácil es criticar desde la comodidad! No entienden el peso de una corona ni el arte de una campaña. Aquí, en Longwood, los días son largos y los pensamientos infinitos. Pero mi espíritu… ese no se detiene.\n" +
                "\n" +
                "Algún día, recordarán mis victorias más que mis derrotas. Mientras tanto, el águila observa desde las alturas. \uD83E\uDD85");
        post.setTags("#Imperio #GloriaEterna #Exilio #Liderazgo #LeyendaViva #ElÁguila #FranciaGrande #Historia #Estratega #DestinyBelongsToMe");
        post.setOwner(napoleonBonaparte());
        post.setAudience(PUBLIC);
        post.setTimestamp(new Timestamp(-4986050084L));
        return post;
    }


    public Event event_A() {
        UUID eventId = UUID.fromString("cea268b5-1591-4dbe-b72e-77e7ecdad0fc");
        var event = new Event();
        event.setActive(true);
        event.setId(eventId);
        event.setTitle("Un destino que no comprenden");
        event.setAddress("Longwood, Saint Helena");
        event.setDescription(
                "Me acusarán de mil cosas, pero ¿Quién puede realmente juzgarme? Exiliado en esta isla donde el viento trae recuerdos de mi gloria. Europa tiembla todavía ante mi sombra, mientras los pequeños hombres discuten mi legado en sus salones. ¡Qué fácil es criticar desde la comodidad! No entienden el peso de una corona ni el arte de una campaña. Aquí, en Longwood, los días son largos y los pensamientos infinitos. Pero mi espíritu… ese no se detiene.\n" +
                "\n" +
                "Algún día, recordarán mis victorias más que mis derrotas. Mientras tanto, el águila observa desde las alturas. \uD83E\uDD85");
        event.setOwner(napoleonBonaparte());
        event.setAudience(PUBLIC);
        event.setCategory(EventCategory.EDUCATIVE);
        event.setCreatedAt(new Timestamp(-4986050084L));
        event.setStart(LocalDateTime.now().plusHours(1));
        event.setEnd(LocalDateTime.now().plusHours(3));
        Set<Neighbourhood> neighbourhoods = new HashSet<>(Collections.singletonList(longwood()));
        event.setNeighbourhoods(neighbourhoods);
        return event;
    }
}
