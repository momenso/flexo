package org.momenso;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;


public class MeaningDefinerTest {

    @Test
    public void defineSingleValueMeaning() {
        MeaningDefiner definition = new MeaningDefiner()
                .define("00", "Zero");

        assertEquals(Optional.of("Zero"), definition.getMeaning("00"));
    }

    @Test
    public void defineMultipleValueMeanings() {
        MeaningDefiner definer = new MeaningDefiner()
                .define("00", "Zero")
                .define("01", "One")
                .define("02", "Two");

        assertEquals(Optional.of("Zero"), definer.getMeaning("00"));
        assertEquals(Optional.of("One"), definer.getMeaning("01"));
        assertEquals(Optional.of("Two"), definer.getMeaning("02"));
    }

    @Test
    public void defineSingleTwoLevelNestedMeaning() {
        MeaningDefiner definer = new MeaningDefiner()
                .define("00", "Zero",
                        new MeaningDefiner()
                                .define("10", "Ten"));

        assertEquals(Optional.of("Zero"), definer.getMeaning("00"));
        assertEquals(Optional.of("Ten"), definer.getMeaning("00", "10"));
    }

    @Test
    public void defineMultipleTwoLevelMeaning() {
        MeaningDefiner definer = new MeaningDefiner()
                .define("00", "Zero",
                        new MeaningDefiner()
                                .define("01", "One")
                                .define("02", "Two"));

        assertEquals(Optional.of("Zero"), definer.getMeaning("00"));
        assertEquals(Optional.of("One"), definer.getMeaning("00", "01"));
        assertEquals(Optional.of("Two"), definer.getMeaning("00", "02"));
    }

    @Test
    public void defineTwoLevelComplete() {
        MeaningDefiner definer = new MeaningDefiner()
                .define("G0", "Group 0",
                        new MeaningDefiner()
                                .define("C0", "Child 0")
                                .define("C1", "Child 1"))
                .define("G1", "Group 1",
                        new MeaningDefiner()
                                .define("C2", "Child 2")
                                .define("C3", "Child 3"));

        assertEquals(Optional.of("Group 0"), definer.getMeaning("G0"));
        assertEquals(Optional.of("Child 0"), definer.getMeaning("G0", "C0"));
        assertEquals(Optional.of("Child 1"), definer.getMeaning("G0", "C1"));
        assertEquals(Optional.of("Group 1"), definer.getMeaning("G1"));
        assertEquals(Optional.of("Child 2"), definer.getMeaning("G1", "C2"));
        assertEquals(Optional.of("Child 3"), definer.getMeaning("G1", "C3"));
    }

    @Test
    public void defineThreeLevelComplete() {
        MeaningDefiner definer = new MeaningDefiner()
                .define("GA", "Group A",
                        new MeaningDefiner()
                                .define("GB", "Group B",
                                        new MeaningDefiner()
                                                .define("E0", "Element 0")
                                                .define("E1", "Element 1")
                                                .define("E2", "Element 2")
                                ))
                .define("GC", "Group C",
                        new MeaningDefiner()
                                .define("GD", "Group D",
                                        new MeaningDefiner()
                                                .define("E3", "Element 3")
                                                .define("E4", "Element 4")
                                                .define("E5", "Element 5")
                                ));


        assertEquals(Optional.of("Group A"), definer.getMeaning("GA"));
        assertEquals(Optional.of("Group B"), definer.getMeaning("GA", "GB"));
        assertEquals(Optional.of("Element 0"), definer.getMeaning("GA", "GB", "E0"));
        assertEquals(Optional.of("Element 1"), definer.getMeaning("GA", "GB", "E1"));
        assertEquals(Optional.of("Element 2"), definer.getMeaning("GA", "GB", "E2"));
        assertEquals(Optional.of("Group C"), definer.getMeaning("GC"));
        assertEquals(Optional.of("Group D"), definer.getMeaning("GC", "GD"));
        assertEquals(Optional.of("Element 3"), definer.getMeaning("GC", "GD", "E3"));
        assertEquals(Optional.of("Element 4"), definer.getMeaning("GC", "GD", "E4"));
        assertEquals(Optional.of("Element 5"), definer.getMeaning("GC", "GD", "E5"));
    }
}