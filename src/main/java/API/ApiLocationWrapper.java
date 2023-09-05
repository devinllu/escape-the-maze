package API;

import Model.Cat;
import Model.CatsManager;

import java.util.ArrayList;
import java.util.List;

public class ApiLocationWrapper {
    // x == col
    public int x;

    // y == row
    public int y;

    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static ApiLocationWrapper makeFromCellLocation(int row, int col) {
        ApiLocationWrapper location = new ApiLocationWrapper();
        location.x = col;
        location.y = row;

        // Populate this object!

        return location;
    }
    // MAY NEED TO CHANGE PARAMETERS HERE TO SUITE YOUR PROJECT
    public static List<ApiLocationWrapper> makeFromCellLocations(CatsManager cats) {
        List<ApiLocationWrapper> locations = new ArrayList<>();

        for (Cat cat : cats) {
            int r = cat.getRow();
            int c = cat.getColumn();

            locations.add(ApiLocationWrapper.makeFromCellLocation(r, c));
        }

        // Populate this object!

        return locations;
    }
}