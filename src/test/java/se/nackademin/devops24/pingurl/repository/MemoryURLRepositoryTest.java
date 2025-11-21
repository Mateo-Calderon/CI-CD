package se.nackademin.devops24.pingurl.repository;

import org.junit.jupiter.api.Test;
import se.nackademin.devops24.pingurl.model.PingedURL;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MemoryURLRepositoryTest {

    @Test
    public void saveTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        PingedURL e = new PingedURL().setName("DN").setUrl("https://www.dn.se");

        repo.save(e.getName(), e.getUrl());

        Collection<PingedURL> all = repo.getUrls();
        assertEquals(1, all.size());
    }

    @Test
    public void findAllTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        repo.save("A", "url1");
        repo.save("B", "url2");

        Collection<PingedURL> all = repo.getUrls();
        assertEquals(2, all.size());
    }

    @Test
    public void findByNameTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        repo.save("DN", "https://dn.se");

        PingedURL e = repo.getUrls().stream()
                           .filter(u -> u.getName().equals("DN"))
                           .findFirst()
                           .orElse(null);

        assertNotNull(e);
        assertEquals("DN", e.getName());
    }

    @Test
    public void findMissingTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        repo.save("DN", "url");

        PingedURL e = repo.getUrls().stream()
                           .filter(u -> u.getName().equals("SVT"))
                           .findFirst()
                           .orElse(null);

        assertNull(e);
    }

    @Test
    public void deleteTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        repo.save("DN", "url");

        repo.delete("DN");
        assertEquals(0, repo.getUrls().size());
    }

    @Test
    public void deleteMissingTest() {
        MemoryURLRepository repo = new MemoryURLRepository();
        repo.save("DN", "url");

        repo.delete("SVT");
        assertEquals(1, repo.getUrls().size());
    }
}
