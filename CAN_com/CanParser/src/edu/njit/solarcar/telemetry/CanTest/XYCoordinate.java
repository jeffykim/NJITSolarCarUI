public class XYCoordinate {
    public int x;
    public int y;

    public XYCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public XYCoordinate(String line) {
        String s[] = line.split(" ");
        try {
            x = Integer.parseInt(s[0]);
            y = Integer.parseInt(s[1]);
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse coordinates from line \"" +line+ "\"");
        }
    }
}
