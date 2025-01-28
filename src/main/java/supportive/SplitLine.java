package supportive;
public  class SplitLine {
    public static String getSession(String massage) {
        String[] data = massage.split(":");
        return data[1];
    }
}
