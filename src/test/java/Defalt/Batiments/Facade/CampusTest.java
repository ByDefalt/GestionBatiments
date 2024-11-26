package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampusTest {

    private Campus campus;
    private BatimentFactory mockFactory;

    @BeforeEach
    void setUp() {
        campus = new Campus();
        mockFactory = mock(BatimentFactory.class);
        BatimentFactory bf = mockFactory;
    }

    @Test
    void testCreateBatiment() {
        Batiment mockBatiment = mock(Batiment.class);
        when(mockFactory.createBatiment(anyString(), anyString(), anyInt(), anyBoolean(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockBatiment);

        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);

        assertEquals(1, campus.getBatiments().size());
        verify(mockFactory, times(1))
                .createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
    }

    @Test
    void testDestroyBatiment() {
        Batiment mockBatiment = mock(Batiment.class);
        when(mockBatiment.getNom()).thenReturn("B1");
        campus.batiments.add(mockBatiment);

        assertTrue(campus.destroyBatiment("B1"));
        assertTrue(campus.getBatiments().isEmpty());

        assertFalse(campus.destroyBatiment("NonExistent"));
    }

    @Test
    void testUpdateNomBatiment() {
        Batiment mockBatiment = mock(Batiment.class);
        when(mockBatiment.getNom()).thenReturn("B1");
        doAnswer(invocation -> {
            when(mockBatiment.getNom()).thenReturn(invocation.getArgument(0));
            return null;
        }).when(mockBatiment).setNom(anyString());

        campus.batiments.add(mockBatiment);

        assertTrue(campus.updateNomBatiment("B1", "B2"));
        assertEquals("B2", mockBatiment.getNom());

        assertFalse(campus.updateNomBatiment("NonExistent", "NewName"));
    }

    @Test
    void testUpdatePieceEstBureau() {
        Batiment mockBatiment = mock(Batiment.class);
        when(mockBatiment.getNom()).thenReturn("B1");
        when(mockBatiment.getPieces()).thenReturn(new ArrayList<>());

        campus.batiments.add(mockBatiment);

        assertFalse(campus.updatePieceEstBureau("B1", 1));
    }

    @Test
    void testAddObserverAndNotify() {
        Observer mockObserver = mock(Observer.class);

        campus.addObserver(mockObserver);
        assertEquals(1, campus.getObservers().size());

        campus.notifyObservers();
        verify(mockObserver, times(1)).update();
    }

    @Test
    void testRemoveObserver() {
        Observer mockObserver = mock(Observer.class);
        campus.addObserver(mockObserver);

        campus.removeObserver(mockObserver);
        assertTrue(campus.getObservers().isEmpty());
    }

    @Test
    void testBatimentsToJson() throws Exception {
        // Setup
        BatimentFactory mockFactoryJson = mock(BatimentFactory.class);
        campus.batimentFactory = mockFactoryJson;
        Batiment mockBatiment = mock(Batiment.class);
        campus.batiments.add(mockBatiment);

        // Execute
        campus.batimentsToJson("output.json");

        // Verify
        verify(mockFactoryJson, times(1)).BatimentsToJson(anyList(), eq("output.json"));
    }

    @Test
    void testJsonToBatiments() throws Exception {
        BatimentFactory mockFactoryJson = mock(BatimentFactory.class);
        VerificateurBatiment mockVerifier = mock(VerificateurBatiment.class);
        List<Batiment> importedBatiments = new ArrayList<>();
        Batiment validBatiment = mock(Batiment.class);
        importedBatiments.add(validBatiment);

        when(mockFactoryJson.jsonToBatiments("input.json")).thenReturn(importedBatiments);
        when(mockVerifier.verifBatiment(any())).thenReturn(List.of(ProblemeBatiment.AUCUN));

        campus.batimentFactory = mockFactoryJson;

        String result = campus.jsonToBatiments("input.json");

        assertTrue(result.isEmpty());
        assertEquals(1, campus.getBatiments().size());
    }
}
