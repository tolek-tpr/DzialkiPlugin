package com.github.tolek.dzialki.plot;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlotTest {

    @Test
    void testOverlaps() {
        try {
            Plot plot = new Plot("test1", 0, 0, 10, 10, "player1", "");
            assertFalse(plot.overlaps(-1, -1), "Should return false when outside of plot area to the left/top");
            assertFalse(plot.overlaps(11, -1), "Should return false when outside of plot area to the right/top");
            assertFalse(plot.overlaps(11, -1), "Should return false when outside of plot area to the left/bottom");
            assertFalse(plot.overlaps(11, 11), "Should return false when outside of plot area to the right/bottom");
            assertFalse(plot.overlaps(-1, 5), "Should return false when outside of plot area to the left");
            assertFalse(plot.overlaps(11, 5), "Should return false when outside of plot area to the right");
            assertFalse(plot.overlaps(5, -1), "Should return false when outside of plot area to the top");
            assertFalse(plot.overlaps(5, 11), "Should return false when outside of plot area to the bottom");
            assertTrue(plot.overlaps(5, 5), "Should return true when inside of plot area");
        } catch (Exception e) {
            fail("Should not throw at plot creation");
        }
    }

    @Test
    void testIsOwnedBy() {
        Plot plot = new Plot("test1", 0, 0, 10, 10, "owner", "");
        assertTrue(plot.isOwnedBy("owner"), "Should return true when owned by owner");
        assertFalse(plot.isOwnedBy("some-other-player"), "Should return false when owned by someone else");
    }

    @Test
    void testIsAccessibleBy() {
        Plot plot = new Plot("test1", 0, 0, 10, 10, "owner", "", List.of("user1", "user2"));
        assertTrue(plot.isAccessibleBy("user1"), "Should return true when accessible by user1");
        assertTrue(plot.isAccessibleBy("user2"), "Should return true when accessible by user2");
        assertFalse(plot.isAccessibleBy("user3"), "Should return false when not accessible by user3");
    }

    @Test
    void testImportFromStorage() {
        String line = "test1|0|0|10|10|owner||user1,user2";
        Plot plot = Plot.importFromStorage(line);
        assertEquals("test1", plot.name);
        assertEquals("owner", plot.owner);
        assertEquals("", plot.type);
        assertEquals(0, plot.x);
        assertEquals(0, plot.z);
        assertEquals(10, plot.sizeX);
        assertEquals(10, plot.sizeZ);
        assertTrue(plot.allowedUsers.contains(("user1")));
        assertTrue(plot.allowedUsers.contains(("user2")));
    }

    @Test
    void testExportToStorage() {
        Plot plot = new Plot("test1", 0, 0, 10, 10, "owner", "", List.of("user1", "user2"));
        String actual = plot.exportToStorage();
        String expected = "test1|0|0|10|10|owner||user1,user2";
        assertEquals(expected, actual, "Should format the plot for export");
    }

}
