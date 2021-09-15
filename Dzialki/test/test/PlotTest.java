package com.github.tolek.plot.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.github.tolek.plot.Plot;

class PlotTest {

	@Test
	void testOverlaps() {
		try {
			Plot plot = new Plot("test1", 0, 0, 10, 10, "player1");
			assertFalse("Should return false when outside of plot area to the left/top", plot.overlaps(-1, -1));
			assertFalse("Should return false when outside of plot area to the right/top", plot.overlaps(11, -1));
			assertFalse("Should return false when outside of plot area to the left/bottom", plot.overlaps(11, -1));
			assertFalse("Should return false when outside of plot area to the right/bottom", plot.overlaps(11, 11));
			assertFalse("Should return false when outside of plot area to the left", plot.overlaps(-1, 5));
			assertFalse("Should return false when outside of plot area to the right", plot.overlaps(11, 5));
			assertFalse("Should return false when outside of plot area to the top", plot.overlaps(5, -1));
			assertFalse("Should return false when outside of plot area to the bottom", plot.overlaps(5, 11));
			assertTrue("Should return true when inside of plot area", plot.overlaps(5, 5));
		} catch (Exception e) {
			fail("Should not throw at plot creation");
		}
	}
	
	@Test
	void testIsOwnedBy() {
		try {
			Plot plot = new Plot("test1", 0, 0, 10, 10, "owner");
			assertTrue("Should return true when owned by owner", plot.isOwnedBy("owner"));
			assertFalse("Should return false when owned by someone else", plot.isOwnedBy("some-other-player"));
		} catch (Exception e) {
			fail("Should not throw at plot creation");
		}
	}

}
