package seng202.team6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import seng202.team6.models.Position;

public class FilterBuilder {
    private List<String> filters = new ArrayList<>();
    private List<Object> arguments = new ArrayList<>();

    private static String escapeWildcards(String str) {
        return str
                .replace("%", "\\%")
                .replace("\\", "\\\\")
                .replace("_", "\\_");

    }

    /**
     * Add a filter to search stations by name, address, or operator.
     * based off the given search string
     * @param search The search string given
     */
    public void addSearchFilter(String search) {
        filters.add("name LIKE ? OR address LIKE ? OR operator LIKE ?");
        String argument = "%" + escapeWildcards(search) + "%";
        // We add this 3 times for the 3 ?'s
        arguments.add(argument);
        arguments.add(argument);
        arguments.add(argument);
    }

    /**
     * Filter for distance from current position.
     * @param position Current position
     * @param kilometers distance to check
     */
    public void addDistanceFilter(Position position, double kilometers) {
        // A rough approximation for latitude, should be changed at some point
        double distance = kilometers / 110.574;
        filters.add("lat < ? AND lat > ? AND long < ? AND long > ?");
        arguments.add(position.getLatitude() + distance);
        arguments.add(position.getLatitude() - distance);
        arguments.add(position.getLongitude() + distance);
        arguments.add(position.getLongitude() - distance);
    }

    /**
     * Filter for time limit.
     * @param timeLimit The timelimit to check
     */
    public void addTimeLimitFilter(double timeLimit) {
        filters.add("timeLimit >= ? OR timeLimit == 0");
        arguments.add(timeLimit);
    }

    /**
     * Filter for 24 hours only.
     */
    public void addIs24HourFilter() {
        filters.add("is24Hours = 1");
    }

    /**
     * Filter for no carpark cost.
     */
    public void addCarParkCostFilter() {
        filters.add("carparkCost = 0");
    }

    /**
     * Filter for tourist attraction nearby.
     */
    public void addHasTouristAttractionFilter() {
        filters.add("hasTouristAttraction = 1");
    }

    /**
     * Filter for no charging cost.
     */
    public void addHasChargingCostFilter() {
        filters.add("chargingCost = 0");
    }

    /**
     * Building the filter as a prepared statement.
     * @param conn The connection to use
     */
    public PreparedStatement build(Connection conn) throws SQLException {
        String sql = "SELECT * FROM stations "
                + "JOIN chargers c ON stations.stationId = c.stationId ";
        if (filters.isEmpty()) {
            sql += " ORDER BY stations.stationId";
            return conn.prepareStatement(sql);
        }

        sql += " WHERE " + filters.stream()
                .map(str -> String.format("(%s)", str))
                .collect(Collectors.joining());
        sql += " ORDER BY stations.stationId";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < arguments.size(); i++) {
            ps.setObject(i + 1, arguments.get(i));
        }
        return ps;
    }
}
