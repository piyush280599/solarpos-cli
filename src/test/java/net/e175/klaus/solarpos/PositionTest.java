package net.e175.klaus.solarpos;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

    @Test
    void testBasicUsageWithJson() {
        String lat = "52.0";
        String lon = "25.0";
        String dateTime = "2022-10-17T12:00:00Z";

        var result = TestUtil.executeIt(lat, lon, dateTime, "--format=json", "--deltat=69", "--show-inputs", "position");
        assertEquals(0, result.returnCode());

        var jsonObject = JsonParser.parseString(result.output()).getAsJsonObject();
        assertEquals(dateTime, jsonObject.get("dateTime").getAsString());
        assertEquals(211.17614, jsonObject.get("azimuth").getAsDouble());
        assertEquals(66.06678, jsonObject.get("zenith").getAsDouble());

        assertEquals(Double.parseDouble(lat), jsonObject.get("latitude").getAsDouble());
        assertEquals(Double.parseDouble(lon), jsonObject.get("longitude").getAsDouble());
    }

    @Test
    void testBasicUsageWithJsonGrena() {
        String lat = "52.0";
        String lon = "25.0";
        String dateTime = "2022-10-17T12:00:00Z";

        var result = TestUtil.executeIt(lat, lon, dateTime, "--format=json", "--deltat=69", "position", "--algorithm=grena3");
        assertEquals(0, result.returnCode());

        var jsonObject = JsonParser.parseString(result.output()).getAsJsonObject();
        assertEquals(dateTime, jsonObject.get("dateTime").getAsString());
        assertEquals(211.17436, jsonObject.get("azimuth").getAsDouble());
        assertEquals(66.06694, jsonObject.get("zenith").getAsDouble());
    }

    @Test
    void testBasicUsageWithCsv() {
        String lat = "52.0";
        String lon = "25.0";
        String dateTime = "2003-10-17T12:00:00Z";

        var result = TestUtil.executeIt(lat, lon, dateTime, "--format=csv", "--deltat=69", "position");
        assertEquals(0, result.returnCode());
        assertEquals("2003-10-17T12:00:00Z,211.20726,65.92346", result.output().strip());

        result = TestUtil.executeIt(lat, lon, dateTime, "--format=csv", "--show-inputs", "--deltat=69", "position");
        assertEquals(0, result.returnCode());

        assertEquals("52.00000,25.00000,0.000,1000.000,0.000,2003-10-17T12:00:00Z,69.000,211.20726,65.92346", result.output().strip());
    }

    @Test
    void testSeriesUsageWithCsv() {
        String lat = "52.0";
        String lon = "25.0";
        String dateTime = "2003-10-17";

        var result = TestUtil.executeIt(lat, lon, dateTime, "--format=csv", "--deltat=69", "--timezone=UTC", "position", "--step=7200");
        assertEquals(0, result.returnCode());
        assertEquals("""
                        2003-10-17T00:00:00Z,38.87778,131.09385
                        2003-10-17T02:00:00Z,69.90910,116.13739
                        2003-10-17T04:00:00Z,94.54534,97.98688
                        2003-10-17T06:00:00Z,118.48590,80.31973
                        2003-10-17T08:00:00Z,146.00826,66.76720
                        2003-10-17T10:00:00Z,178.46662,61.15144
                        2003-10-17T12:00:00Z,211.20726,65.92346
                        2003-10-17T14:00:00Z,239.15181,78.97740
                        2003-10-17T16:00:00Z,263.21613,96.46308
                        2003-10-17T18:00:00Z,287.52922,114.74832
                        2003-10-17T20:00:00Z,317.71947,130.28269
                        2003-10-17T22:00:00Z,358.05561,137.33998""",
                result.output().strip());
    }


}
