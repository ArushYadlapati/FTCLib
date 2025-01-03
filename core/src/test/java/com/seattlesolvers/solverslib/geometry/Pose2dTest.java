package com.seattlesolvers.solverslib.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class Pose2dTest {
    private static final double kEpsilon = 1E-9;
    Pose2d base = new Pose2d(new Translation2d(0, 0), new Rotation2d(0));

    @Test
    public void testPlus() {
        Pose2d output = base.plus(new Transform2d(new Translation2d(1, 1), new Rotation2d(90)));
        Pose2d expected = new Pose2d(new Translation2d(1, 1), new Rotation2d(90));
        assertEquals(output, expected);
    }

    @Test
    public void testTransformBy() {
        Pose2d initial = new Pose2d(new Translation2d(1.0, 2.0), Rotation2d.fromDegrees(45.0));
        Transform2d transformation = new Transform2d(new Translation2d(5.0, 0.0),
                Rotation2d.fromDegrees(5.0));

        Pose2d transformed = initial.plus(transformation);

        assertEquals(transformed.getX(), 1 + 5.0 / Math.sqrt(2.0), kEpsilon);
        assertEquals(transformed.getY(), 2 + 5.0 / Math.sqrt(2.0), kEpsilon);
        assertEquals(transformed.getRotation().getDegrees(), 50.0, kEpsilon);
    }

    @Test
    public void testRelativeTo() {
        Pose2d initial = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(45.0));
        Pose2d last = new Pose2d(5.0, 5.0, Rotation2d.fromDegrees(45.0));

        Pose2d finalRelativeToInitial = last.relativeTo(initial);

        assertEquals(finalRelativeToInitial.getX(), 5.0 * Math.sqrt(2.0), kEpsilon);
        assertEquals(finalRelativeToInitial.getY(), 0.0, kEpsilon);
        assertEquals(finalRelativeToInitial.getRotation().getDegrees(), 0.0, kEpsilon);
    }

    @Test
    public void testEquality() {
        Pose2d one = new Pose2d(0.0, 5.0, Rotation2d.fromDegrees(43.0));
        Pose2d two = new Pose2d(0.0, 5.0, Rotation2d.fromDegrees(43.0));
        assertEquals(one, two);
    }

    @Test
    public void testInequality() {
        Pose2d one = new Pose2d(0.0, 5.0, Rotation2d.fromDegrees(43.0));
        Pose2d two = new Pose2d(0.0, 1.524, Rotation2d.fromDegrees(43.0));
        assertNotEquals(one, two);
    }

    @Test
    public void testMinus() {
        Pose2d initial = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(45.0));
        Pose2d last = new Pose2d(5.0, 5.0, Rotation2d.fromDegrees(45.0));

        final Transform2d transform = last.minus(initial);

        assertEquals(transform.getTranslation().getX(), 5.0 * Math.sqrt(2.0), kEpsilon);
        assertEquals(transform.getTranslation().getY(), 0.0, kEpsilon);
        assertEquals(transform.getRotation().getDegrees(), 0.0, kEpsilon);
    }
}