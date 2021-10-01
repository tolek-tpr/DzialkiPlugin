package com.github.tolek.dzialki.plot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlotManagerTest {

	private int mocksCount = 0;

	private Plot mockPlot() {
		return new Plot("MOCKPLOT" + (++mocksCount), mocksCount * 2, mocksCount * 2, 1, 1, "MOCKUSER" + mocksCount, "");
	}

    @Test
    void getPlotByLocation() {
    	Plot plot1 = new Plot("test1", 0, 0, 10, 10, "player1", "");
    	Plot plot2 = new Plot("test1", 20, 20, 10, 10, "player1", "");
        PlotManager plots = new PlotManager();
    	plots.add(plot1);
    	plots.add(plot2);

    	assertEquals(plot1, plots.getPlotByLocation(0, 0));
    	assertEquals(plot1, plots.getPlotByLocation(10, 10));
    	assertEquals(plot1, plots.getPlotByLocation(5, 5));

    	assertEquals(plot2, plots.getPlotByLocation(20, 30));
    	assertEquals(plot2, plots.getPlotByLocation(30, 20));
    	assertEquals(plot2, plots.getPlotByLocation(25, 25));

    	assertNull(plots.getPlotByLocation(500, 500));
    }

	@Test
	void getPlotCount_and_removeAll() {
		PlotManager plots = new PlotManager();
		assertEquals(0, plots.getPlotCount());

		assertTrue(plots.add(mockPlot()));
		assertTrue(plots.add(mockPlot()));
		assertTrue(plots.add(mockPlot()));
		assertEquals(3, plots.getPlotCount());

		plots.removeAll();
		assertEquals(0, plots.getPlotCount());
	}

}
