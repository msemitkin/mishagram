package ua.knu.mishagram;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Point {

    @NotNull
    private final Double longitude;
    @NotNull
    private final Double latitude;

    public Point(@NotNull Double longitude, @NotNull Double latitude) {
        this.longitude = Objects.requireNonNull(longitude);
        this.latitude = Objects.requireNonNull(latitude);
    }

    @NotNull
    public Double getLongitude() {
        return longitude;
    }

    @NotNull
    public Double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.longitude, longitude) == 0 && Double.compare(point.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}
